package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassIndicaciones
import RecycleViewHelper.AdaptadorTratamientos
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_control_tratamientos, container, false)
        val calendarView = root.findViewById<CalendarView>(R.id.calendarTratamientos)
        val txtRecordatorioTratamientos =
            root.findViewById<TextView>(R.id.txtRecordatorioTratamientos)
        val txtAunNotienescitas = root.findViewById<TextView>(R.id.txtAunNotienescitas)
        val rcvTratamientos = root.findViewById<RecyclerView>(R.id.rcvRecordatoriosTratamientos)
        rcvTratamientos.layoutManager = LinearLayoutManager(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val tratamientosDB = obtenerDatosTratamientosPeriodo()
                withContext(Dispatchers.Main) {
                    if (tratamientosDB.isEmpty()) {
                        txtAunNotienescitas.visibility = View.VISIBLE
                    } else {
                        txtAunNotienescitas.visibility = View.GONE
                        val miAdaptador = AdaptadorTratamientos(tratamientosDB)
                        rcvTratamientos.adapter = miAdaptador
                        println("Adaptador size: ${tratamientosDB.size}")
                    }
                }
            } catch (e: Exception) {
                println("Este es el error ${e.message}")
            }
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
            val fechaSeleccionada =
                selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            println("Fecha seleccionada: $fechaSeleccionada")
            txtRecordatorioTratamientos.text = "Recordatorio de tratamientos del día ${
                SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(selectedDate)
            }"
            CoroutineScope(Dispatchers.IO).launch {
                val tratamientosCalendar = obtenerDatosTratamientos()
                val tratamientosFiltrados = tratamientosCalendar.filter { tratamiento ->
                    tratamiento.inicioMedi?.let { inicio ->
                        val inicioTratamiento =
                            inicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        val finalTratamiento =
                            tratamiento.finalMedi?.toInstant()?.atZone(ZoneId.systemDefault())
                                ?.toLocalDate()
                        fechaSeleccionada.isEqual(inicioTratamiento) || (fechaSeleccionada.isAfter(
                            inicioTratamiento
                        ) && fechaSeleccionada.isBefore(
                            finalTratamiento
                        )) || fechaSeleccionada.isEqual(finalTratamiento)
                    } == true
                }
                withContext(Dispatchers.Main) {
                    println("Tratamientos filtrados: ${tratamientosFiltrados.size}")
                    val miAdaptador = AdaptadorTratamientos(tratamientosFiltrados)
                    rcvTratamientos.adapter = miAdaptador
                }
            }
        }
        return root
    }

    suspend fun obtenerDatosTratamientosPeriodo(): List<dataClassIndicaciones> {
        return withContext(Dispatchers.IO) {
            val indicaciones = mutableListOf<dataClassIndicaciones>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement(
                        """SELECT 
    indi.ID_Indicacion, 
    indi.inicioMedi, 
    indi.finalMedi, 
    indi.dosisMedi, 
    indi.medicina, 
    indi.detalleindi, 
    tiem.lapsostiempo, 
    tiem.frecuenciamedi 
FROM 
    tbIndicaciones indi 
INNER JOIN 
    tbTiempos tiem ON indi.id_tiempo = tiem.id_tiempo 
INNER JOIN 
    tbRecetas rec ON indi.id_receta = rec.id_receta 
INNER JOIN 
    tbFichasMedicas fichi ON rec.id_receta = fichi.id_receta 
INNER JOIN 
    tbcitasmedicas citas ON fichi.id_cita = citas.id_cita
INNER JOIN 
    tbUsuarios USUA ON citas.id_usuario = USUA.id_usuario 
WHERE 
    USUA.emailusuario = ?
    AND indi.inicioMedi <= CURRENT_DATE 
    AND indi.finalMedi >= CURRENT_DATE""".trimIndent()
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

    suspend fun obtenerDatosTratamientos(): List<dataClassIndicaciones> {
        return withContext(Dispatchers.IO) {
            val indicaciones = mutableListOf<dataClassIndicaciones>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement(
                        """SELECT 
    indi.ID_Indicacion, 
    indi.inicioMedi, 
    indi.finalMedi, 
    indi.dosisMedi, 
    indi.medicina, 
    indi.detalleindi, 
    tiem.lapsostiempo, 
    tiem.frecuenciamedi 
    FROM 
    tbIndicaciones indi 
    INNER JOIN 
    tbTiempos tiem ON indi.id_tiempo = tiem.id_tiempo 
    INNER JOIN 
    tbRecetas rec ON indi.id_receta = rec.id_receta 
INNER JOIN 
    tbFichasMedicas fichi ON rec.id_receta = fichi.id_receta 
INNER JOIN 
    tbcitasmedicas citas ON fichi.id_cita = citas.id_cita 
INNER JOIN 
    tbUsuarios USUA ON citas.id_usuario = USUA.id_usuario 
WHERE 
    USUA.emailusuario = ?"""
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
        fun newInstance(param1: String, param2: String) = fragment_control_tratamientos().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}