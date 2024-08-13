package RecycleViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class ViewHolderTratamientosChiquito(view: View) : RecyclerView.ViewHolder(view) {
        val txtTiempo: TextView = view.findViewById(R.id.txtTiempo)
        val imgOpciones: ImageView = view.findViewById(R.id.imgOpciones)
        val cardview: CardView = view.findViewById(R.id.cardview)
}
