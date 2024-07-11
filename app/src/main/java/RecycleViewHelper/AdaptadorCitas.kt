package RecycleViewHelper

import Modelo.dataClassCitas
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R
import java.text.SimpleDateFormat

class AdaptadorCitas(private var Datos: List<dataClassCitas>): RecyclerView.Adapter<viewHolderCitas>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolderCitas {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_citas, parent, false)

        return viewHolderCitas(vista)
    }

    override fun getItemCount()= Datos.size

    override fun onBindViewHolder(holder: viewHolderCitas, position: Int) {
        val citas = Datos[position]
        val dayFormat = SimpleDateFormat("dd")
        val monthFormat = SimpleDateFormat("MM")
        val timeFormat = SimpleDateFormat("HH:mm")

        val diaCitaString = dayFormat.format(citas.diaCita)
        val mesCitaString = monthFormat.format(citas.diaCita)
        val horaCitaString = timeFormat.format(citas.horaCita)

        val diaYMes = "$diaCitaString/$mesCitaString"

        val TiempoYDia = "${diaYMes} ${horaCitaString}"
        val MotivoYPaciente = "${citas.motivo}, ${citas.nombrePaciente}"
        val DoctorEspecialidad = "${citas.nombreDoctor} ${citas.apellidoDoctor} - ${citas.especialidad}"
        holder.txtTiempoCitas.text = TiempoYDia
        holder.txtMotivo_paciente.text = MotivoYPaciente
        holder.txtDoctor_especialidad.text = DoctorEspecialidad
        holder.imgOpciones.setOnClickListener {

        }
    }
}