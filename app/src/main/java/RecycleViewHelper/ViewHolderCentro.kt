package RecycleViewHelper

import Modelo.dataClassCentro
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import delta.medic.mobile.R

class ViewHolderCentro(view: View) : RecyclerView.ViewHolder(view) {

    val txtNombreDoctor: TextView = view.findViewById(R.id.txtNombreDoctor)
    val txtEspecialidad: TextView = view.findViewById(R.id.txtEspecialidad)
    val txtDireccion: TextView = view.findViewById(R.id.txtDireccionSucur)
    val txtCosto: TextView = view.findViewById(R.id.txtCosto)
    val imgUsuario: ImageView = view.findViewById(R.id.imgUsuario)
    val imgSucursal: ImageView = view.findViewById(R.id.imgSucursal)

    fun render(Datos: dataClassCentro){
        Glide.with(imgUsuario.context).load(Datos.imgUsuario).into(imgUsuario)
        Glide.with(imgSucursal.context).load(Datos.imgSucursal).into(imgSucursal)
    }
}