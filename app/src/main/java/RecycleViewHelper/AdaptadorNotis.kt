package RecycleViewHelper

import Modelo.dataClassNotis
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper


class AdaptadorNotis(private var notificaciones: MutableList<dataClassNotis>) : RecyclerView.Adapter<AdaptadorNotis.NotificacionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificacionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_notificaciones, parent, false)
        return NotificacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificacionViewHolder, position: Int) {
        val notificacion = notificaciones[position]
        holder.bind(notificacion)
    }

    override fun getItemCount(): Int = notificaciones.size

    fun removeItem(position: Int) {
        notificaciones.removeAt(position)
        notifyItemRemoved(position)
    }

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
                else -> R.drawable.ic_default
            })

            val context = itemView.context
            title.setTextColor(when (notificacion.tipo) {
                "A" -> ContextCompat.getColor(context, R.color.avisos)
                "R" -> ContextCompat.getColor(context, R.color.Azul1)
                "C" -> ContextCompat.getColor(context, R.color.Turquesa1)
                "P" -> ContextCompat.getColor(context, R.color.GrisHumo)
                else -> ContextCompat.getColor(context, R.color.black)
            })
        }
    }

    val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            removeItem(position)
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            val itemView = viewHolder.itemView
            val background = ColorDrawable(Color.RED)
            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            background.draw(c)
            val deleteIcon: Drawable? = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_borrarblanquito)
            val intrinsicWidth = deleteIcon?.intrinsicWidth ?: 0
            val intrinsicHeight = deleteIcon?.intrinsicHeight ?: 0
            val iconMargin = (itemView.height - intrinsicHeight) / 2
            val iconTop = itemView.top + (itemView.height - intrinsicHeight) / 2
            val iconBottom = iconTop + intrinsicHeight
            val iconLeft = itemView.right - iconMargin - intrinsicWidth
            val iconRight = itemView.right - iconMargin

            deleteIcon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            deleteIcon?.draw(c)

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    fun attachSwipeToRecyclerView(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}