package delta.medic.mobile.ui.home

import Modelo.ClaseConexion
import Modelo.dataClassUsuario
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import delta.medic.mobile.R
import delta.medic.mobile.activity_login.UserData.userEmail
import delta.medic.mobile.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.SQLException

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val txtBienvenido = root.findViewById<TextView>(R.id.txtBienvenido)
        loadData(txtBienvenido)
        return root
    }

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
                        val sexo = resultSet.getCharacterStream("sexo").toString()
                        val fechaNacimiento = resultSet.getDate("fechaNacimiento")
                        var imgUsuario = ""
                        if(resultSet.getBlob("imgUsuario") != null){
                            imgUsuario = resultSet.getBlob("imgUsuario").toString()}
                        else{
                            imgUsuario = ""
                        }
                        val idTipoUsuario = resultSet.getInt("ID_TipoUsuario")

                        val userWithFullData = dataClassUsuario(
                            idUsuario, nombreUsuario, apellidoUsuario, emailUsuario, contrasena,
                            direccion, teléfono, sexo, fechaNacimiento, imgUsuario, idTipoUsuario
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

    fun loadData(lbNombre: TextView) {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                val user = GetUserParameters(userEmail)
                //Estos campos al estar con map pondrá  "[]" al inicio y al final
                val nombreUsuario = user.map { it.nombreUsuario }

                withContext(Dispatchers.Main) {
                    //Para solucionarlo se coloca replace
                    lbNombre.setText(
                        " Bienvenido ${
                            nombreUsuario.toString().replace("[", "").replace("]", "")
                        }"
                    )
                }
            }
        }catch (e: Exception) {
            println("Error: $e")
        }
    }
}