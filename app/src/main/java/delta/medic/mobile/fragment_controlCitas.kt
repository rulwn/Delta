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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Timestamp
import java.util.Date

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_control_citas, container, false)
        val calendarView = root.findViewById<CalendarView>(R.id.calendarCitas)
        val rcvRecordatoriosCitas = root.findViewById<RecyclerView>(R.id.rcvRecordatoriosCitas)
        rcvRecordatoriosCitas.layoutManager = LinearLayoutManager(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            val citasDB = obtenerDatos()
            withContext(Dispatchers.Main) {
                val miAdaptador = AdaptadorCitas(citasDB)
                rcvRecordatoriosCitas.adapter = miAdaptador
            }
        }
        return root
    }

    private suspend fun obtenerDatos(): List<dataClassCitas>{
        return withContext(Dispatchers.IO) {
            val citas = mutableListOf<dataClassCitas>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.createStatement()
                    val resultset = statement?.executeQuery("SELECT citas.ID_Cita,\n" +
                            "    citas.diacita,\n" +
                            "    citas.horacita,\n" +
                            "    citas.motivo,\n" +
                            "    citas.id_centro,\n" +
                            "    citas.id_paciente,\n" +
                            "    pacs.nombrepaciente,\n" +
                            "    pacs.parentesco,\n" +
                            "    usua.id_usuario,\n" +
                            "    USUA.nombreUsuario,\n" +
                            "    USUA.apellidoUsuario,\n" +
                            "    esp.nombreespecialidad FROM  tbcitasmedicas CITAS \n" +
                            "        INNER JOIN tbcentrosmedicos CENTROS ON CITAS.id_centro=CENTROS.id_centro\n" +
                            "        INNER JOIN tbdoctores DOCS ON CENTROS.id_doctor=DOCS.id_doctor\n" +
                            "        INNER JOIN tbEspecialidades ESP ON docs.id_especialidad = esp.id_especialidad\n" +
                            "        INNER JOIN tbUsuarios USUA ON DOCS.id_usuario = USUA.id_usuario\n" +
                            "        INNER JOIN tbpacientes PACS ON citas.id_paciente = pacs.id_paciente")!!
                    while (resultset.next()) {
                        val ID_Cita = resultset.getInt("ID_Cita")
                        val diaCita = resultset.getDate("diaCita")
                        val horaCita = resultset.getTimestamp("horaCita")
                        val motivo = resultset.getString("motivo")
                        val ID_Centro = resultset.getInt("ID_Centro")
                        val ID_Paciente = resultset.getInt("ID_Paciente")
                        val nombrePaciente = resultset.getString("nombrePaciente")
                        val parentesco = resultset.getString("parentesco")
                        val ID_Usuario = resultset.getInt("ID_Usuario")
                        val nombreDoctor = resultset.getString("nombreUsuario")
                        val apellidoDoctor = resultset.getString("apellidoUsuario")
                        val especialidad = resultset.getString("nombreespecialidad")
                        val cita = dataClassCitas(ID_Cita, diaCita, horaCita, motivo, ID_Centro, ID_Paciente, nombrePaciente, parentesco, ID_Usuario, nombreDoctor, apellidoDoctor, especialidad)
                        citas.add(cita)
                    }
                } else {
                    println("No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: Exception) {
                println("Este es el error ${e.message}")
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
        fun newInstance(param1: String, param2: String) =
            fragment_controlCitas().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}