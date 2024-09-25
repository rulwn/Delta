package delta.medic.mobile

import Modelo.InputValidator
import android.annotation.SuppressLint
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

    var passValid = false
    var nameValid = false
    var lastNameValid = false
    var emailValid = false
    @SuppressLint("MissingInflatedId", "WrongViewCast")
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
        val textoGrande = findViewById<TextView>(R.id.txtTextoGrande)
        val frase = findViewById<TextView>(R.id.frase)

        val validar = InputValidator()



        validar.setTextChangedNombreApellido(nombreEditText) { isValid ->
            nameValid = isValid
        }
        validar.setTextChangedNombreApellido(apellidoEditText) { isValid ->
            lastNameValid = isValid
        }
        validar.setTextChangedCorreo(txtEmail) { isValid ->
            emailValid = isValid

        }
        validar.setTextChangedPassword(txtClave) { isValid ->
            passValid = isValid
        }

        /*
val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
when (currentNightMode) {
    Configuration.UI_MODE_NIGHT_NO -> {
        textoGrande.setTextColor(ContextCompat.getColor(this, R.color.black))
        frase.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtTienesUnaCuenta.setTextColor(ContextCompat.getColor(this, R.color.black))
        btnSiguiente.setBackgroundColor(ContextCompat.getColor(this, R.color.Azul2))
        nombreEditText.setTextColor(ContextCompat.getColor(this, R.color.black))
        apellidoEditText.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtEmail.setTextColor(ContextCompat.getColor(this, R.color.black))
        direccionEditText.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtClave.setHintTextColor(ContextCompat.getColor(this, R.color.black))
    } // Night mode is not active, we're using the light theme.
    Configuration.UI_MODE_NIGHT_YES -> {
        textoGrande.setTextColor(ContextCompat.getColor(this, R.color.white))
        frase.setTextColor(ContextCompat.getColor(this, R.color.white))
        txtTienesUnaCuenta.setTextColor(ContextCompat.getColor(this, R.color.white))
        btnSiguiente.setBackgroundColor(ContextCompat.getColor(this, R.color.Turquesa2))
        nombreEditText.setHintTextColor(ContextCompat.getColor(this, R.color.GrisHumo))
        apellidoEditText.setHintTextColor(ContextCompat.getColor(this, R.color.GrisHumo))
        txtEmail.setHintTextColor(ContextCompat.getColor(this, R.color.GrisHumo))
        direccionEditText.setHintTextColor(ContextCompat.getColor(this, R.color.GrisHumo))
        txtClave.setHintTextColor(ContextCompat.getColor(this, R.color.GrisHumo))
        } // Night mode is active, we're using dark theme.
}

 */

        txtTienesUnaCuenta.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }


        btnSiguiente.setOnClickListener {

            nombre = nombreEditText.text.toString().trim()
            apellido = apellidoEditText.text.toString().trim()
            email = txtEmail.text.toString().trim()
            direccion = direccionEditText.text.toString().trim()
            clave = txtClave.text.toString()

            var error = false

            if (nombre.isEmpty()) {
                nombreEditText.error = "Este campo no puede estar vacío."
                nombreEditText.requestFocus()
                nombreEditText.setBackgroundResource(R.drawable.textboxpruebarojo)
            } else if (apellido.isEmpty()) {
                apellidoEditText.error = "Este campo no puede estar vacío."
                apellidoEditText.requestFocus()
                apellidoEditText.setBackgroundResource(R.drawable.textboxpruebarojo)
            } else if (email.isEmpty()) {
                txtEmail.error = "Este campo no puede estar vacío."
                txtEmail.requestFocus()
                txtEmail.setBackgroundResource(R.drawable.textboxpruebarojo)
            } else if (direccion.isEmpty()) {
                direccionEditText.error = "Este campo no puede estar vacío."
                direccionEditText.requestFocus()
                direccionEditText.setBackgroundResource(R.drawable.textboxpruebarojo)
            }else if (clave.isEmpty()) {
                txtClave.error = "Este campo no puede estar vacío."
                txtClave.requestFocus()
                txtClave.setBackgroundResource(R.drawable.textboxpruebarojo)
            }else if (passValid && emailValid && nameValid && lastNameValid) {
                val intent = Intent(this, activity_register2::class.java)
                startActivity(intent)
            }
        }
    }
}