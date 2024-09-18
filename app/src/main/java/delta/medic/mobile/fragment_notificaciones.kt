package delta.medic.mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import Modelo.ClaseConexion
import Modelo.dataClassNotis
import RecycleViewHelper.AdaptadorNotis
import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import oracle.ons.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.ZoneId
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class fragment_notificaciones : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notificaciones, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.rcvNotis)
        val lbNotis = root.findViewById<TextView>(R.id.lbNotis)
        val noNotificacionesView = root.findViewById<TextView>(R.id.noNotificacionesView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val userId = obtenerUsuarioIdActual(requireContext())
            if (userId != null) {
                val notificaciones = obtenerNotificaciones(userId)
                withContext(Dispatchers.Main) {
                    if (notificaciones.isNotEmpty()) {
                        lbNotis.visibility = View.VISIBLE
                        noNotificacionesView.visibility = View.GONE
                        val adapter = AdaptadorNotis(notificaciones)
                        recyclerView.adapter = adapter
                    } else {
                        noNotificacionesView.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    noNotificacionesView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                println("No se pudo obtener el ID del usuario.")
            }
        }
        return root
    }

    private suspend fun obtenerNotificaciones(userId: Int): List<dataClassNotis> {
        return withContext(Dispatchers.IO) {
            val notificaciones = mutableListOf<dataClassNotis>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    println("Conexión establecida con éxito")
                    val statement = objConexion.prepareStatement("""
                    SELECT 
                        n.ID_Notificacion, n.fechaNoti, n.tipoNoti, n.mensajeNoti, n.flag, 
                        n.ID_TipoNoti, t.nombreTipoNoti
                    FROM tbNotis n
                    JOIN tbTipoNotis t ON n.ID_TipoNoti = t.ID_TipoNoti
                    WHERE n.ID_Usuario = ?
                    """.trimIndent())
                    statement.setInt(1, userId)
                    val resultSet = statement.executeQuery()
                    while (resultSet.next()) {
                        println("Notificación encontrada: ${resultSet.getString("mensajeNoti")}")
                        val notificacion = dataClassNotis(
                            resultSet.getInt("ID_Notificacion"),
                            resultSet.getString("fechaNoti"),
                            resultSet.getString("tipoNoti"),
                            resultSet.getString("mensajeNoti"),
                            resultSet.getString("flag")[0],
                            resultSet.getInt("ID_TipoNoti"),
                            resultSet.getString("nombreTipoNoti")
                        )
                        notificaciones.add(notificacion)
                    }
                    statement.close()
                    objConexion.close()
                } else {
                    println("No se pudo establecer conexión con la base de datos.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
            println("Número de notificaciones: ${notificaciones.size}")
            notificaciones
        }
    }

    private suspend fun obtenerUsuarioIdActual(context: Context): Int? {
        return withContext(Dispatchers.IO) {
            try {
                val sharedPreferences = context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
                val email = sharedPreferences.getString("email", null)

                if (email != null) {
                    val objConexion = ClaseConexion().cadenaConexion()
                    val statement = objConexion?.prepareStatement(
                        "SELECT ID_Usuario FROM tbUsuarios WHERE emailusuario = ?"
                    )
                    statement?.setString(1, email)
                    val resultSet = statement?.executeQuery()

                    if (resultSet != null && resultSet.next()) {
                        return@withContext resultSet.getInt("ID_Usuario")
                    }
                }
            } catch (e: Exception) {
                println("Error al obtener ID del usuario: ${e.message}")
            }
            return@withContext null
        }
    }

    private suspend fun cancelarCita(idCita: Int, userId: Int) {
        try {
            val objConexion = ClaseConexion().cadenaConexion()


            objConexion?.prepareStatement("UPDATE tbCitasMedicas SET estadoCita = 'C' WHERE ID_Cita = ?")?.apply {
                setInt(1, idCita)
                executeUpdate()
            }


            val citaDetailsStmt = objConexion?.prepareStatement("""
SELECT 
    c.horaCita, 
    d.nombreUsuario AS doctorNombre
FROM 
    tbCitasMedicas c
JOIN 
    tbDoctores d ON c.ID_Doctor = d.ID_Doctor
WHERE 
    c.ID_Cita = ?
            """.trimIndent())
            citaDetailsStmt?.setInt(1, idCita)
            val resultSet = citaDetailsStmt?.executeQuery()

            var mensaje = ""
            if (resultSet != null && resultSet.next()) {
                val horaCita = resultSet.getTimestamp("horaCita")
                val doctorNombre = resultSet.getString("doctorNombre")


                val calendar = Calendar.getInstance()
                calendar.time = horaCita


                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                val formattedDateTime = sdf.format(calendar.time)

                mensaje = "Cita cancelada con $doctorNombre el $formattedDateTime"
            }


            objConexion?.prepareStatement("""
                INSERT INTO tbNotis (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti)
                VALUES (SYSDATE, 'A', ?, 'S', ?, 1) 
            """.trimIndent())?.apply {
                setString(1, mensaje)
                setInt(2, userId)
                executeUpdate()
            }

            objConexion?.commit()
            println("Cita cancelada y notificación insertada con éxito.")
        } catch (e: Exception) {
            println("Error al cancelar cita: ${e.message}")
        }
    }

    private suspend fun insertarRecordatorioMedicacion(userId: Int, nombreMedicina: String, hora: String) {
        try {
            val objConexion = ClaseConexion().cadenaConexion()


            val mensaje = "Recuerda tomar tu medicina $nombreMedicina a las $hora."


            objConexion?.prepareStatement("""
                INSERT INTO tbNotis (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti)
                VALUES (SYSDATE, 'R', ?, 'S', ?, 2) 
            """.trimIndent())?.apply {
                setString(1, mensaje)
                setInt(2, userId)
                executeUpdate()
            }

            objConexion?.commit()
            println("Notificación de medicación insertada con éxito.")
        } catch (e: Exception) {
            println("Error al insertar recordatorio de medicación: ${e.message}")
        }
    }
}