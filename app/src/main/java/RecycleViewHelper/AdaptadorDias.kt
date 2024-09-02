import Modelo.Dia
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R
import java.time.format.DateTimeFormatter

class DiasAdapter(
    private var Datos: List<Dia>,
    private val onDiaSelected: (Dia) -> Unit,
    private val fechaSeleccionadaTextView: TextView) : RecyclerView.Adapter<DiasAdapter.ViewHolderDias>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    class ViewHolderDias(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardDia)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(dia: Dia, isSelected: Boolean, onClick: () -> Unit) {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formattedDate = dia.fecha.format(formatter)
            val tvDisponibilidad: TextView = itemView.findViewById(R.id.tvDisponibilidad)
            val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
            tvFecha.text = formattedDate

            val backgroundColor = if (isSelected) {
                ContextCompat.getColor(itemView.context, R.color.Turquesa1)
            } else {
                ContextCompat.getColor(itemView.context, R.color.white)
            }
            cardView.setCardBackgroundColor(backgroundColor)

            val textColor = if (isSelected) {
                ContextCompat.getColor(itemView.context, android.R.color.white)
            } else {
                ContextCompat.getColor(itemView.context, android.R.color.black)
            }
            tvFecha.setTextColor(textColor)

            val textColor2 = if (isSelected) {
                ContextCompat.getColor(itemView.context, android.R.color.white)
            } else {
                ContextCompat.getColor(itemView.context, android.R.color.black)
            }
            tvDisponibilidad.setTextColor(textColor2)
            itemView.setOnClickListener {
                onClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDias {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_dia, parent, false)
        return ViewHolderDias(view)
    }

    override fun getItemCount() = Datos.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolderDias, position: Int) {
        val dia = Datos[position]
        val isSelected = holder.adapterPosition == selectedPosition

        holder.bind(dia, isSelected) {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            fechaSeleccionadaTextView.text = "Fecha seleccionada: ${dia.fecha.format(formatter)}"

            onDiaSelected(dia)
        }
    }
}