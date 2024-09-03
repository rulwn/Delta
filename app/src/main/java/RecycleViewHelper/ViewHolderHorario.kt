package RecycleViewHelper

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class ViewHolderHorario (view: View) : RecyclerView.ViewHolder(view) {
    val textViewTime = view.findViewById<TextView>(R.id.textViewTime)

}