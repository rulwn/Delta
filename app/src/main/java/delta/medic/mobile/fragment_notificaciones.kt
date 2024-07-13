package delta.medic.mobile

import Modelo.AdaptadorNotis
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import Modelo.ClaseConexion
import Modelo.dataClassNotis
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



/**
 * A simple [Fragment] subclass.
 * Use the [fragment_notificaciones.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_notificaciones : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdaptadorNotis



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notificaciones, container, false)

        recyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadNotifications()

        return root
    }

    private fun loadNotifications() {
        CoroutineScope(Dispatchers.Main).launch {
            val notifications = withContext(Dispatchers.IO) {
                getNotificationsFromDatabase()
            }
            adapter = AdaptadorNotis(notifications)
            recyclerView.adapter = adapter
        }
    }

    private suspend fun getNotificationsFromDatabase(): List<dataClassNotis> {
        return withContext(Dispatchers.IO) {
            val notifications = mutableListOf<dataClassNotis>()
            try {
                val connection = ClaseConexion().cadenaConexion()
                if (connection != null) {
                    val statement = connection.prepareStatement("SELECT ID_Notificacion, mensajeNoti AS title, tipoNoti AS type, TO_CHAR(fechaNoti, 'YYYY-MM-DD') AS date, ID_Usuario, '' AS subtitle FROM tbNotis WHERE ID_Usuario = ?")!!
                    statement.setInt(1, 1) // aqui asumo q 1 es el numero de tipo de usuario
                    val resultSet = statement.executeQuery()
                    while (resultSet.next()) {
                        val idNotis = resultSet.getInt("ID_Notificacion")
                        val title = resultSet.getString("title")
                        val subtitle = resultSet.getString("subtitle")
                        val type = resultSet.getString("type")
                        val date = resultSet.getString("date")
                        val userId = resultSet.getInt("ID_Usuario")
                        val notification = dataClassNotis(idNotis, title, subtitle, type, date, userId)
                        notifications.add(notification)
                    }
                    resultSet.close()
                    statement.close()
                    connection.close()
                } else {
                    println("No se pudo establecer conexión con la base de datos.")
                }
            } catch (e: Exception) {
                println("Este es el error ${e.message}")
            }
            notifications
        }
    }


}