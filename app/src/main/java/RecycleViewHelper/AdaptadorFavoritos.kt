package RecycleViewHelper

import Modelo.ClaseConexion
import Modelo.dataClassFavoritos
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import delta.medic.mobile.R

import delta.medic.mobile.activity_login.UserData.userEmail
import delta.medic.mobile.activity_vistadoctores

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.SQLException

class AdaptadorFavoritos(private var Datos: List<dataClassFavoritos>):RecyclerView.Adapter<ViewHolderCentroMini>() {

    var emailUsuario = userEmail
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCentroMini {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_centro_medico_pequeno_normal, parent, false)
        return ViewHolderCentroMini(vista)
    }

    override fun getItemCount() = Datos.size

    fun eliminarFavorito(EmailUsuario: String, ID_Sucursal: Int, ID_Doctor: Int, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val conexion = ClaseConexion().cadenaConexion()

            // Verifica que la conexión no sea nula antes de continuar
            if (conexion != null) {
                try {
                    conexion.prepareCall("{CALL PROC_DELT_FAVORITOS(?,?,?)}").use { eliminarFavorito ->
                        eliminarFavorito.setString(1, EmailUsuario)
                        eliminarFavorito.setInt(2, ID_Doctor)
                        eliminarFavorito.setInt(3, ID_Sucursal)
                        eliminarFavorito.executeUpdate()
                    }

                    withContext(Dispatchers.Main) {
                        // Actualiza la lista y notifica al adaptador
                        val listaDatos = Datos.toMutableList()
                        listaDatos.removeAt(position)
                        Datos = listaDatos.toList()
                        notifyItemRemoved(position)
                    }

                } catch (e: SQLException) {
                    // Manejo de excepciones SQL
                    e.printStackTrace()
                } catch (e: Exception) {
                    // Manejo de cualquier otra excepción
                    e.printStackTrace()
                } finally {
                    // Cierre de la conexión, si es necesario
                    try {
                        conexion.close()
                    } catch (e: SQLException) {
                        e.printStackTrace()
                    }
                }
            } else {
                // Manejo del caso en que la conexión es nula
                println("No se pudo establecer conexión con la base de datos")
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolderCentroMini, position: Int) {
        val favorito = Datos[position]
        holder.imgClinicaFondo.setImageResource(R.drawable.fotoclinica1)
        holder.imgFotoDoctor.setImageResource(R.drawable.circulo_foto_bienvenida)
        holder.imgFondoTexto.setImageResource(R.drawable.fondoitemcentroxml)
        holder.render(favorito)
        Glide.with(holder.itemView)
            .load(favorito.imgSucusal)
            .centerCrop()
            .into(holder.imgClinicaFondo)

        holder.render(favorito)
        Glide.with(holder.itemView)
            .load(favorito.imgUsuario)
            .circleCrop()
            .into(holder.imgFotoDoctor)

        holder.txtNombreDoctor.text = favorito.nombreUsuario
        holder.txtEspecialidad.text = favorito.nombreTipoSucursal
        holder.imgFavoritos.setImageResource(R.drawable.corazon_favoritos)

        holder.imgFavoritos.visibility = View.VISIBLE

        holder.imgFavoritos.setOnClickListener {
            println(emailUsuario)
            val email = emailUsuario
            eliminarFavorito(email, favorito.ID_Sucursal, favorito.ID_Doctor, position)

        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val doctorFavorito = Datos[position]
            val pantallaDetalle = Intent(context, activity_vistadoctores::class.java)
            pantallaDetalle.putExtra("ID_Doctor", doctorFavorito.ID_Doctor)
            pantallaDetalle.putExtra("doctorEmail", doctorFavorito.emailUsuario)
            pantallaDetalle.putExtra("Fav", true)
            context.startActivity(pantallaDetalle)
        }



    }
}