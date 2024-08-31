package RecycleViewHelper

import Modelo.dataClassResena
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import delta.medic.mobile.R

class AdaptadorResenas(private var Datos: MutableList<dataClassResena>) : RecyclerView.Adapter<ViewHolderResenas>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderResenas {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resenas, parent, false)
        return ViewHolderResenas(view)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderResenas, position: Int) {
        val item = Datos[position]
        val nombreCompleto = "${item.nombreUsuario?.trim() ?: ""} ${item.apellidoUsuario?.trim() ?: ""}".trim()
        holder.txtUserName.text = nombreCompleto
        holder.txtReview.text = item.comentario
        holder.ratingBar.rating = item.promEstrellas
        holder.render(item)

        Glide.with(holder.itemView)
            .load(item.imgUsuario)
            .circleCrop()
            .into(holder.imgProfile)

        holder.btnBorrar.setOnClickListener {
            val popupMenu = PopupMenu(holder.itemView.context, holder.btnBorrar)
            popupMenu.inflate(R.menu.menu_opciones)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_reseñas -> {

                       true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    fun agregarItem(nuevoItem: dataClassResena) {
        Datos.add(nuevoItem)
        notifyItemInserted(Datos.size)
    }

    fun actualizarLista(nuevaLista: List<dataClassResena>) {
        Datos.clear()
        Datos.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}