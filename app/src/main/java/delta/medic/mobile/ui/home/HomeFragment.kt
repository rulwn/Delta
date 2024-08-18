package delta.medic.mobile.ui.homeç

import Modelo.ClaseConexion
import Modelo.dataClassCitas
import Modelo.dataClassUsuario
import RecycleViewHelper.AdaptadorCitas
import RecycleViewHelper.AdaptadorTratamientos
import RecycleViewHelper.AdaptadorTratamientosChiquito
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R
import delta.medic.mobile.activity_login.UserData.userEmail
import delta.medic.mobile.databinding.FragmentHomeBinding
import delta.medic.mobile.fragment_controlCitas
import delta.medic.mobile.fragment_control_tratamientos
import delta.medic.mobile.fragment_usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.SQLException

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val txtBienvenido = root.findViewById<TextView>(R.id.txtBienvenido)
        val txtaunnotienestratamientos =
            root.findViewById<TextView>(R.id.txtaunnotienesTratamientos)
        val rcvTratamientos = root.findViewById<RecyclerView>(R.id.rcvTratamientosMini)
        val fragmentControlTratamientos = fragment_control_tratamientos()
        val rcvRecordatoriosCitas = root.findViewById<RecyclerView>(R.id.rcvProximaCita)
        val txtaunotienescitas = root.findViewById<TextView>(R.id.txtaunnotienescitas)
        val txtAunotienescentros = root.findViewById<TextView>(R.id.txtaunnotienescentros)
        rcvRecordatoriosCitas.overScrollMode = View.OVER_SCROLL_NEVER
        rcvTratamientos.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rcvRecordatoriosCitas.layoutManager = NoScrollLinearLayoutManager(requireContext())
        loadData(txtBienvenido)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val tratamientosDB = fragmentControlTratamientos.obtenerDatosTratamientos()
                withContext(Dispatchers.Main) {
                    if (tratamientosDB.isEmpty()) {
                        txtaunnotienestratamientos.visibility = View.VISIBLE
                    } else {
                        txtaunnotienestratamientos.visibility = View.GONE
                        val miAdaptador = AdaptadorTratamientosChiquito(tratamientosDB)
                        rcvTratamientos.adapter = miAdaptador
                    }
                }
            } catch (e: Exception) {
                println("Error al obtener los tratamientos: ${e.message}")
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val citasDB = obtenerDatos()
                withContext(Dispatchers.Main) {
                    if (citasDB.isEmpty()) {
                        txtaunotienescitas.visibility = View.VISIBLE
                    } else {
                        txtaunotienescitas.visibility = View.GONE
                        val miAdaptador = AdaptadorCitas(citasDB)
                        rcvRecordatoriosCitas.adapter = miAdaptador
                    }
                }
            } catch (e: Exception) {
                println("Error al obtener los datos de la base de datos: ${e.message}")
            }
        }
        return root
    }

    class NoScrollLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
        override fun canScrollVertically(): Boolean {
            return false
        }
    }

    private suspend fun obtenerDatos(): List<dataClassCitas> {
        return withContext(Dispatchers.IO) {
            val citas = mutableListOf<dataClassCitas>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement(
                        """
    SELECT * FROM (
        SELECT 
            citas.ID_Cita,
            citas.diacita,
            citas.horacita,
            citas.motivo,
            citas.estadoCita,
            citas.id_centro,
            citas.id_paciente,
            pacs.nombrepaciente,
            pacs.parentesco,
            usua.id_usuario,
            usua.nombreUsuario,
            usua.apellidoUsuario,
            esp.nombreespecialidad
        FROM tbcitasmedicas CITAS 
        INNER JOIN tbcentrosmedicos CENTROS ON CITAS.id_centro=CENTROS.id_centro
        INNER JOIN tbdoctores DOCS ON CENTROS.id_doctor=DOCS.id_doctor
        INNER JOIN tbEspecialidades ESP ON DOCS.id_especialidad = ESP.id_especialidad
        INNER JOIN tbUsuarios USUA ON DOCS.id_usuario = USUA.id_usuario
        INNER JOIN tbpacientes PACS ON CITAS.id_paciente = PACS.id_paciente
        WHERE USUA.emailUsuario = ?
        AND CITAS.diacita >= CURRENT_DATE
        AND CITAS.estadoCita = 'A'
        ORDER BY CITAS.diacita ASC, CITAS.horacita ASC
    )
    WHERE ROWNUM = 1
    """
                    )!!

                    statement.setString(1, userEmail)
                    val resultset = statement.executeQuery()
                    while (resultset.next()) {
                        val ID_Cita = resultset.getInt("ID_Cita")
                        val diaCita = resultset.getDate("diaCita")
                        val horaCita = resultset.getTimestamp("horaCita")
                        val motivo = resultset.getString("motivo")
                        val estadoCita = resultset.getString("estadoCita")
                        val ID_Centro = resultset.getInt("ID_Centro")
                        val ID_Paciente = resultset.getInt("ID_Paciente")
                        val nombrePaciente = resultset.getString("nombrePaciente")
                        val parentesco = resultset.getString("parentesco")
                        val ID_Usuario = resultset.getInt("ID_Usuario")
                        val nombreDoctor = resultset.getString("nombreUsuario")
                        val apellidoDoctor = resultset.getString("apellidoUsuario")
                        val especialidad = resultset.getString("nombreespecialidad")
                        val cita = dataClassCitas(
                            ID_Cita,
                            diaCita,
                            horaCita,
                            motivo,
                            estadoCita,
                            ID_Centro,
                            ID_Paciente,
                            nombrePaciente,
                            parentesco,
                            ID_Usuario,
                            nombreDoctor,
                            apellidoDoctor,
                            especialidad
                        )
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

    fun loadData(lbNombre: TextView) {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                val fragmentUsuario = fragment_usuario()
                val user = fragmentUsuario.GetUserParameters(userEmail)
                //Estos campos al estar con map pondrá  "[]" al inicio y al final
                val nombreUsuario = user.map { it.nombreUsuario }

                withContext(Dispatchers.Main) {
                    //Para solucionarlo se coloca replace
                    lbNombre.setText(
                        " Bienvenido ${
                            nombreUsuario.toString().replace("[", "").replace("]", "")
                        }"
                    )
                }
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}