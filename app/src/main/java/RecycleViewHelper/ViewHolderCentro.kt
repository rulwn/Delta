package RecycleViewHelper

import Modelo.dataClassCentro
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import delta.medic.mobile.R

class ViewHolderCentro(view: View) : RecyclerView.ViewHolder(view) {
    val txtNombreDoctor: TextView = view.findViewById(R.id.txtNombreDoctor)
    val txtEspecialidad: TextView = view.findViewById(R.id.txtEspecialidad)
    val txtDireccion: TextView = view.findViewById(R.id.txtDireccionnn)
    val txtCosto: TextView = view.findViewById(R.id.txtCosto)
    val imgUsuario: ImageView = view.findViewById(R.id.imgFotoUser)
    //val imgSucursal: ImageView = view.findViewById(R.id.imgClinicaFondo)

    fun bind(hotel: dataClassCentro) {
        Glide.with(itemView)
            .load(hotel.imgUsuario)
            .into(imgUsuario)
    }
}
