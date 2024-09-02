package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassUsuario
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.SQLException
import delta.medic.mobile.activity_login.UserData.userEmail as sentEmail

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
                    // Verifica si hay resultados
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

                    } else {
                        println("No se encontraron usuarios con el email ${email}.")
                    }

                    // Cerrar recursos
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
                val primerNombre =
                    (nombreCompleto).split(" ").firstOrNull() ?: ""
                //Hacemos lo mismo de arriba para el apellido
                val primerApellido =
                    (apellidoCompleto).split(" ").firstOrNull() ?: ""

                //Ahora solo ponemos que el lbNombre sea el nombre y apellido.
                lbNombre.setText("$primerNombre $primerApellido")
                lbCorreo.setText(emailUsuario)


                if (fotoUsuario.isNotEmpty()) {
                    Glide.with(imgvFoto)
                        .load(fotoUsuario)
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

        fun ActivitySettings(activitySettings: Intent): Intent {
            val idUsuario = dataUser.idUsuario
            val nombreUsuario = dataUser.nombreUsuario
            val apellidoUsuario = dataUser.apellidoUsuario
            val emailUsuario = dataUser.emailUsuario
            val contraseña = dataUser.contraseña
            val dirección = dataUser.dirección
            val teléfono = dataUser.teléfonoUsuario
            val sexo = dataUser.sexo
            val fechaNacimiento = dataUser.fechaNacimiento
            val imgUsuario = dataUser.idUsuario
            val idTipoUsuario = dataUser.idTipoUsuario


            activitySettings.putExtra("idUsuario", idUsuario)
            activitySettings.putExtra("nombreUsuario", nombreUsuario)
            activitySettings.putExtra("apellidoUsuario", apellidoUsuario)
            activitySettings.putExtra("emailUsuario", emailUsuario)
            activitySettings.putExtra("contraseña", contraseña)
            activitySettings.putExtra("dirección", dirección)
            activitySettings.putExtra("teléfono", teléfono)
            activitySettings.putExtra("sexo", sexo)
            activitySettings.putExtra("fechaNacimiento", fechaNacimiento)
            activitySettings.putExtra("imgUsuario", imgUsuario)
            activitySettings.putExtra("idTipoUsuario", idTipoUsuario)

            return activitySettings
        }

        /******************************************************************************************
         * Values                                                                                  *
         ******************************************************************************************/
        //Image View
        val imgvFoto = root.findViewById<ImageView>(R.id.imgvPriv)
        val imgvPersonalizar = root.findViewById<ImageView>(R.id.imgvPerfil)
        val imgvDoctoresFavoritos = root.findViewById<ImageView>(R.id.imgvDocFav)
        val imgvRecetas = root.findViewById<ImageView>(R.id.imgvRecetas)
        val imgvHistorialCitas = root.findViewById<ImageView>(R.id.imgvHistCitas)
        val imgvMisReseñas = root.findViewById<ImageView>(R.id.imgvMisReseñas)
        val imgvPacientesPerfil = root.findViewById<ImageView>(R.id.imgvPacientesPerfil)
        //Labels
        val lbNombre = root.findViewById<TextView>(R.id.txtPrivacidadySeguridad)
        val lbCorreo = root.findViewById<TextView>(R.id.txtNotiiii)
        val lbPersonalizar = root.findViewById<TextView>(R.id.lbPersonalizarPerfil)
        val lbPerfil = root.findViewById<TextView>(R.id.lbPerfil)
        val lbPacientes = root.findViewById<TextView>(R.id.lbPacientesPerfil)

        lbPerfil.setText(Html.fromHtml(getResources().getString(R.string.lbPerfilSub)))


        loadData(lbNombre, lbCorreo, imgvFoto)

        /******************************************************************************************
         * On Clicks                                                                              *
         ******************************************************************************************/
        imgvPersonalizar.setOnClickListener {
            val activityEditarPerfil = Intent(requireContext(), activity_editarperfil::class.java)
            activityEditarPerfil.putExtra("idUsuario", dataUser.idUsuario)
            activityEditarPerfil.putExtra("nombreUsuario", dataUser.nombreUsuario)
            activityEditarPerfil.putExtra("apellidoUsuario", dataUser.apellidoUsuario)
            activityEditarPerfil.putExtra("emailUsuario", dataUser.emailUsuario)
            activityEditarPerfil.putExtra("dirección", dataUser.dirección)
            activityEditarPerfil.putExtra("teléfono", dataUser.teléfonoUsuario)
            activityEditarPerfil.putExtra("imgUsuario", dataUser.imgUsuario)
            startActivity(ActivitySettings(activityEditarPerfil))
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

            startActivity(ActivitySettings(activityEditarPerfil))
        }
        imgvPacientesPerfil.setOnClickListener {
            val activityPacientes = Intent(requireContext(), activity_pacientes::class.java)
            startActivity(activityPacientes)
        }
        lbPacientes.setOnClickListener {
            val activityPacientes = Intent(requireContext(), activity_pacientes::class.java)
            startActivity(activityPacientes)
        }
        /*imgvSeguro.setOnClickListener {
            val activitySeguroPaciente =
                Intent(requireContext(), activity_seguro_paciente::class.java)
            startActivity(activitySeguroPaciente)
        }*/
        imgvDoctoresFavoritos.setOnClickListener {
            val activityDoctoresFavoritos =
                Intent(requireContext(), activity_doctoresfavoritos::class.java)
            startActivity(activityDoctoresFavoritos)
        }
        imgvRecetas.setOnClickListener {
            val activityRecetas = Intent(requireContext(), activity_misrecetas::class.java)
            startActivity(activityRecetas)
        }
        imgvHistorialCitas.setOnClickListener {
            val activityHistorialCitas =
                Intent(requireContext(), activity_historialdecitas::class.java)
            startActivity(activityHistorialCitas)
        }
        imgvMisReseñas.setOnClickListener {
            //No estan las reseñas
        }

        return root
    }

}