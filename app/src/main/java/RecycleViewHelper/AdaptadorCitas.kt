package RecycleViewHelper

import Modelo.ClaseConexion
import Modelo.dataClassCitas
import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.graphics.Color
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import delta.medic.mobile.MainActivity
import delta.medic.mobile.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import android.app.NotificationManager


class AdaptadorCitas(private var Datos: List<dataClassCitas>) :
    RecyclerView.Adapter<viewHolderCitas>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolderCitas {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_citas, parent, false)

        return viewHolderCitas(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: viewHolderCitas, position: Int) {
        val citas = Datos[position]
        val dayFormat = SimpleDateFormat("dd")
        val monthFormat = SimpleDateFormat("MM")
        val timeFormat = SimpleDateFormat("HH:mm")

        val diaCitaString = dayFormat.format(citas.diaCita)
        val mesCitaString = monthFormat.format(citas.diaCita)
        val horaCitaString = timeFormat.format(citas.horaCita)

        val diaYMes = "$diaCitaString/$mesCitaString"
        val TiempoYDia = "${diaYMes} ${horaCitaString}"
        val MotivoYPaciente = citas.motivo
        val DoctorEspecialidad =
            "${citas.nombreDoctor} ${citas.apellidoDoctor} - ${citas.especialidad}"
        holder.txtTiempoCitas.text = TiempoYDia
        holder.txtMotivo_paciente.text = MotivoYPaciente
        holder.txtDoctor_especialidad.text = DoctorEspecialidad

        holder.imgOpciones.setOnClickListener {
            val popupMenu = PopupMenu(holder.itemView.context, holder.imgOpciones)
            popupMenu.inflate(R.menu.menu_citas)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_cancelar -> {
                        mostrarDialogoCancelacion(holder, citas)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private fun mostrarDialogoCancelacion(holder: viewHolderCitas, citas: dataClassCitas) {
        val layout = LinearLayout(holder.itemView.context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 40)
        }
        val icon = ImageView(holder.itemView.context).apply {
            setImageResource(R.drawable.ic_cancelarcita)
            layoutParams = LinearLayout.LayoutParams(
                300, 300
            ).apply {
                gravity = android.view.Gravity.CENTER_HORIZONTAL
            }
            setPadding(0, 0, 0, 40)
        }

        val message = TextView(holder.itemView.context).apply {
            text = "¿Estás seguro que quieres cancelar la cita?"
            setTextColor(Color.parseColor("#E33E3E"))
            textSize = 26f
            gravity = android.view.Gravity.CENTER
        }

        layout.addView(icon)
        layout.addView(message)

        val builder = AlertDialog.Builder(holder.itemView.context)
        builder.setView(layout)
        builder.setPositiveButton("Sí", null)
        builder.setNegativeButton("No", null)

        val dialog = builder.create()
        dialog.show()

        val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setTextColor(Color.parseColor("#E33E3E"))
        positiveButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                cancelarCita(citas.ID_Cita, citas.ID_Usuario, "${citas.nombreDoctor} ${citas.apellidoDoctor}", holder.itemView.context)
            }
            dialog.dismiss()
        }

        val negativeButton: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        negativeButton.setTextColor(Color.parseColor("#E33E3E"))
        negativeButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    private suspend fun cancelarCita(idCita: Int, idUsuario: Int, nombreDoctor: String, context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement(
                        """
                    UPDATE tbcitasmedicas
                    SET estadoCita = 'C'
                    WHERE ID_Cita = ?
                """
                    )
                    statement?.setInt(1, idCita)
                    val rowsAffected = statement?.executeUpdate()
                    if (rowsAffected != null && rowsAffected > 0) {
                        println("Cita con ID $idCita ha sido cancelada.")
                        insertarNotificacionCancelacion(idUsuario, nombreDoctor)


                        enviarNotificacion(context, "Cita Cancelada", "Cita con el doctor $nombreDoctor ha sido cancelada.")
                    } else {
                        println("No se encontró ninguna cita con ID $idCita.")
                    }
                    statement?.close()
                    objConexion.close()
                } else {
                    println("No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: Exception) {
                println("Error al cancelar cita: ${e.message}")
            }
        }
    }

    private suspend fun insertarNotificacionCancelacion(idUsuario: Int, nombreDoctor: String) {
        withContext(Dispatchers.IO) {
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement(
                        """
                        INSERT INTO tbNotis (fechaNoti, tipoNoti, mensajeNoti, flag, ID_Usuario, ID_TipoNoti)
                        VALUES (SYSDATE, 'A', 'Cita cancelada con el doctor $nombreDoctor', 'S', ?, 1)
                    """
                    )
                    statement?.setInt(1, idUsuario)
                    statement?.executeUpdate()
                    statement?.close()
                    objConexion.close()
                } else {
                    println("No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: Exception) {
                println("Error al insertar notificación: ${e.message}")
            }
        }
    }
    fun enviarNotificacion(context: Context, titulo: String, mensaje: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }


        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        val builder = NotificationCompat.Builder(context, "general_notifications")
            .setSmallIcon(R.drawable.ic_app)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        with(NotificationManagerCompat.from(context)) {
            notify(1001, builder.build())
        }
    }
}