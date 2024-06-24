package delta.medic.mobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import delta.medic.mobile.databinding.ActivityCambiarcontraBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_cambiarcontra : AppCompatActivity() {

    private lateinit var binding: ActivityCambiarcontraBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarcontraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnCambiarContrasena2.setOnClickListener {
            val user = Firebase.auth.currentUser!!

            val contrasenaActual: String = binding.txtContrasenaActual.text.toString()
            val nuevaContrasena: String = binding.txtNuevaContrasena.text.toString()
            val confirmarContrasena: String = binding.txtConfirmarContrasena.text.toString()

            val credential = EmailAuthProvider
                .getCredential(user.email.toString(), contrasenaActual)

            user.reauthenticate(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "La contraseña actual es correcta", Toast.LENGTH_SHORT).show()
                        if (nuevaContrasena == confirmarContrasena) {
                            user.updatePassword(nuevaContrasena)
                                .addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        Toast.makeText(this, "La contraseña se cambió con éxito", Toast.LENGTH_SHORT).show()

                                        binding.txtContrasenaActual.text.clear()
                                        binding.txtNuevaContrasena.text.clear()
                                        binding.txtConfirmarContrasena.text.clear()
                                    } else {
                                        Toast.makeText(this,"Ocurrió un error al cambiar la contraseña", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "La contraseña actual es incorrecta", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
