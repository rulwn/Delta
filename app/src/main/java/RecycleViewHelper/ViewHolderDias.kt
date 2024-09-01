package RecycleViewHelper

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class ViewHolderDias(view: View) : RecyclerView.ViewHolder(view) {
    val tvFecha = view.findViewById<TextView>(R.id.tvFecha)
    val tvDisponibilidad = view.findViewById<TextView>(R.id.tvDisponibilidad)
}