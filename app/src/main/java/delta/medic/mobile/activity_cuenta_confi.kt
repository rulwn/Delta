package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassUsuario
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import delta.medic.mobile.databinding.ActivityCuentaConfiBinding
import android.content.res.Configuration
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.SQLException

class activity_cuenta_confi : AppCompatActivity() {

    private lateinit var binding: ActivityCuentaConfiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuenta_confi)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val btnCambiarContra = findViewById<Button>(R.id.btnCambiarContra1)
        val txtCuenta = findViewById<TextView>(R.id.txtCuentaConfi)
        val btnEliminarUsuario = findViewById<Button>(R.id.btnEliminarUsuario)


        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                txtCuenta.setTextColor(ContextCompat.getColor(this, R.color.black))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
            } // Night mode is not active, we're using the light theme.
            Configuration.UI_MODE_NIGHT_YES -> {
                txtCuenta.setTextColor(ContextCompat.getColor(this, R.color.white))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
            } // Night mode is active, we're using dark theme.
        }
        btnRegresar.setOnClickListener {
            finish()
        }
        btnCambiarContra.setOnClickListener {
            val intent = Intent(this, activity_cambiarcontra::class.java)
            startActivity(intent)
        }

        btnEliminarUsuario.setOnClickListener {

        }

        suspend fun deleteUser(email: String): List<dataClassUsuario> {
            return withContext(Dispatchers.IO) {
                val listaUsuarios = mutableListOf<dataClassUsuario>()
                try {
                    val objConexion = ClaseConexion().cadenaConexion()
                    if (objConexion != null) {
                        val statement = objConexion.prepareStatement("DELETE FROM tbUsuarios WHERE emailUsuario = ?")!!
                        statement.setString(1, email)
                        val resultSet = statement.executeQuery()
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

    }
}