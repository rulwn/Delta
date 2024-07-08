package RecycleViewHelper

import Modelo.dataClassCitas
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class AdaptadorCitas(private var Datos: List<dataClassCitas>): RecyclerView.Adapter<viewHolderCitas>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolderCitas {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_tratamiento, parent, false)

        return viewHolderCitas(vista)
    }

    override fun getItemCount()= Datos.size

    override fun onBindViewHolder(holder: viewHolderCitas, position: Int) {
        val citas = Datos[position]
        val TiempoYDia = holder.itemView.context.getString(R.string.concatenar2, citas.diaCita, citas.horaCita)
        val MotivoYPaciente = holder.itemView.context.getString(R.string.concatenar2, citas.motivo, citas.nombrePaciente)
        val DoctorEspecialidad = holder.itemView.context.getString(R.string.concatenar3, citas.nombreDoctor, citas.apellidoDoctor, citas.especialidad)
        holder.txtTiempoCitas.text = TiempoYDia
        holder.txtMotivo_paciente.text = MotivoYPaciente
        holder.txtDoctor_especialidad.text = DoctorEspecialidad
        holder.imgOpciones.setOnClickListener {

        }

    }
}