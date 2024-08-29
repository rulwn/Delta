package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassIndicaciones
import RecycleViewHelper.AdaptadorCitas
import RecycleViewHelper.AdaptadorTratamientos
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date
import java.util.Calendar

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
        val txtAunNotienescitas = root.findViewById<TextView>(R.id.txtAunNotienescitas)
        val rcvTratamientos = root.findViewById<RecyclerView>(R.id.rcvRecordatoriosTratamientos)
        rcvTratamientos.layoutManager = LinearLayoutManager(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val tratamientosDB = obtenerDatosTratamientos()
                withContext(Dispatchers.Main) {
                    if (tratamientosDB.isEmpty()) {
                        txtAunNotienescitas.visibility = View.VISIBLE
                    } else {
                        txtAunNotienescitas.visibility = View.GONE
                        val miAdaptador = AdaptadorTratamientos(tratamientosDB)
                        rcvTratamientos.adapter = miAdaptador
                        println("Adaptador size: ${tratamientosDB.size}")

                        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                            val selectedDate = Calendar.getInstance().apply {
                                set(year, month, dayOfMonth)
                            }.time

                            val tratamientosFiltrados = tratamientosDB.filter { tratamiento ->
                                tratamiento.inicioMedi?.let { it <= selectedDate } == true &&
                                        tratamiento.finalMedi?.let { it >= selectedDate } == true
                            }

                            miAdaptador.Actualizarlista(tratamientosFiltrados)
                        }
                    }
                }
            } catch (e: Exception) {
                println("Este es el error ${e.message}")
            }
        }
        return root
    }

    suspend fun obtenerDatosTratamientos(): List<dataClassIndicaciones> {
        return withContext(Dispatchers.IO) {
            val indicaciones = mutableListOf<dataClassIndicaciones>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement(
                        "SELECT indi.ID_Indicacion, " +
                                "indi.inicioMedi, " +
                                "indi.finalMedi, " +
                                "indi.dosisMedi, " +
                                "indi.medicina, " +
                                "indi.detalleindi, " +
                                "tiem.lapsostiempo, " +
                                "tiem.frecuenciamedi " +
                                "FROM tbIndicaciones indi " +
                                "INNER JOIN tbTiempos tiem ON indi.id_tiempo = tiem.id_tiempo " +
                                "INNER JOIN tbRecetas rec ON indi.id_receta = rec.id_receta " +
                                "INNER JOIN tbFichasMedicas fichi ON rec.id_receta = fichi.id_receta " +
                                "INNER JOIN tbcitasmedicas citas ON fichi.id_cita = citas.id_cita " +
                                "INNER JOIN tbpacientes PACS ON citas.id_paciente = PACS.id_paciente " +
                                "INNER JOIN tbUsuarios USUA ON PACS.id_usuario = USUA.id_usuario " +
                                "WHERE USUA.emailusuario = ?"
                    )
                    statement.setString(1, userEmail)
                    val resultSet = statement.executeQuery()
                    var resultCount = 0


                    while (resultSet.next()) {
                        resultCount++
                        val ID_Indicacion = resultSet.getInt("ID_Indicacion")
                        val inicioMedi = resultSet.getDate("inicioMedi")
                        val finalMedi = resultSet.getDate("finalMedi")
                        val dosisMedi = resultSet.getString("dosisMedi")
                        val medicina = resultSet.getString("medicina")
                        val detalleIndi = resultSet.getString("detalleindi")
                        val lapsosTiempo = resultSet.getString("lapsostiempo")
                        val frecuenciaMedi = resultSet.getInt("frecuenciamedi")

                        val horas = when (frecuenciaMedi) {
                            1 -> listOf("8:00 AM")
                            2 -> listOf("8:00 AM", "8:00 PM")
                            3 -> listOf("8:00 AM", "2:00 PM", "8:00 PM")
                            4 -> listOf("7:00 AM", "12:00 PM", "5:00 PM", "10:00 PM")
                            else -> emptyList()
                        }

                        for (hora in horas) {
                            val indicacion = dataClassIndicaciones(
                                ID_Indicacion,
                                inicioMedi,
                                finalMedi,
                                dosisMedi,
                                medicina,
                                detalleIndi,
                                lapsosTiempo,
                                frecuenciaMedi,
                                hora
                            )
                            indicaciones.add(indicacion)
                            println("Added indicacion: $medicina at $hora")
                        }
                    }
                    println("Number of records fetched: $resultCount")
                } else {
                    println("No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: Exception) {
                println("Este es el error ${e.message}")
            }
            indicaciones
        }
    }
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