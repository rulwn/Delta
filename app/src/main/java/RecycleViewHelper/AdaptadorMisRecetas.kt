package RecycleViewHelper

import Modelo.dataClassMisRecetas
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class AdaptadorMisRecetas(private var Datos: List<dataClassMisRecetas>): RecyclerView.Adapter<ViewHolderMisRecetas>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMisRecetas {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_recetas_pdf, parent, false)
        return ViewHolderMisRecetas(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderMisRecetas, position: Int) {
        val receta = Datos[position]

        holder.txtNombreDoctor.text = receta.nombreDoctor + " " + receta.apellidoDoctor
        holder.txtFechaReceta.text = receta.fechaReceta
        holder.txtAbrirPdf.setOnClickListener {}

        /*
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val pantallaDetalle = Intent(context, activity_vistadoctores::class.java)
            pantallaDetalle.putExtra("ID_Doctor", doctorFavorito.ID_Doctor)
            pantallaDetalle.putExtra("Fav", true)
            context.startActivity(pantallaDetalle)
        }*/



    }
}