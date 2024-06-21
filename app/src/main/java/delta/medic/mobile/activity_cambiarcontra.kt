package delta.medic.mobile

import android.os.Bundle
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

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_cambiarcontra)

            txtContrasenaActual = findViewById(R.id.txtContrasenaActual)
            txtNuevaContrasena = findViewById(R.id.txtNuevaContrasena)
            txtConfirmarContrasena = findViewById(R.id.txtConfirmarContrasena)
            btnCambiarContrasena = findViewById(R.id.btnCambiarContrasena)

            btnCambiarContrasena.setOnClickListener {
                val contrasenaActual = txtContrasenaActual.text.toString()
                val nuevaContrasena = txtNuevaContrasena.text.toString()
                val confirmarContrasena = txtConfirmarContrasena.text.toString()

                // Validar contraseñas
                if (nuevaContrasena != confirmarContrasena) {
                    Toast.makeText(this, "Las contraseñas no coinciden, verifica nuevamente", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                CoroutineScope(Dispatchers.IO).launch {
                        val response = cambiarContrasena(contrasenaActual, nuevaContrasena)
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@activity_cambiarcontra, "La contraseña se cambió con éxito", Toast.LENGTH_SHORT).show()

                                txtContrasenaActual.text.clear()
                                txtNuevaContrasena.text.clear()
                                txtConfirmarContrasena.text.clear()
                            } else {
                                Toast.makeText(this@activity_cambiarcontra, "Ocurrió un error al cambiar la contraseña", Toast.LENGTH_SHORT).show()
                            }
                        }
                }

            }
        }
}