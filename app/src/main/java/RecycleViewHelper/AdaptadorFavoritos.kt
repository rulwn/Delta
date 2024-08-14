package RecycleViewHelper

import Modelo.ClaseConexion
import Modelo.dataClassFavoritos
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R
import delta.medic.mobile.activity_login
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdaptadorFavoritos(private var Datos: List<dataClassFavoritos>):RecyclerView.Adapter<ViewHolderCentroMini>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCentroMini {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_centro_medico_pequeno_normal, parent, false)
        return ViewHolderCentroMini(vista)
    }

    override fun getItemCount() = Datos.size

    fun eliminarFavorito(EmailUsuario: String, ID_Sucursal: Int, ID_Doctor: Int, position: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val conexion = ClaseConexion().cadenaConexion()
            val eliminarFavorito = conexion?.prepareCall("{CALL PROC_DELT_FAVORITOS(?,?,?)}")!!
            eliminarFavorito.setString(1, EmailUsuario)
            eliminarFavorito.setInt(2, ID_Doctor)
            eliminarFavorito.setInt(3, ID_Sucursal)

            eliminarFavorito.executeUpdate()

            val listaDatos = Datos.toMutableList()
            listaDatos.removeAt(position)
            Datos = listaDatos.toList()
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }

    }

    override fun onBindViewHolder(holder: ViewHolderCentroMini, position: Int) {
        val favorito = Datos[position]
        holder.imgClinicaFondo.setImageResource(R.drawable.fotoclinica1)
        holder.imgFotoDoctor.setImageResource(R.drawable.circulo_foto_bienvenida)
        holder.imgFondoTexto.setImageResource(R.drawable.fondoitemcentroxml)

        holder.txtNombreDoctor.text = favorito.nombreUsuario
        holder.txtEspecialidad.text = favorito.nombreTipoSucursal
        holder.imgFavoritos.setImageResource(R.drawable.corazon_favoritos)

        holder.imgFavoritos.visibility = View.VISIBLE

        holder.imgFavoritos.setOnClickListener {
            val email = activity_login.UserData.userEmail
            eliminarFavorito(email, favorito.ID_Sucursal, favorito.ID_Doctor, position)

        }



    }
}