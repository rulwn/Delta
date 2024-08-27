package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.Encrypter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_cambiarcontra : AppCompatActivity() {

    private lateinit var txtContrasenaActual: EditText
    private lateinit var txtNuevaContrasena: EditText
    private lateinit var txtConfirmarContrasena: EditText
    private lateinit var btnCambiarContrasena: Button

    private lateinit var userEmail: String
    private var codigoRecu: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiarcontra)

        Log.d("activity_cambiarcontra", "onCreate iniciado")

        // Inicialización de las vistas
        try {
            txtContrasenaActual = findViewById(R.id.txtContrasenaActual)
            txtNuevaContrasena = findViewById(R.id.txtNuevaContrasena)
            txtConfirmarContrasena = findViewById(R.id.txtConfirmarContrasena)
            btnCambiarContrasena = findViewById(R.id.btnCambiarContrasena2)
            Log.d("activity_cambiarcontra", "Vistas inicializadas correctamente")
        } catch (e: Exception) {
            Log.e("activity_cambiarcontra", "Error al inicializar vistas: ${e.message}")
        }

        // Listener del botón
        btnCambiarContrasena.setOnClickListener {
            Log.d("activity_cambiarcontra", "Botón cambiar contraseña presionado")

            val actualContrasena = txtContrasenaActual.text.toString()
            val nuevaContrasena = txtNuevaContrasena.text.toString()
            val confirmarContrasena = txtConfirmarContrasena.text.toString()

            if (actualContrasena.isNotEmpty() && nuevaContrasena == confirmarContrasena) {
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("activity_cambiarcontra", "Verificando contraseña actual")

                    val contrasenaActualValida = withContext(Dispatchers.IO) {
                        verificarContrasenaActual(userEmail, actualContrasena)
                    }

                    if (contrasenaActualValida) {
                        Log.d("activity_cambiarcontra", "Contraseña actual válida, cambiando contraseña")
                        cambiarContrasena(userEmail, nuevaContrasena)
                    } else {
                        Log.e("activity_cambiarcontra", "Contraseña actual incorrecta")
                        Toast.makeText(this@activity_cambiarcontra, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Log.e("activity_cambiarcontra", "Las contraseñas no coinciden o el campo está vacío")
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verificarContrasenaActual(email: String, contrasena: String): Boolean {
        Log.d("activity_cambiarcontra", "Verificando contraseña para el email: $email")
        // Lógica para verificar la contraseña actual
        return true // Aquí debes agregar la lógica real de verificación
    }

    private fun cambiarContrasena(email: String, nuevaContrasena: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val contrasenaEncriptada = Encrypter().encrypt(nuevaContrasena)
                Log.d("activity_cambiarcontra", "Contraseña encriptada: $contrasenaEncriptada")

                val objConexion = ClaseConexion().cadenaConexion()
                val cambiarClave = objConexion?.prepareStatement("UPDATE tbUsuarios SET contrasena = ? WHERE emailusuario = ?")!!
                cambiarClave.setString(1, contrasenaEncriptada)
                cambiarClave.setString(2, email)
                cambiarClave.executeUpdate()

                val commit = objConexion.prepareStatement("COMMIT")!!
                commit.executeUpdate()

                withContext(Dispatchers.Main) {
                    Log.d("activity_cambiarcontra", "Contraseña cambiada exitosamente")
                    Toast.makeText(this@activity_cambiarcontra, "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                Log.e("activity_cambiarcontra", "Error al cambiar la contraseña: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@activity_cambiarcontra, "Error al cambiar la contraseña: $e", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
