package RecycleViewHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class AdaptadorRecientes (private val searchList: List<String>) :
    RecyclerView.Adapter<AdaptadorRecientes.ViewHolder>()  {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSearchText: TextView = itemView.findViewById(R.id.tvSearchText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_busqueda_reciente, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSearchText.text = searchList[position]
    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}