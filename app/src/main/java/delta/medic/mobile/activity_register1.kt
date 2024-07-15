package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_register1 : AppCompatActivity() {
    companion object variablesLogin {
        var nombre = ""
        var apellido = ""
        var email = ""
        var direccion = ""
        var clave = ""
        var sexo = ""
        var telefono = ""
        var fechaNacimiento = ""
        var imgUsuario = ""
        val codigoautenticacion = (100000..999999).random()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombreEditText = findViewById<EditText>(R.id.nombreEditText)
        val apellidoEditText = findViewById<EditText>(R.id.apellidoEditText)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val direccionEditText = findViewById<EditText>(R.id.direccionEditText)
        val txtClave = findViewById<EditText>(R.id.txtClave)
        val txtTienesUnaCuenta = findViewById<TextView>(R.id.txtTienesUnaCuenta1)
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)

        txtTienesUnaCuenta.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }

        btnSiguiente.setOnClickListener {
            nombre = nombreEditText.text.toString()
            apellido = apellidoEditText.text.toString()
            email = txtEmail.text.toString()
            direccion = direccionEditText.text.toString()
            clave = txtClave.text.toString()

            var hayVacio = false
            var hayError = false

            if (nombre.isEmpty()) {
                nombreEditText.error = "Llena este campo"
                hayVacio = true
            } else if (!nombre.matches(Regex("^[a-zA-Z]+$"))) {
                nombreEditText.error = "El nombre solo contiene letras"
                hayError = true
            } else {
                nombreEditText.error = null
            }

            if (apellido.isEmpty()) {
                apellidoEditText.error = "Llena este campo"
                hayVacio = true
            } else if (!apellido.matches(Regex("^[a-zA-Z]+$"))) {
                apellidoEditText.error = "El apellido solo contiene letras"
                hayError = true
            } else {
                apellidoEditText.error = null
            }

            if (email.isEmpty()) {
                txtEmail.error = "Llena este campo"
                hayVacio = true
            } else if (!email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+[.][a-z]+"))) {
                txtEmail.error = "El correo no tiene formato válido"
                hayError = true
            } else {
                txtEmail.error = null
            }

            if (direccion.isEmpty()) {
                direccionEditText.error = "Llena este campo"
                hayVacio = true
            } else {
                direccionEditText.error = null
            }

            if (clave.isEmpty()) {
                txtClave.error = "Llena este campo"
                hayVacio = true
            } else if (clave.length < 8) {
                txtClave.error = "La contraseña debe tener mínimo 8 caracteres"
                hayError = true
            } else {
                txtClave.error = null
            }

            if (hayVacio || hayError) {
                Toast.makeText(this, "Verificar todos los campos", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, activity_register2::class.java)
                startActivity(intent)
            }
        }
    }
}