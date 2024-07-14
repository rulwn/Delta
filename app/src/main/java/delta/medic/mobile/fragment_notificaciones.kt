package delta.medic.mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import Modelo.ClaseConexion
import Modelo.dataClassNotis
import RecycleViewHelper.AdaptadorNotis
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




class fragment_notificaciones : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notificaciones, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.rcvNotis)
        val lbNotis = root.findViewById<TextView>(R.id.lbNotis)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val notificaciones = obtenerNotificaciones()
            withContext(Dispatchers.Main) {
                if (notificaciones.isNotEmpty()) {
                    lbNotis.visibility = View.VISIBLE
                    val adapter = AdaptadorNotis(notificaciones)
                    recyclerView.adapter = adapter
                } else {
                    lbNotis.visibility = View.GONE
                }
            }
        }

        return root
    }

    private suspend fun obtenerNotificaciones(): List<dataClassNotis> {
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
                """.trimIndent())
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

}