package RecycleViewHelper

import Modelo.dataClassCentro
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class AdaptadorCentro(private var items: List<dataClassCentro>) : RecyclerView.Adapter<AdaptadorCentro.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreEspecialidad: TextView = view.findViewById(R.id.txtEspecialidad)
        val direccionSucur: TextView = view.findViewById(R.id.txtDireccionnn)
        val nombreUsuario: TextView = view.findViewById(R.id.txtNombre)
        val costo: TextView = view.findViewById(R.id.txtServicios)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_centro_medico, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nombreEspecialidad.text = item.nombreEspecialidad
        holder.direccionSucur.text = item.direccionSucur
        holder.nombreUsuario.text = item.nombreUsuario
        val costoFormatted = String.format("$%.2f", item.costo)
        val infoAdicional = "Costo: "
        holder.costo.text = "$infoAdicional$costoFormatted"
    }

    override fun getItemCount(): Int {
        val itemCount = items.size
        return itemCount
    }

    fun actualizarDatos(nuevosItems: List<dataClassCentro>) {
        items = nuevosItems
        notifyDataSetChanged()
    }
}

