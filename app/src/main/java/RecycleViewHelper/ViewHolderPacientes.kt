package RecycleViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class ViewHolderPacientes(view: View) : RecyclerView.ViewHolder(view) {
    val txtNombreConcatenado: TextView = view.findViewById(R.id.txtNombreConcatenado)
    val txtParentesco: TextView = view.findViewById(R.id.txtParentesco)
    val imgFotoPaciente: ImageView = view.findViewById(R.id.imgFotoPaciente)
    val txtAbrirPdf: TextView = view.findViewById(R.id.txtAbrirPdf)
}