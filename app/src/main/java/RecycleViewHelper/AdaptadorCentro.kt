package RecycleViewHelper

import Modelo.dataClassCentro
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import delta.medic.mobile.R
import delta.medic.mobile.activity_vistadoctores

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
        val nombreCompleto = String.format("Dr. %s %s", item.nombreUsuario, item.apellidoUsuario)
        holder.txtNombreDoctor.text = nombreCompleto

        holder.render(item)
        Glide.with(holder.itemView)
            .load(item.imgSucursal)
            .into(holder.imgSucursal)

        holder.render(item)
        Glide.with(holder.itemView)
            .load(item.imgUsuario)
            .circleCrop()
            .into(holder.imgUsuario)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val doctor = Datos[position]
            val pantallaDetalle = Intent(context, activity_vistadoctores::class.java)
            pantallaDetalle.putExtra("ID_Doctor", doctor.ID_Doctor)
            context.startActivity(pantallaDetalle)
        }

    }
}