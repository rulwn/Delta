package RecycleViewHelper

import Modelo.dataClassNotis
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class AdaptadorNotis (private val notificaciones: List<dataClassNotis>) : RecyclerView.Adapter<AdaptadorNotis.NotificacionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificacionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_notificaciones, parent, false)
        return NotificacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificacionViewHolder, position: Int) {
        val notificacion = notificaciones[position]
        holder.bind(notificacion)
    }

    override fun getItemCount(): Int = notificaciones.size

    class NotificacionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.notificationTitle)
        private val subtitle: TextView = itemView.findViewById(R.id.notificationSubtitle)
        private val icon: ImageView = itemView.findViewById(R.id.notificationIcon)

        fun bind(notificacion: dataClassNotis) {
            title.text = notificacion.mensaje
            subtitle.text = when (notificacion.tipo) {
                "A" -> "Ver motivos"
                "R" -> "Ver tratamiento"
                "C" -> "Ir a la cita"
                "P" -> "Abrir PDF"
                else -> ""
            }
            icon.setImageResource(when (notificacion.tipo) {
                "A" -> R.drawable.ic_alert
                "R" -> R.drawable.ic_reminder
                "C" -> R.drawable.ic_confirmation
                "P" -> R.drawable.ic_pdf
                else -> R.drawable.ic_notificaciones
            })
        }
    }
}