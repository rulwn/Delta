package RecycleViewHelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class AdaptadorHorarios(
    private val items: Array<String>, // Horarios disponibles
    private val horasOcupadas: List<String>, // Horas que ya están ocupadas
    private val onHoraSelected: (String) -> Unit // Callback para cuando se selecciona una hora
) : RecyclerView.Adapter<AdaptadorHorarios.ViewHolderHorario>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class ViewHolderHorario(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTime: TextView = view.findViewById(R.id.textViewTime)
        val cardView: CardView = view.findViewById(R.id.cardViewHorario)

        fun bind(hora: String, isOcupada: Boolean, isSelected: Boolean) {
            textViewTime.text = hora

            when {
                isOcupada -> {
                    // Si la hora está ocupada, usa el drawable de fondo rojo y deshabilita el ítem
                    cardView.setBackgroundResource(R.drawable.fondo_card_ocupado)
                    textViewTime.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                    itemView.isClickable = false
                }
                isSelected -> {
                    // Si la hora está seleccionada, usa el drawable de fondo seleccionado
                    cardView.setBackgroundResource(R.drawable.degradado_card)
                    textViewTime.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.black))
                }
                else -> {
                    // Si la hora está disponible pero no seleccionada, usa el drawable estándar
                    cardView.setBackgroundResource(R.drawable.fondo_card)
                    textViewTime.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.black))
                    itemView.isClickable = true

                    itemView.setOnClickListener {
                        onHoraSelected(hora)
                        val previousPosition = selectedPosition
                        selectedPosition = adapterPosition
                        notifyItemChanged(previousPosition)
                        notifyItemChanged(selectedPosition)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHorario {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_horario, parent, false)
        return ViewHolderHorario(view)
    }

    override fun onBindViewHolder(holder: ViewHolderHorario, position: Int) {
        val hora = items[position]
        val isOcupada = horasOcupadas.contains(hora)
        holder.bind(hora, isOcupada, holder.adapterPosition == selectedPosition)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}