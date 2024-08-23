package RecycleViewHelper

import Modelo.dataClassServicios
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class AdaptadorServicios (var Datos: List<dataClassServicios>) : RecyclerView.Adapter<ViewHolderServicios>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderServicios {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_servicios, parent, false)
        return ViewHolderServicios(view)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderServicios, position: Int) {
        val item = Datos[position]
        holder.txtnombreServicio.text = item.nombreServicio
        val costoFormatted = String.format("$%.2f", item.costo)
        val infoAdicional = "Costo: "
        holder.txtCosto.text = "$infoAdicional$costoFormatted"
        holder.txtAseguradora.text = item.nombreAseguradora
    }
}

