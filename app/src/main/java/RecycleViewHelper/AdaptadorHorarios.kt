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

    inner class HorarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewHora: TextView = view.findViewById(R.id.textViewTime)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(hora: Timestamp, isOcupada: Boolean) {
            val localDateTime = hora.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            textViewHora.text = localDateTime.toLocalTime().toString()

            if (isOcupada) {
                itemView.setBackgroundResource(R.drawable.fondo_card_ocupado)
                itemView.isEnabled = false
            } else {
                itemView.setBackgroundResource(R.drawable.fondo_card)
                itemView.isEnabled = true
                itemView.setOnClickListener {
                    onHoraSelected(hora)
                }
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
        holder.bind(hora, isOcupada)
    }

    override fun getItemCount(): Int {
        return horarios.size
    }
}