import Modelo.Dia
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R
import java.time.format.DateTimeFormatter

class DiasAdapter(
    private var Datos: List<Dia>,
    private val onDiaSelected: (Dia) -> Unit // Callback para pasar el día seleccionado a la actividad
) : RecyclerView.Adapter<DiasAdapter.ViewHolderDias>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION // Posición seleccionada

    class ViewHolderDias(view: View) : RecyclerView.ViewHolder(view) {
        private val tvFecha: TextView = view.findViewById(R.id.tvFecha)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(dia: Dia, isSelected: Boolean, onClick: () -> Unit) {
            // Formatear la fecha a DD/MM/YYYY
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formattedDate = dia.fecha.format(formatter)

            // Asigna la fecha formateada al TextView
            tvFecha.text = formattedDate

            // Cambiar color de fondo si está seleccionado
            val backgroundColor = if (isSelected) {
                ContextCompat.getColor(itemView.context, R.color.Azul3)
            } else {
                ContextCompat.getColor(itemView.context, android.R.color.transparent)
            }
            itemView.setBackgroundColor(backgroundColor)

            // Establecer el listener para cambiar la selección
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
            // Actualiza la selección
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousPosition) // Notifica el cambio del item previo
            notifyItemChanged(selectedPosition) // Notifica el cambio del item seleccionado

            // Llama al callback con el día seleccionado
            onDiaSelected(dia)
        }
    }
}