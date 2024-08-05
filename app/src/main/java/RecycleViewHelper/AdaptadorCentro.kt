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

class AdaptadorCentro(var Datos: List<dataClassCentro>) : RecyclerView.Adapter<ViewHolderCentro>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCentro {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_centro_medico, parent, false)
        return ViewHolderCentro(view)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderCentro, position: Int) {
        val item = Datos[position]
        holder.txtEspecialidad.text = item.nombreEspecialidad
        holder.txtDireccion.text = item.direccionSucur
        val nombreCompleto = String.format("%s %s", item.nombreUsuario, item.apellidoUsuario)
        holder.txtNombreDoctor.text = nombreCompleto
        val costoFormatted = String.format("$%.2f", item.costo)
        val infoAdicional = "Costo: "
        holder.txtCosto.text = "$infoAdicional$costoFormatted"

        holder.bind(item)
        Glide.with(holder.itemView)
            .load(item.imgUsuario)
            .into(holder.imgUsuario)
    }

    fun actualizarDatos(nuevosItems: List<dataClassCentro>) {
        Datos = nuevosItems
        notifyDataSetChanged()
    }

}

