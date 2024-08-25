package delta.medic.mobile

import Modelo.ClaseConexion
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
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.SQLException

class activity_cuenta_confi : AppCompatActivity() {

    private lateinit var binding: ActivityCuentaConfiBinding

    lateinit var txtNom: TextView
    lateinit var txtApe: TextView
    lateinit var txtCorr: TextView
    lateinit var txtDire: TextView
    lateinit var txtTel: TextView
    lateinit var txtSex: TextView
    lateinit var txtFech: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuenta_confi)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        txtNom = findViewById(R.id.txtNom)
        txtApe = findViewById(R.id.txtApe)
        txtCorr = findViewById(R.id.txtCorr)
        txtDire = findViewById(R.id.txtDire)
        txtTel = findViewById(R.id.txtTel)
        txtSex = findViewById(R.id.txtSex)
        txtFech = findViewById(R.id.txtFech)

        val emailUsuario = activity_login.userEmail

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        data class Usuario(
            val nombre: String,
            val apellido: String,
            val email: String,
            val direccion: String,
            val telefono: String,
            val sexo: String,
            val fechaNacimiento: String
        )

        //Cargar los datos del usuario
        suspend fun obtenerDatosUsuario(emailUsuario: String): Usuario? {
            var usuario: Usuario? = null
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val query = "SELECT nombre, apellido, email, direccion, telefono, sexo, fechaNacimiento FROM tbUsuarios WHERE emailUsuario = ?"
                    val statement = objConexion.prepareStatement(query)
                    statement.setString(1, emailUsuario)

                    val resultSet = statement.executeQuery()
                    if (resultSet.next()) {
                        usuario = Usuario(
                            nombre = resultSet.getString("nombre"),
                            apellido = resultSet.getString("apellido"),
                            email = resultSet.getString("email"),
                            direccion = resultSet.getString("direccion"),
                            telefono = resultSet.getString("telefono"),
                            sexo = resultSet.getString("sexo"),
                            fechaNacimiento = resultSet.getString("fechaNacimiento")
                        )
                    }

                    resultSet.close()
                    statement.close()
                    objConexion.close()
                }
            } catch (e: SQLException) {
                println("Error en la consulta SQL: ${e.message}")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
            return usuario
        }

        suspend fun obtenerDatosUsuarioCorutina(emailUsuario: String): Usuario? {
            return withContext(Dispatchers.IO) {
                obtenerDatosUsuario(emailUsuario)
            }
        }

        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val btnCambiarContra = findViewById<Button>(R.id.btnCambiarContra1)
        val txtCuenta = findViewById<TextView>(R.id.txtCuentaConfi)
        val btnEliminarUsuario = findViewById<Button>(R.id.btnEliminarUsuario)

        //Modo claro y oscuro
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                txtCuenta.setTextColor(ContextCompat.getColor(this, R.color.black))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
                txtNom.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtCorr.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtDire.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTel.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtFech.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtSex.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtApe.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                txtCuenta.setTextColor(ContextCompat.getColor(this, R.color.white))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                txtNom.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtCorr.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtDire.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTel.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtFech.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtSex.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtApe.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }

        btnRegresar.setOnClickListener {
            finish()
        }

        // Función para mostrar los datos del usuario
        fun mostrarDatosUsuario(usuario: Usuario) {
            txtNom.text = usuario.nombre
            txtApe.text = usuario.apellido
            txtCorr.text = usuario.email
            txtDire.text = usuario.direccion
            txtTel.text = usuario.telefono
            txtSex.text = usuario.sexo
            txtFech.text = usuario.fechaNacimiento
        }

        CoroutineScope(Dispatchers.Main).launch {
            val usuario = obtenerDatosUsuarioCorutina(emailUsuario)
            if (usuario != null) {
                mostrarDatosUsuario(usuario)
            } else {
                Toast.makeText(this@activity_cuenta_confi, "No se encontraron datos para el usuario.", Toast.LENGTH_SHORT).show()
            }
        }

        //Cambiar la contraseña con un botón
        btnCambiarContra.setOnClickListener {
            val intent = Intent(this, activity_cambiarcontra::class.java)
            startActivity(intent)
        }

        //Eliminar el usuario con un botón
        btnEliminarUsuario.setOnClickListener {
            val emailToDelete = activity_login.userEmail
            CoroutineScope(Dispatchers.Main).launch {
                val isDeleted = deleteUser(emailToDelete)
                if (isDeleted) {
                    Toast.makeText(this@activity_cuenta_confi, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@activity_cuenta_confi, activity_login::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@activity_cuenta_confi, "No se encontró el usuario con email $emailToDelete", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //Eliminar el usuario
    private suspend fun deleteUser(emailUsuario: String): Boolean {
        return withContext(Dispatchers.IO) {
            var isDeleted = false
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement("DELETE FROM tbUsuarios WHERE emailUsuario = ?")
                    statement.setString(1, emailUsuario)

                    val affectedRows = statement.executeUpdate()
                    if (affectedRows > 0) {
                        isDeleted = true
                        println("Usuario con email $emailUsuario eliminado correctamente.")
                    } else {
                        println("No se encontraron usuarios con el email $emailUsuario para eliminar.")
                    }

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
            isDeleted
        }
    }
}
