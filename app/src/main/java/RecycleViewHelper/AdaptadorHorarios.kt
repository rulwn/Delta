package RecycleViewHelper

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R
import java.sql.Timestamp
import java.time.ZoneId

class AdaptadorHorarios(
    private val horarios: List<Timestamp>,
    private val horasOcupadas: Array<String>,
    private val onHoraSelected: (Timestamp) -> Unit
) : RecyclerView.Adapter<AdaptadorHorarios.HorarioViewHolder>() {

    private var horaSeleccionada: Timestamp? = null // Variable para rastrear la hora seleccionada

    inner class HorarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewHora: TextView = view.findViewById(R.id.textViewTime)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(hora: Timestamp, isSelected: Boolean) {
            val localDateTime = hora.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            textViewHora.text = localDateTime.toLocalTime().toString()

            // Aquí se diferencia entre los ítems seleccionados y no seleccionados
            if (isSelected) {
                itemView.setBackgroundResource(R.drawable.degradado_card) // Fondo para el ítem seleccionado
            } else {
                itemView.setBackgroundResource(R.drawable.fondo_card) // Fondo para ítems no seleccionados
            }

            // Aquí actualizas la selección y notificas al adaptador
            itemView.setOnClickListener {
                horaSeleccionada = hora // Actualiza la hora seleccionada
                notifyDataSetChanged()  // Notifica al adaptador que debe redibujar
                onHoraSelected(hora)    // Ejecuta el callback para la hora seleccionada
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_horario, parent, false)
        return HorarioViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HorarioViewHolder, position: Int) {
        val hora = horarios[position]
        val localDateTime = hora.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val horaString = localDateTime.toLocalTime().toString()
        val isOcupada = horasOcupadas.contains(horaString)

        // Verificar si la hora es la seleccionada
        val isSelected = hora == horaSeleccionada

        holder.bind(hora, isSelected) // Pasar la selección al bind
    }

    override fun getItemCount(): Int {
        return horarios.size
    }
}
