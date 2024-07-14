package RecycleViewHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class AdaptadorEspecialidades (
    private val specialties: List<String>,
    private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<AdaptadorEspecialidades.SpecialtyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_especialidad, parent, false)
        return SpecialtyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val specialty = specialties[position]
        holder.bind(specialty, clickListener)
    }

    override fun getItemCount(): Int = specialties.size

    class SpecialtyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val specialtyTextView: TextView = itemView.findViewById(R.id.specialtyTextView)

        fun bind(specialty: String, clickListener: (String) -> Unit) {
            specialtyTextView.text = specialty
            itemView.setOnClickListener { clickListener(specialty) }
        }
    }
}