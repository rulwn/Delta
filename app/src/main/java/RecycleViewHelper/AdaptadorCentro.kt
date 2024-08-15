package RecycleViewHelper

import Modelo.dataClassCentro
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class AdaptadorCentro(private val items: List<dataClassCentro>) : RecyclerView.Adapter<AdaptadorCentro.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreEspecialidad: TextView = view.findViewById(R.id.txtEspecialidad)
        val direccionSucur: TextView = view.findViewById(R.id.txtDireccionnn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_centro_medico, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nombreEspecialidad.text = item.nombreEspecialidad
        holder.direccionSucur.text = item.direccionSucur
    }
    override fun getItemCount(): Int {
        return items.size
    }
}
