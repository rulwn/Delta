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


    suspend fun GetUserParameters(email: String): List<dataClassUsuario> {
        return withContext(Dispatchers.IO) {
            val listaUsuarios = mutableListOf<dataClassUsuario>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {

                    val statement = objConexion.prepareStatement("SELECT * FROM tbUsuarios WHERE emailUsuario = ?")!!
                    statement.setString(1, sentEmail)
                    val resultSet = statement.executeQuery()
                    resultSet.next()
                    
                    // Verifica si hay resultados
                    if (resultSet.next()) {
                        val idUsuario = resultSet.getInt("ID_Usuario")
                        val nombreUsuario = resultSet.getString("nombreUsuario")
                        val apellidoUsuario = resultSet.getString("apellidoUsuario")
                        val emailUsuario = resultSet.getString("emailUsuario")
                        val contrasena = resultSet.getString("contrasena")
                        val direccion = resultSet.getString("direccion")
                        val teléfono = resultSet.getString("telefonoUsuario")
                        val sexo = resultSet.getCharacterStream("sexo").toString()
                        val fechaNacimiento = resultSet.getDate("fechaNacimiento")
                        val imgUsuario = resultSet.getBlob("imgUsuario").toString()
                        val idTipoUsuario = resultSet.getInt("ID_TipoUsuario")
                        val idSeguro = resultSet.getInt("ID_Seguro")

                        val userWithFullData = dataClassUsuario(
                            idUsuario, nombreUsuario, apellidoUsuario, emailUsuario, contrasena,
                            direccion, teléfono, sexo, fechaNacimiento, imgUsuario, idTipoUsuario, idSeguro
                        )
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
            val nombreUsuario = user.map { it.nombreUsuario }
            val emailUsuario = user.map { it.emailUsuario }
            val fotoUsuario = user.map { it.imgUsuario}

            withContext(Dispatchers.Main) {
                lbNombre.text = nombreUsuario.toString()
                lbCorreo.text = emailUsuario.toString()
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



        /******************************************************************************************
        * Values                                                                                  *
        ******************************************************************************************/
        //Image View
        val imgvSettings = root.findViewById<ImageView>(R.id.imgVSettingsPerfil)
        val imgvFoto = root.findViewById<ImageView>(R.id.imgvFotoPerfil)
        val imgvPersonalizar = root.findViewById<ImageView>(R.id.imgvPersonalizarPerfil)
        val imgvSeguro = root.findViewById<ImageView>(R.id.imgvSeguroPerfil)
        val imgvDoctoresFavoritos = root.findViewById<ImageView>(R.id.imgvDocFav)
        val imgvRecetas = root.findViewById<ImageView>(R.id.imgvRecetas)
        val imgvHistorialCitas = root.findViewById<ImageView>(R.id.imgvHistCitas)
        val imgvMisReseñas = root.findViewById<ImageView>(R.id.imgvMisReseñas)
        //Labels
        val lbNombre = root.findViewById<TextView>(R.id.lbNombrePerfil)
        val lbCorreo = root.findViewById<TextView>(R.id.lbCorreoPerfil)
        val lbPersonalizar = root.findViewById<TextView>(R.id.lbPersonalizarPerfil)
        val lbSeguro = root.findViewById<TextView>(R.id.lbSeguroPerfil)
        val lbPerfil = root.findViewById<TextView>(R.id.lbPerfil)

        lbPerfil.setText(Html.fromHtml(getResources().getString(R.string.lbPerfilSub)))
        Toast.makeText(requireContext(), sentEmail, Toast.LENGTH_SHORT).show()


        loadData(lbNombre,lbCorreo,imgvFoto)
        /******************************************************************************************
        * On Clicks                                                                              *
        ******************************************************************************************/
        imgvSettings.setOnClickListener {
            val activitySettings = Intent(requireContext(), activity_configuracion::class.java)
            startActivity(activitySettings)
        }
        imgvPersonalizar.setOnClickListener{
            val activityEditarPerfil = Intent(requireContext(), activity_editarperfil::class.java)
            startActivity(activityEditarPerfil)
        }
        lbPersonalizar.setOnClickListener{
            val activityEditarPerfil = Intent(requireContext(), activity_editarperfil::class.java)
            startActivity(activityEditarPerfil)
        }

        lbSeguro.setOnClickListener {
            //No sé hacia donde lleva
            val activityEditarPerfl = Intent(requireContext(), activity_vistadoctores::class.java)
            startActivity(activityEditarPerfl)
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

        /******************************************************************************************
         * Funciones                                                                              *
         ******************************************************************************************/

        /*fun GetUserParameters(): List<dataClassUsuario>{
            val listaUsuarios = mutableListOf<dataClassUsuario>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                val statement = objConexion?.prepareStatement("SELECT * FROM tbUsuario Where emailUsuario = ?")!!
                statement.setString(1, email)
                val resultSet = statement.executeQuery()


                while (resultSet.next()){
                    val idUsuario = resultSet.getInt("ID_Usuario")
                    val nombreUsuario = resultSet.getString("nombreUsuario")
                    val apellidoUsuario = resultSet.getString("apellidoUsuario")
                    val emailUsuario = resultSet.getString("emailUsuario")
                    val contraseña = resultSet.getString("contrasena")
                    val dirección = resultSet.getString("direccion")
                    val sexo =  resultSet.getString("sexo").toString().toCharArray()[0]
                    val fechaNacimiento = resultSet.getDate("fechaNacimiento")
                    val imgUsuario = resultSet.getBlob("imgUsuario")
                    val idTipoUsuario = resultSet.getInt("ID_TipoUsuario")
                    val idSeguro = resultSet.getInt("ID_Seguro")

                    val userWithFullData = dataClassUsuario(idUsuario, nombreUsuario, apellidoUsuario, emailUsuario, contraseña,
                        dirección, sexo, fechaNacimiento, imgUsuario, idTipoUsuario, idSeguro)
                    listaUsuarios.add(userWithFullData)
                }
                return listaUsuarios
            }
            catch (e: Exception){
                println("Este es el error $e")

            }
            return listaUsuarios

        }


        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val user = GetUserParameters()
                val nombreUsuario = user.map { it.nombreUsuario }
                val emailUsuario = user.map { it.emailUsuario }
                val fotoUsuario = user.map { it.imgUsuario }.toString().toByteArray()

                withContext(Dispatchers.Main) {
                    lbNombre.setText(nombreUsuario.toString())
                    lbCorreo.setText((emailUsuario.toString()))
                    if (fotoUsuario != null && fotoUsuario.isNotEmpty()) {
                        val bitmap = BitmapFactory.decodeByteArray(fotoUsuario, 0, fotoUsuario.size)

                        imgvFoto.setImageBitmap(bitmap)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Hubo un error al intentar cargar la foto de perfil",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }*/


        imgvSeguro.setOnClickListener{
            //No sé hacia donde lleva

        }

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_usuario.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_usuario().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}