package RecycleViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R

class viewHolderCitas(view: View) : RecyclerView.ViewHolder(view){
    val txtTiempoCitas = view.findViewById<TextView>(R.id.txtTiempoCitas)
    val txtDoctor_especialidad = view.findViewById<TextView>(R.id.txtDoctor_especialidad)
    val txtMotivo_paciente = view.findViewById<TextView>(R.id.txtMotivo_paciente)
    val imgOpciones = view.findViewById<ImageView>(R.id.imgOpciones)
}