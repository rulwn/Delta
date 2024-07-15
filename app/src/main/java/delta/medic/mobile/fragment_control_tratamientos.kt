package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassIndicaciones
import RecycleViewHelper.AdaptadorTratamientos
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_control_tratamientos.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_control_tratamientos : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_control_tratamientos, container, false)
        val calendarView = root.findViewById<CalendarView>(R.id.calendarTratamientos)
        val rcvTratamientos = root.findViewById<RecyclerView>(R.id.rcvRecordatoriosTratamientos)
        rcvTratamientos.layoutManager = LinearLayoutManager(requireContext())
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val tratamientosDB = obtenerDatos()
                withContext(Dispatchers.Main) {
                    val miAdaptador = AdaptadorTratamientos(tratamientosDB)
                    rcvTratamientos.adapter = miAdaptador
                }
            } catch (e: Exception) {
                println("Este es el error ${e.message}")
            }
        }
        return root
    }
    //asignar un layout al rcv

    private suspend fun obtenerDatos(): List<dataClassIndicaciones>{
        return withContext(Dispatchers.IO) {
            val tratamientos = mutableListOf<dataClassIndicaciones>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                val statement = objConexion.createStatement()
                val resultset = statement?.executeQuery("Select * from tbIndicaciones")!!
                while (resultset.next()) {
                    val idIndicaciones = resultset.getInt("ID_Indicacion")
                    val duracion = resultset.getTimestamp("duracionMedi")
                    val dosisMedicina = resultset.getString("dosisMedi")
                    val nombreMedicina = resultset.getString("medicina")
                    val detalleIndi = resultset.getString("detalleIndi")
                    val idReceta = resultset.getInt("ID_Receta")
                    val idTiempo = resultset.getInt("ID_Tiempo")
                    val tratamiento = dataClassIndicaciones(
                        idIndicaciones,
                        duracion,
                        dosisMedicina,
                        nombreMedicina,
                        detalleIndi,
                        idTiempo,
                        idReceta
                    )
                    tratamientos.add(tratamiento)
                }
                } else {
                    println("No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: Exception) {
                println("Este es el error ${e.message}")
            }
        tratamientos
        }
    }
    //Asignar un adaptador


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_control_tratamientos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_control_tratamientos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}