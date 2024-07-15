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
                    objConexion?.createStatement()?.executeQuery(
                        """
                           SELECT
    u.nombreUsuario,
    u.apellidoUsuario,
    u.imgUsuario,
    s.nombreSucursal,
    s.telefonoSucur ,
    s.direccionSucur ,
    s.ubicacionSucur ,
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
    tbServicios srv ON cm.ID_Centro = srv.ID_Centro
                        """)?.apply {
                        while (next()) {
                            val ID_Doctor = getInt("ID_Doctor")
                            val nombreEspecialidad = getString("nombreEspecialidad")
                            val direccionSucur = getString("direccionSucur")
                            val nombreUsuario = getString("nombreUsuario")
                            val apellidoUsuario = getString("apellidoUsuario")
                            val nombreSucursal = getString("nombreSucursal")
                            val telefonoSucur = getString("telefonoSucur")
                            val ubicacionSucur = getString("ubicacionSucur")
                            val nombreServicio = getString("nombreServicio")
                            val costo = getFloat("costo")
                            val favorito = getBoolean("favorito")
                            centroMedico.add(dataClassCentro(ID_Doctor, nombreUsuario, apellidoUsuario, nombreEspecialidad, nombreSucursal, telefonoSucur, direccionSucur,
                                ubicacionSucur, nombreServicio, costo, favorito))
                        }
                    } ?: println("No se pudo establecer una conexión con la base de datos.")
                } catch (e: Exception) {
                    println("Este es el error ${e.message}")
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
