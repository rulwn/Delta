package RecycleViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class ViewHolderCentroMini(view: View) : RecyclerView.ViewHolder(view) {
    val imgClinicaFondo = view.findViewById<ImageView>(R.id.imgSucursal)
    val imgFotoDoctor = view.findViewById<ImageView>(R.id.imgFotoDoctor)
    val imgFondoTexto = view.findViewById<ImageView>(R.id.imgFondoTexto)
    val txtNombreDoctor = view.findViewById<TextView>(R.id.txtNombreDoctor)
    val txtEspecialidad = view.findViewById<TextView>(R.id.txtEspecialidad)
    val imgFavoritos = view.findViewById<ImageView>(R.id.imgFavoritos)
}