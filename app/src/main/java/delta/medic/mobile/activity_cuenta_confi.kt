package delta.medic.mobile

import Modelo.ClaseConexion
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import delta.medic.mobile.databinding.ActivityCuentaConfiBinding
import android.content.res.Configuration
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.SQLException
import delta.medic.mobile.activity_login.UserData.userEmail

class activity_cuenta_confi : AppCompatActivity() {

    private lateinit var binding: ActivityCuentaConfiBinding

    private lateinit var txtNom: TextView
    private lateinit var txtApe: TextView
    private lateinit var txtCorr: TextView
    private lateinit var txtDire: TextView
    private lateinit var txtTel: TextView
    private lateinit var txtSex: TextView
    private lateinit var txtFech: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuenta_confi)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Inicializar los textViews
        txtNom = findViewById(R.id.txtNom)
        txtApe = findViewById(R.id.txtApe)
        txtCorr = findViewById(R.id.txtCorr)
        txtDire = findViewById(R.id.txtDire)
        txtTel = findViewById(R.id.txtTel)
        txtSex = findViewById(R.id.txtSex)
        txtFech = findViewById(R.id.txtFech)

        //Llamar la función para cargar los datos del usuario
        CoroutineScope(Dispatchers.Main).launch {
            val fragmentUsuario = fragment_usuario()
            val user = fragmentUsuario.GetUserParameters(userEmail)
            val nombreUsuario = user.map { it.nombreUsuario }
            val apellidoUsuario = user.map { it.apellidoUsuario }
            val emailUsuario = user.map { it.emailUsuario }
            val direccion = user.map { it.dirección }
            val telefonoUsuario = user.map { it.teléfonoUsuario }
            val sexo = user.map { it.sexo }
            val fechaNacimiento = user.map { it.fechaNacimiento }

            withContext(Dispatchers.Main) {
                //Para solucionarlo se coloca replace
                txtNom.setText(nombreUsuario.toString().replace("[", "").replace("]", ""))
                txtApe.setText(apellidoUsuario.toString().replace("[", "").replace("]", ""))
                txtCorr.setText(emailUsuario.toString().replace("[", "").replace("]", ""))
                txtDire.setText(direccion.toString().replace("[", "").replace("]", ""))
                txtTel.setText(telefonoUsuario.toString().replace("[", "").replace("]", ""))
                txtSex.setText(sexo.toString().replace("[", "").replace("]", ""))
                txtFech.setText(fechaNacimiento.toString().replace("[", "").replace("]", ""))
            }
        }

        //Modo claro y oscuro
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val btnCambiarContrasena = findViewById<Button>(R.id.btnCambiarContrasena)
        val txtCuenta = findViewById<TextView>(R.id.txtCuentaConfi)
        val btnEliminarUsuario = findViewById<TextView>(R.id.btnEliminarUsuario)

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

        btnCambiarContrasena.setOnClickListener {
            val intent = Intent(this, activity_cambiarcontra::class.java)
            startActivity(intent)
        }

        btnEliminarUsuario.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val isDeleted = deleteUser(userEmail)
                if (isDeleted) {
                    Toast.makeText(this@activity_cuenta_confi, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@activity_cuenta_confi, activity_login::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@activity_cuenta_confi, "No se encontró el usuario con email $userEmail", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Función para cargar datos del usuario desde la base de datos
    /*private suspend fun CargarDatosUsuario(userEmail: String): dataClassUsuario? {
        return withContext(Dispatchers.IO) {
            var usuario: dataClassUsuario? = null
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement(
                        "SELECT nombreUsuario, apellidoUsuario, emailUsuario, direccion, telefono, sexo, fechanacimiento " +
                                "FROM tbUsuarios WHERE emailUsuario = ?"
                    )
                    statement.setString(1, userEmail)

                    val resultSet = statement.executeQuery()
                    if (resultSet.next()) {
                        usuario = dataClassUsuario(
                            nombreUsuario = resultSet.getString("nombreUsuario"),
                            apellidoUsuario = resultSet.getString("apellidoUsuario"),
                            emailUsuario = resultSet.getString("emailUsuario"),
                            dirección = resultSet.getString("direccion"),
                            teléfonoUsuario = resultSet.getString("telefono"),
                            sexo = resultSet.getString("sexo"),
                            fechaNacimiento = resultSet.getString("fechanacimiento")
                        )
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
            usuario
        }
    }
        */
    // Función para eliminar usuario
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