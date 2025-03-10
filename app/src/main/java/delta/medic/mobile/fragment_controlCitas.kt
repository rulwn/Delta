package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassCitas
import RecycleViewHelper.AdaptadorCitas
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
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.Calendar
import delta.medic.mobile.activity_login.UserData.userEmail as sentEmail
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_controlCitas.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_controlCitas : Fragment() {
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_control_citas, container, false)
        val calendarView = root.findViewById<CalendarView>(R.id.calendarCitas)
        val rcvRecordatoriosCitas = root.findViewById<RecyclerView>(R.id.rcvRecordatoriosCitas)
        val txtRecordatorioCitas = root.findViewById<TextView>(R.id.txtRecordatorioCitas)
        val txtAunNotienescitas = root.findViewById<TextView>(R.id.txtAunNotienescitas)
        rcvRecordatoriosCitas.layoutManager = LinearLayoutManager(requireContext())
        txtAunNotienescitas.visibility = View.GONE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val citasDB = obtenerDatosCitas()
                withContext(Dispatchers.Main) {
                    if (citasDB.isEmpty()) {
                        txtAunNotienescitas.visibility = View.VISIBLE
                    } else {
                        txtAunNotienescitas.visibility = View.GONE
                        val miAdaptador = AdaptadorCitas(citasDB)
                        rcvRecordatoriosCitas.adapter = miAdaptador
                    }
                }
            } catch (e: Exception) {
                println("Error al obtener los datos de la base de datos: ${e.message}")
            }
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            try {
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }.time
                txtRecordatorioCitas.text = "Recordatorio de citas del día ${
                    SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(selectedDate)
                }"
                val fechaFormateada = java.sql.Date(selectedDate.time)

                CoroutineScope(Dispatchers.IO).launch {
                    val citasDelDia = obtenerDiaCita(fechaFormateada)
                    withContext(Dispatchers.Main) {
                            txtAunNotienescitas.visibility = View.GONE
                            val miAdaptador = AdaptadorCitas(citasDelDia)
                            rcvRecordatoriosCitas.adapter = miAdaptador

                    }
                }
            } catch (e: Exception) {
                println("Error al obtener los datos de la base de datos: ${e.message}")
            }
        }
        return root
    }

    private suspend fun obtenerDiaCita(Fecha: java.sql.Date): List<dataClassCitas> {
        return withContext(Dispatchers.IO) {
            val DiaCita = mutableListOf<dataClassCitas>()
            var objConexion: Connection? = null
            var statement: PreparedStatement? = null
            var resultset: ResultSet? = null

            try {
                objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    statement = objConexion.prepareStatement(
                        """
SELECT 
                            citas.ID_Cita,
                            citas.diacita,
                            citas.horacita,
                            citas.motivo,
                            citas.estadoCita,
                            citas.id_usuario,
                            usua.nombreUsuario,
                            usua.apellidoUsuario,
                            esp.nombreespecialidad
                        FROM 
                            tbcitasmedicas citas
                        INNER JOIN 
                            tbdoctores docs ON citas.id_doctor = docs.id_doctor
                        INNER JOIN 
                            tbEspecialidades esp ON docs.id_especialidad = esp.id_especialidad
                        INNER JOIN 
                            tbUsuarios usua ON docs.id_usuario = usua.id_usuario
                        INNER JOIN 
                            tbUsuarios us ON citas.id_usuario = us.id_usuario
                        WHERE  
    us.emailUsuario = ?
    AND citas.diacita = ?
                        """.trimIndent()
                    )
                    statement.setString(1, userEmail)
                    statement.setDate(2, Fecha)
                    resultset = statement.executeQuery()

                    while (resultset.next()) {
                        val cita = dataClassCitas(
                            resultset.getInt("ID_Cita"),
                            resultset.getDate("diaCita"),
                            resultset.getTimestamp("horaCita"),
                            resultset.getString("motivo"),
                            resultset.getString("EstadoCita"),
                            resultset.getInt("ID_Usuario"),
                            resultset.getString("nombreUsuario"),
                            resultset.getString("apellidoUsuario"),
                            resultset.getString("nombreespecialidad")
                        )
                        DiaCita.add(cita)
                    }
                } else {
                    println("No se pudo establecer conexión con la base de datos.")
                }
            } catch (e: Exception) {
                println("Este es el error: ${e.message}")
            } finally {
                resultset?.close()
                statement?.close()
                objConexion?.close()
            }
            DiaCita
        }
    }


    suspend fun obtenerDatosCitas(): List<dataClassCitas> {
        return withContext(Dispatchers.IO) {
            val citas = mutableListOf<dataClassCitas>()
            var objConexion: Connection? = null
            var statement: PreparedStatement? = null
            var resultset: ResultSet? = null

            try {
                objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    statement = objConexion.prepareStatement(
                        """
SELECT 
                            citas.ID_Cita,
                            citas.diacita,
                            citas.horacita,
                            citas.motivo,
                            citas.estadoCita,
                            citas.id_usuario,
                            usua.nombreUsuario,
                            usua.apellidoUsuario,
                            esp.nombreespecialidad
                        FROM 
                            tbcitasmedicas citas
                        INNER JOIN 
                            tbdoctores docs ON citas.id_doctor = docs.id_doctor
                        INNER JOIN 
                            tbEspecialidades esp ON docs.id_especialidad = esp.id_especialidad
                        INNER JOIN 
                            tbUsuarios usua ON docs.id_usuario = usua.id_usuario
                        INNER JOIN 
                            tbUsuarios us ON citas.id_usuario = us.id_usuario
                        WHERE 
                            us.emailUsuario = ?
                            AND citas.diacita >= CURRENT_DATE
                            AND citas.estadoCita = 'A'
                        ORDER BY 
                            citas.diacita ASC, 
                            citas.horacita ASC
    """)
                    statement.setString(1, userEmail)
                    resultset = statement.executeQuery()

                    while (resultset.next()) {
                        val cita = dataClassCitas(
                            resultset.getInt("ID_Cita"),
                            resultset.getDate("diaCita"),
                            resultset.getTimestamp("horaCita"),
                            resultset.getString("motivo"),
                            resultset.getString("EstadoCita"),
                            resultset.getInt("ID_Usuario"),
                            resultset.getString("nombreUsuario"),
                            resultset.getString("apellidoUsuario"),
                            resultset.getString("nombreespecialidad")
                        )
                        citas.add(cita)
                    }
                } else {
                    println("No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: Exception) {
                println("Este es el error: ${e.message}")
            } finally {
                resultset?.close()
                statement?.close()
                objConexion?.close()
            }
            citas
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_controlCitas.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = fragment_controlCitas().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}