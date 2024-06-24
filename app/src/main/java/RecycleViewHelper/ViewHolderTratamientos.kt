package RecycleViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class ViewHolderTratamientos(view: View) : RecyclerView.ViewHolder(view) {
        val txtTiempo: TextView = view.findViewById(R.id.txtTiempo)
        val txtNombreMedicina: TextView = view.findViewById(R.id.txtNombreMedicina)
        val txtDosis: TextView = view.findViewById(R.id.txtDosis)
        val txtDetallesIndicaciones: TextView = view.findViewById(R.id.txtDetallesIndicaciones)
        val imgOpciones: ImageView = view.findViewById(R.id.imgOpciones)
}