package RecycleViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class ViewHolderMisRecetas(view: View) : RecyclerView.ViewHolder(view) {
    val txtNombreDoctor: TextView = view.findViewById(R.id.txtNombreDoctor)
    val txtFechaReceta: TextView = view.findViewById(R.id.txtFechaReceta)
    val txtAbrirPdf: TextView = view.findViewById(R.id.txtAbrirPdf)
}