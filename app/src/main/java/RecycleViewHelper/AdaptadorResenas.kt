package RecycleViewHelper

import Modelo.dataClassResena
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import delta.medic.mobile.R

class AdaptadorResenas (var Datos: List<dataClassResena>) : RecyclerView.Adapter<ViewHolderResenas>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderResenas {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resenas, parent, false)
        return ViewHolderResenas(view)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderResenas, position: Int) {
        val item = Datos[position]
        val nombreCompleto = String.format("%s %s", item.nombreUsuario, item.apellidoUsuario)
        holder.txtUserName.text = nombreCompleto
        holder.render(item)
        Glide.with(holder.itemView)
            .load(item.imgUsuario)
            .circleCrop()git
            .into(holder.imgProfile)

        holder.edtReview.text = item.comentario
        holder.ratingBar.rating = item.promEstrellas
    }

}