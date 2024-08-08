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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.Dispatchers
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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var dataUser: dataClassUsuario


    suspend fun GetUserParameters(email: String): List<dataClassUsuario> {
        return withContext(Dispatchers.IO) {
            val listaUsuarios = mutableListOf<dataClassUsuario>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {

                    val statement = objConexion.prepareStatement("SELECT * FROM tbUsuarios WHERE emailUsuario = ?")!!
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
                        var imgUsuario = ""
                        if(resultSet.getString("imgUsuario") != null){
                            imgUsuario = resultSet.getString("imgUsuario").toString()}
                        else{
                            imgUsuario = ""
                        }
                        val idTipoUsuario = resultSet.getInt("ID_TipoUsuario")

                        val userWithFullData = dataClassUsuario(
                            idUsuario, nombreUsuario, apellidoUsuario, emailUsuario, contrasena,
                            direccion, teléfono, sexo, fechaNacimiento, imgUsuario, idTipoUsuario
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
            val user = GetUserParameters(sentEmail)
            //Estos campos al estar con map pondrá  "[]" al inicio y al final
            val nombreCompleto = user.map { it.nombreUsuario}
            val apellidoCompleto = user.map {it.apellidoUsuario}
            val emailUsuario = user.map { it.emailUsuario}
            val fotoUsuario = user.map { it.imgUsuario}

            withContext(Dispatchers.Main) {
                /*Como se quiere obtener solo el primer nombre sin importar que el usuario haya metido 827 nombres
                * Lo primero que se hace es que nombreCompleto era un map, asi que tenía [], por lo que para quitarselos ponemos replace "["
                * y replace "]" y de esta manera que los quite de la cadena, y luego, como no nos interesan el resto de nombres,
                * los dividimos con un .split(" ") entre las comillas pusimos un espacio, pq cada nombre va separado con un espacio en blanco
                * y luego .firstOrNull() ?:"" para obtener el primer elemento, y en caso de que sea nulo que no se explote el celular y se
                * petatee la app, y asi ya nos furrula.*/
                val primerNombre = (nombreCompleto.toString().replace("[", "").replace("]","")).split(" ").firstOrNull() ?:""
                //Hacemos lo mismo de arriba para el apellido
                val primerApellido = (apellidoCompleto.toString().replace("[", "").replace("]","")).split(" ").firstOrNull() ?:""


                //Ahora solo ponemos que el lbNombre sea el nombre y apellido.
                lbNombre.setText("$primerNombre $primerApellido")
                lbCorreo.setText(emailUsuario.toString().replace("[", "").replace("]",""))

                /*
                if (fotoUsuario.isNotEmpty()) {
                    val bitmap = BitmapFactory.decodeByteArray(fotoUsuario.toString().toByteArray(), 0, fotoUsuario.size)
                    imgvFoto.setImageBitmap(bitmap)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Hubo un error al intentar cargar la foto de perfil",
                        Toast.LENGTH_SHORT
                    ).show()
                }*/
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
            var imgUsuario = ""
            if(dataUser.imgUsuario != null){
                imgUsuario = dataUser.imgUsuario}
            else{
                imgUsuario = ""
            }
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
        val imgvSettings = root.findViewById<ImageView>(R.id.imgVSettingsPerfil)
        val imgvFoto = root.findViewById<ImageView>(R.id.imgvPriv)
        val imgvPersonalizar = root.findViewById<ImageView>(R.id.imgvPerfil)
        val imgvSeguro = root.findViewById<ImageView>(R.id.imgvSeguroPerfil)
        val imgvDoctoresFavoritos = root.findViewById<ImageView>(R.id.imgvDocFav)
        val imgvRecetas = root.findViewById<ImageView>(R.id.imgvRecetas)
        val imgvHistorialCitas = root.findViewById<ImageView>(R.id.imgvHistCitas)
        val imgvMisReseñas = root.findViewById<ImageView>(R.id.imgvMisReseñas)
        //Labels
        val lbNombre = root.findViewById<TextView>(R.id.txtPrivacidadySeguridad)
        val lbCorreo = root.findViewById<TextView>(R.id.txtNotiiii)
        val lbPersonalizar = root.findViewById<TextView>(R.id.lbPersonalizarPerfil)
        val lbSeguro = root.findViewById<TextView>(R.id.lbSeguroPerfil)
        val lbPerfil = root.findViewById<TextView>(R.id.lbPerfil)

        lbPerfil.setText(Html.fromHtml(getResources().getString(R.string.lbPerfilSub)))



        loadData(lbNombre,lbCorreo,imgvFoto)
        /******************************************************************************************
        * On Clicks                                                                              *
        ******************************************************************************************/
        imgvSettings.setOnClickListener{
            val activitySettings = Intent(requireContext(), activity_configuracion::class.java)
            startActivity(activitySettings)
        }
        imgvPersonalizar.setOnClickListener{
            val activityEditarPerfil = Intent(requireContext(), activity_editarperfil::class.java)

            startActivity(ActivitySettings(activityEditarPerfil))
        }


        lbPersonalizar.setOnClickListener{
            val activityEditarPerfil = Intent(requireContext(), activity_editarperfil::class.java)


            startActivity(ActivitySettings(activityEditarPerfil))
        }
        imgvSeguro.setOnClickListener {
            //No sé hacia donde lleva
        }
        lbSeguro.setOnClickListener {
            //No sé hacia donde lleva
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
            //No estan las reseñas
        }

        imgvSeguro.setOnClickListener{
            //No sé hacia donde lleva

        }

        return root
    }

}