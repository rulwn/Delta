package Modelo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R


class AdaptadorNotis (private val notifications: List<dataClassNotis>) :
    RecyclerView.Adapter<AdaptadorNotis.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_notificaciones, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]
        holder.bind(notification)
    }

    override fun getItemCount(): Int = notifications.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.notificationTitle)
        private val subtitle: TextView = itemView.findViewById(R.id.notificationSubtitle)
        private val icon: ImageView = itemView.findViewById(R.id.notificationIcon)

        fun bind(notification: dataClassNotis) {
            title.text = notification.title
            subtitle.text = notification.subtitle
            when (notification.tiponoti) {
                "A" -> {  //aqui asumo q A es de alerta
                    title.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.holo_red_dark))
                    icon.setImageResource(R.drawable.ic_alert)
                }
                "R" -> {  // aqui que R es de recordatorio
                    title.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.holo_blue_dark))
                    icon.setImageResource(R.drawable.ic_reminder)
                }

            }
        }
    }
}