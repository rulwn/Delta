package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassCentro
import RecycleViewHelper.AdaptadorCentro
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

class fragment_Resultados : Fragment() {

    private lateinit var btnRegresar: ImageView
    private lateinit var rcvResultados: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment__resultados, container, false)

        btnRegresar = root.findViewById(R.id.btnRegresar)
        rcvResultados = root.findViewById(R.id.rcvResultados)

        btnRegresar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        rcvResultados.layoutManager = LinearLayoutManager(context)

        arguments?.getString("query")?.let {
        }

        suspend fun obtenerDatos(): List<dataClassCentro> {
            return withContext(Dispatchers.IO) {
                val centroMedico = mutableListOf<dataClassCentro>()
                try {
                    val objConexion = ClaseConexion()?.cadenaConexion()
                    if (objConexion != null) {
                        val statement = objConexion.createStatement()
                        val resultSet = statement.executeQuery("""
SELECT
    cm.ID_Doctor,
    u.nombreUsuario,
    u.apellidoUsuario,
    u.imgUsuario,
    s.nombreSucursal,
    s.telefonoSucur,
    s.direccionSucur,
    s.ubicacionSucur,
    e.nombreEspecialidad,
    srv.nombreServicio,
    srv.costo,
    cm.favorito
FROM 
    tbCentrosMedicos cm
INNER JOIN
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
INNER JOIN
    tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
INNER JOIN
    tbSucursales s ON cm.ID_Sucursal = s.ID_Sucursal
INNER JOIN
    tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
INNER JOIN
    tbServicios srv ON cm.ID_Centro = srv.ID_Centro
                """)
                        while (resultSet.next()) {
                            val ID_Doctor = resultSet.getInt("ID_Doctor")
                            val nombreUsuario = resultSet.getString("nombreUsuario")
                            val apellidoUsuario = resultSet.getString("apellidoUsuario")
                            val imgUsuario = resultSet.getString("imgUsuario")
                            val nombreSucursal = resultSet.getString("nombreSucursal")
                            val telefonoSucur = resultSet.getString("telefonoSucur")
                            val direccionSucur = resultSet.getString("direccionSucur")
                            val ubicacionSucur = resultSet.getString("ubicacionSucur")
                            val nombreEspecialidad = resultSet.getString("nombreEspecialidad")
                            val nombreServicio = resultSet.getString("nombreServicio")
                            val costo = resultSet.getFloat("costo")
                            val favorito = resultSet.getBoolean("favorito")

                            centroMedico.add(dataClassCentro(ID_Doctor, nombreUsuario, apellidoUsuario, imgUsuario, nombreEspecialidad, nombreSucursal, telefonoSucur, direccionSucur,
                                ubicacionSucur, nombreServicio, costo, favorito))
                        }
                        resultSet.close()
                        statement.close()
                    } else {
                        Log.e("obtenerDatos", "No se pudo establecer una conexión con la base de datos.")
                    }
                } catch (e: Exception) {
                    Log.e("obtenerDatos", "Error al obtener datos: ${e.message}")
                }
                centroMedico
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val centrosDB = obtenerDatos()
            withContext(Dispatchers.Main) {
                val miAdapter = AdaptadorCentro(centrosDB)
                rcvResultados.adapter = miAdapter
            }
        }
        return root
    }
}