package RecycleViewHelper

import Modelo.dataClassPacientes
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R
import delta.medic.mobile.activity_login
import delta.medic.mobile.activity_login.UserData.userEmail
import delta.medic.mobile.activity_vistadoctores

class AdaptadorPacientes(private var Datos: List<dataClassPacientes>): RecyclerView.Adapter<ViewHolderPacientes>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPacientes {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_pacientes, parent, false)
        return ViewHolderPacientes(vista)
    }

    override fun getItemCount() = Datos.size

    //Aquí van los listeners
    override fun onBindViewHolder(holder: ViewHolderPacientes, position: Int) {
        val paciente = Datos[position]

        holder.txtNombreConcatenado.text = paciente.nombrePaciente + " " + paciente.apellidoPaciente
        holder.txtParentesco.text = paciente.parentesco
        holder.imgFotoPaciente.setImageResource(R.drawable.circulo_foto_bienvenida)
        holder.txtAbrirPdf.setOnClickListener {}

        /*
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val pantallaDetalle = Intent(context, activity_vistadoctores::class.java)
            pantallaDetalle.putExtra("ID_Doctor", doctorFavorito.ID_Doctor)
            pantallaDetalle.putExtra("Fav", true)
            context.startActivity(pantallaDetalle)
        }*/



    }
}