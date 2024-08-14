package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassCentro
import RecycleViewHelper.AdaptadorCentro
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log
import android.widget.EditText

class fragment_Resultados : Fragment() {

    private lateinit var btnRegresar: ImageView
    private lateinit var rcvResultados: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment__resultados, container, false)

        // Obtener los argumentos del fragmento
        val nombreUsuario = arguments?.getString("nombreUsuario") ?: ""
        val apellidoUsuario = arguments?.getString("apellidoUsuario") ?: ""
        val nombreEspecialidad = arguments?.getString("nombreEspecialidad") ?: ""

        btnRegresar = root.findViewById(R.id.btnRegresar)
        rcvResultados = root.findViewById(R.id.rcvResultados)

        btnRegresar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        rcvResultados.layoutManager = LinearLayoutManager(context)

        // Ejecutar la búsqueda en una coroutine
        CoroutineScope(Dispatchers.IO).launch {
            val centrosDB = obtenerDatos(nombreUsuario, apellidoUsuario, nombreEspecialidad)
            withContext(Dispatchers.Main) {
                val miAdapter = AdaptadorCentro(centrosDB)
                rcvResultados.adapter = miAdapter
            }
        }

        return root
    }

    private suspend fun obtenerDatos(nombreUsuario: String, apellidoUsuario: String, nombreEspecialidad: String): List<dataClassCentro> {
        return withContext(Dispatchers.IO) {
            val centroMedico = mutableListOf<dataClassCentro>()
            try {
                val objConexion = ClaseConexion()?.cadenaConexion()
                if (objConexion != null) {
                    // Ajusta la consulta SQL para incluir la columna ID_Doctor
                    val busqueda = objConexion.prepareStatement("""
SELECT 
    d.ID_Doctor,
    u.nombreUsuario, 
    u.apellidoUsuario, 
    u.imgUsuario, 
    e.nombreEspecialidad,
    s.nombreSucursal,
    s.telefonoSucur, 
    s.direccionSucur, 
    s.ubicacionSucur, 
    srv.nombreServicio, 
    srv.costo
FROM 
    tbCentrosMedicos cm
INNER JOIN 
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
INNER JOIN 
    tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
INNER JOIN
    tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
INNER JOIN
    tbSucursales s ON cm.ID_Sucursal = s.ID_Sucursal
INNER JOIN 
    tbServicios srv ON cm.ID_Centro = srv.ID_Centro
WHERE 
    (
        LOWER(u.nombreUsuario) LIKE LOWER(?)
        OR LOWER(u.apellidoUsuario) LIKE LOWER(?)
        OR LOWER(e.nombreEspecialidad) LIKE LOWER(?)
    )
    AND u.ID_TipoUsuario = 2
                """)
                    // Establece los parámetros para la consulta
                    busqueda.setString(1, "%${nombreUsuario.lowercase()}%")
                    busqueda.setString(2, "%${apellidoUsuario.lowercase()}%")
                    busqueda.setString(3, "%${nombreEspecialidad.lowercase()}%")

                    val resultSet = busqueda.executeQuery()
                    while (resultSet.next()) {
                        val ID_Doctor = resultSet.getInt("ID_Doctor")
                        val nombreUsuario = resultSet.getString("nombreUsuario")
                        val apellidoUsuario = resultSet.getString("apellidoUsuario")
                        val imgUsuario = resultSet.getString("imgUsuario")
                        val nombreEspecialidad = resultSet.getString("nombreEspecialidad")
                        val nombreSucursal = resultSet.getString("nombreSucursal")
                        val telefonoSucur = resultSet.getString("telefonoSucur")
                        val direccionSucur = resultSet.getString("direccionSucur")
                        val ubicacionSucur = resultSet.getString("ubicacionSucur")
                        val nombreServicio = resultSet.getString("nombreServicio")
                        val costo = resultSet.getFloat("costo")

                        centroMedico.add(dataClassCentro(
                            ID_Doctor, nombreUsuario, direccionSucur
                        ))
                    }
                } else {
                    Log.e("obtenerDatos", "No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: Exception) {
                Log.e("obtenerDatos", "Error al obtener datos: ${e.message}")
            }
            centroMedico
        }
    }
}