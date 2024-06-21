package RecycleViewHelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class ViewHolderCentro(view: View) : RecyclerView.ViewHolder(view)
{
    val txtNombreDoctor: TextView = view.findViewById(R.id.txtNombreDoctor)
    val txtEspecialidad : TextView = view.findViewById(R.id.txtEspecialidad)
    val txtDireccion : TextView = view.findViewById(R.id.txtDireccion)
    val txtServicios : TextView = view.findViewById(R.id.txtServicios)
    val imgDoctor : ImageView = view.findViewById(R.id.imgFotoDoctor)
    val imgSucursal : ImageView = view.findViewById(R.id.imgClinicaFondo)


    data class dataClassSucursal(val id: Int, val centroMedico: String)

    class ViewHolderSucursal(view: View) : RecyclerView.ViewHolder(view) {
        val textViewNombreDoctor : TextView = view.findViewById(R.id.txtNombreDoctor)
    }

    class AdaptadorCentro(private var Datos: List<dataClassSucursal>, private val onClick: (dataClassSucursal) -> Unit) : RecyclerView.Adapter<ViewHolderSucursal>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSucursal {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_centro_medico, parent, false)
            return ViewHolderSucursal(view)
        }

        override fun onBindViewHolder(holder: ViewHolderSucursal, position: Int) {
            val sucursal = Datos[position]
            holder.textViewNombreDoctor.text = sucursal.centroMedico
            holder.itemView.setOnClickListener { onClick(sucursal) }
        }

        override fun getItemCount(): Int {
            return Datos.size
        }

        fun actualizarLista(newList: List<dataClassSucursal>) {
            Datos = newList
            notifyDataSetChanged()
        }
    }
}