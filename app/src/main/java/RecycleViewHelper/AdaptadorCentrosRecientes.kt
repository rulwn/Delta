package RecycleViewHelper

import Modelo.dataClassFavoritos
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import delta.medic.mobile.R
import delta.medic.mobile.activity_carga_vista
import delta.medic.mobile.activity_login.UserData.userEmail

class AdaptadorCentrosRecientes(private var Datos: MutableList<dataClassFavoritos>): RecyclerView.Adapter<ViewHolderCentroMini>()  {
    var emailUsuario = userEmail
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCentroMini {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_centro_medico_pequeno_normal, parent, false)
        return ViewHolderCentroMini(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderCentroMini, position: Int) {
        val reciente = Datos[position]
        holder.imgClinicaFondo.setImageResource(R.drawable.fotoclinica1)
        holder.imgFotoDoctor.setImageResource(R.drawable.circulo_foto_bienvenida)
        holder.imgFondoTexto.setImageResource(R.drawable.fondoitemcentroxml)

        holder.render(reciente)
        Glide.with(holder.itemView)
            .load(reciente.imgSucusal)
            .centerCrop()
            .into(holder.imgClinicaFondo)

        holder.render(reciente)
        Glide.with(holder.itemView)
            .load(reciente.imgUsuario)
            .circleCrop()
            .into(holder.imgFotoDoctor)

        holder.txtNombreDoctor.text = reciente.nombreUsuario
        holder.txtEspecialidad.text = reciente.nombreTipoSucursal
        holder.imgFavoritos.setImageResource(R.drawable.corazon_favoritos)

        holder.imgFavoritos.visibility = View.GONE

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val doctorFavorito = Datos[position]
            val pantallaDetalle = Intent(context, activity_carga_vista::class.java)
            pantallaDetalle.putExtra("ID_Doctor", doctorFavorito.ID_Doctor)
            pantallaDetalle.putExtra("doctorEmail", doctorFavorito.emailUsuario)
            pantallaDetalle.putExtra("Fav", true)
            context.startActivity(pantallaDetalle)
        }
    }

    fun updateListaRecientes(nuevaLista: List<dataClassFavoritos>) {
        Datos.clear()
        Datos.addAll(nuevaLista)
    }
}