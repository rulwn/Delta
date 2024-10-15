package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassUsuario
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.SQLException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_usuario.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("DEPRECATION")
class fragment_usuario : Fragment() {

    lateinit var dataUser: dataClassUsuario

    suspend fun GetUserParameters(email: String): List<dataClassUsuario> {
        return withContext(Dispatchers.IO) {
            val listaUsuarios = mutableListOf<dataClassUsuario>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {

                    val statement =
                        objConexion.prepareStatement("SELECT * FROM tbUsuarios WHERE emailUsuario = ?")!!
                    statement.setString(1, userEmail)
                    val resultSet = statement.executeQuery()

                    if (resultSet.next()) {
                        val idUsuario = resultSet.getInt("ID_Usuario")
                        val nombreUsuario = resultSet.getString("nombreUsuario")
                        val apellidoUsuario = resultSet.getString("apellidoUsuario")
                        val emailUsuario = resultSet.getString("emailUsuario")
                        val contrasena = resultSet.getString("contrasena")
                        val direccion = resultSet.getString("direccion")
                        val teléfono = resultSet.getString("telefonoUsuario")
                        val sexo = resultSet.getString("sexo").toString()
                        val fechaNacimiento = resultSet.getString("fechaNacimiento")
                        val imgUsuario = resultSet.getString("imgUsuario")
                        val idTipoUsuario = resultSet.getInt("ID_TipoUsuario")

                        val userWithFullData = dataClassUsuario(
                            idUsuario,
                            nombreUsuario,
                            apellidoUsuario,
                            emailUsuario,
                            contrasena,
                            direccion,
                            teléfono,
                            sexo,
                            fechaNacimiento,
                            imgUsuario,
                            idTipoUsuario
                        )
                        dataUser = userWithFullData
                        listaUsuarios.add(userWithFullData)
                    }
                    resultSet.close()
                    statement.close()
                    objConexion.close()
                } else {
                    println("No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: SQLException) {
                println("Error en la consulta SQL: ${e.message}")
            } catch (e: Exception) {
                println("Este es el error: ${e.message}")
            }
            listaUsuarios
        }
    }

    fun loadData(lbNombre: TextView, lbCorreo: TextView, imgvFoto: ImageView) {
        viewLifecycleOwner.lifecycleScope.launch {
            GetUserParameters(userEmail)

            val nombreCompleto = dataUser.nombreUsuario
            val apellidoCompleto = dataUser.apellidoUsuario
            val emailUsuario = userEmail
            val fotoUsuario = dataUser.imgUsuario

            withContext(Dispatchers.Main) {
                val primerNombre = (nombreCompleto).split(" ").firstOrNull() ?: ""
                val primerApellido = (apellidoCompleto).split(" ").firstOrNull() ?: ""

                lbNombre.setText("$primerNombre $primerApellido")
                lbCorreo.setText(emailUsuario)

                if (fotoUsuario.isNotEmpty()) {
                    Glide.with(imgvFoto)
                        .load(fotoUsuario)
                        .circleCrop()
                        .into(imgvFoto)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Hubo un error al intentar cargar la foto de perfil",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_usuario, container, false)

        /******************************************************************************************
         * Valores de los componentes
         ******************************************************************************************/
        val imgvFoto = root.findViewById<ImageView>(R.id.imgvPriv)
        val imgvPersonalizar = root.findViewById<ImageView>(R.id.imgvPerfil)
        val imgvDoctoresFavoritos = root.findViewById<ImageView>(R.id.imgvDocFav)
        val imgvRecetas = root.findViewById<ImageView>(R.id.imgvRecetas)
        val imgvHistorialCitas = root.findViewById<ImageView>(R.id.imgvHistCitas)
        val imgvMisReseñas = root.findViewById<ImageView>(R.id.imgvMisReseñas)

        val lbNombre = root.findViewById<TextView>(R.id.txtPrivacidadySeguridad)
        val lbCorreo = root.findViewById<TextView>(R.id.txtNotiiii)
        val lbPersonalizar = root.findViewById<TextView>(R.id.lbPersonalizarPerfil)
        val lbPerfil = root.findViewById<TextView>(R.id.lbPerfil)
        lbPerfil.setText(Html.fromHtml(getResources().getString(R.string.lbPerfilSub)))

        loadData(lbNombre, lbCorreo, imgvFoto)

        /******************************************************************************************
         * Cambiar tema (modo claro/oscuro)
         ******************************************************************************************/
        applyThemeColors(imgvDoctoresFavoritos, imgvRecetas, imgvHistorialCitas, imgvMisReseñas, lbPersonalizar)

        /******************************************************************************************
         * OnClick events
         ******************************************************************************************/
        imgvPersonalizar.setOnClickListener {
            val activityEditarPerfil = Intent(requireContext(), activity_editarperfil::class.java)
            activityEditarPerfil.putExtra("idUsuario", dataUser.idUsuario)
            activityEditarPerfil.putExtra("nombreUsuario", dataUser.nombreUsuario)
            activityEditarPerfil.putExtra("apellidoUsuario", dataUser.apellidoUsuario)
            activityEditarPerfil.putExtra("emailUsuario", dataUser.emailUsuario)
            activityEditarPerfil.putExtra("dirección", dataUser.dirección)
            activityEditarPerfil.putExtra("teléfono", dataUser.teléfonoUsuario)
            activityEditarPerfil.putExtra("imgUsuario1", dataUser.imgUsuario.toString())
            startActivity(activityEditarPerfil)
        }

        lbPersonalizar.setOnClickListener {
            val activityEditarPerfil = Intent(requireContext(), activity_editarperfil::class.java)
            activityEditarPerfil.putExtra("idUsuario", dataUser.idUsuario)
            activityEditarPerfil.putExtra("nombreUsuario", dataUser.nombreUsuario)
            activityEditarPerfil.putExtra("apellidoUsuario", dataUser.apellidoUsuario)
            activityEditarPerfil.putExtra("emailUsuario", dataUser.emailUsuario)
            activityEditarPerfil.putExtra("dirección", dataUser.dirección)
            activityEditarPerfil.putExtra("teléfono", dataUser.teléfonoUsuario)
            activityEditarPerfil.putExtra("imgUsuario", dataUser.imgUsuario)
            startActivity(activityEditarPerfil)
        }

        imgvDoctoresFavoritos.setOnClickListener {
            val activityDoctoresFavoritos = Intent(requireContext(), activity_doctoresfavoritos::class.java)
            startActivity(activityDoctoresFavoritos)
        }

        imgvRecetas.setOnClickListener {
            val activityRecetas = Intent(requireContext(), activity_misrecetas::class.java)
            startActivity(activityRecetas)
        }

        imgvHistorialCitas.setOnClickListener {
            val activityHistorialCitas = Intent(requireContext(), activity_historialdecitas::class.java)
            startActivity(activityHistorialCitas)
        }

        imgvMisReseñas.setOnClickListener {
            val activityMisReseñas = Intent(requireContext(), activity_misresenas::class.java)
            startActivity(activityMisReseñas)
        }

        return root
    }

    private fun applyThemeColors(
        imgvDocFav: ImageView,
        imgvRecetas: ImageView,
        imgvHistCitas: ImageView,
        imgvMisReseñas: ImageView,
        lbPersonalizarPerfil: TextView
    ) {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                imgvDocFav.setImageResource(R.drawable.ic_mdf_perfil)
                imgvRecetas.setImageResource(R.drawable.ic_mr_perfil)
                imgvHistCitas.setImageResource(R.drawable.ic_mhdc_perfil)
                imgvMisReseñas.setImageResource(R.drawable.ic_mir_perfil)
                lbPersonalizarPerfil.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                imgvDocFav.setImageResource(R.drawable.ic_mdf_perfil_darksito)
                imgvRecetas.setImageResource(R.drawable.ic_mr_perfil_dark)
                imgvHistCitas.setImageResource(R.drawable.ic_mhdc_perfil_dark)
                imgvMisReseñas.setImageResource(R.drawable.ic_mir_perfil_dark)
                lbPersonalizarPerfil.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }
}