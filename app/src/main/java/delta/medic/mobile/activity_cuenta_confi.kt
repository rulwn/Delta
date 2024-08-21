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

    private lateinit var txtNom: TextView
    private lateinit var txtCorr: TextView
    private lateinit var txtDire: TextView
    private lateinit var txtTel: TextView
    private lateinit var txtFech: TextView
    private lateinit var txtSex: TextView
    private lateinit var txtApe: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuenta_confi)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val btnCambiarContra = findViewById<Button>(R.id.btnCambiarContra1)
        val txtCuenta = findViewById<TextView>(R.id.txtCuentaConfi)
        val txtNombre = findViewById<TextView>(R.id.txtNombre)
        val txtCorreo = findViewById<TextView>(R.id.txtCorreo)
        val txtDireccion = findViewById<TextView>(R.id.txtDireccionSucur)
        val txtTelefono = findViewById<TextView>(R.id.txtTelefono)
        val txtFechaNacimiento = findViewById<TextView>(R.id.txtFecha)
        val txtSexo = findViewById<TextView>(R.id.txtSexo)
        val txtApellido = findViewById<TextView>(R.id.txtApellido)
        val btnEliminarUsuario = findViewById<Button>(R.id.btnEliminarUsuario)


        //Modo claro y oscuro
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                txtCuenta.setTextColor(ContextCompat.getColor(this, R.color.black))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
                txtNombre.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtCorreo.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtDireccion.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTelefono.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtFechaNacimiento.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtSexo.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtApellido.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                txtCuenta.setTextColor(ContextCompat.getColor(this, R.color.white))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                txtNombre.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtCorreo.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtDireccion.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTelefono.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtFechaNacimiento.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtSexo.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtApellido.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }
        btnRegresar.setOnClickListener {
            finish()
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

    //Para que se carguen los datos del register
    private fun CargarDatos(emailUsuario: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val userData = withContext(Dispatchers.IO) {
                try {
                    val objConexion = ClaseConexion().cadenaConexion()
                    val statement = objConexion?.prepareStatement("SELECT nombre, apellido, emailUsuario, direccion, telefono, fechaNacimiento, sexo FROM tbUsuarios WHERE emailUsuario = ?")
                    statement?.setString(1, emailUsuario)
                    val resultSet = statement?.executeQuery()
                    if (resultSet?.next() == true) {
                        val nombre = resultSet.getString("nombre")
                        val apellido = resultSet.getString("apellido")
                        val email = resultSet.getString("emailUsuario")
                        val direccion = resultSet.getString("direccion")
                        val telefono = resultSet.getString("telefono")
                        val fechaNacimiento = resultSet.getString("fechaNacimiento")
                        val sexo = resultSet.getString("sexo")
                        resultSet.close()
                        statement.close()
                        objConexion?.close()
                        mapOf(
                            "nombre" to nombre,
                            "apellido" to apellido,
                            "email" to email,
                            "direccion" to direccion,
                            "telefono" to telefono,
                            "fechaNacimiento" to fechaNacimiento,
                            "sexo" to sexo
                        )
                    } else {
                        null
                    }
                } catch (e: SQLException) {
                    e.printStackTrace()
                    null
                }
            }
            userData?.let {
                txtNom.text = it["nombre"]
                txtApe.text = it["apellido"]
                txtCorr.text = it["email"]
                txtDire.text = it["direccion"]
                txtTel.text = it["telefono"]
                txtFech.text = it["fechaNacimiento"]
                txtSex.text = it["sexo"]
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