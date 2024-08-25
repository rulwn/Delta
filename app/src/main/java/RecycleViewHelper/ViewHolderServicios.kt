package RecycleViewHelper

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class ViewHolderServicios(view: View) : RecyclerView.ViewHolder(view) {

    val txtnombreServicio : TextView = view.findViewById(R.id.txtnombreServicio)
    val txtCosto : TextView = view.findViewById(R.id.txtCosto)
    val txtAseguradora : TextView = view.findViewById(R.id.txtAseguradora)

}