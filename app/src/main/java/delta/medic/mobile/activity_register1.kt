package delta.medic.mobile

import Modelo.ValidationHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        val validar = ValidationHelper()

        validar.setTextChangedNombreApellido(nombreEditText)
        validar.setTextChangedNombreApellido(apellidoEditText)
        validar.setTextChangedCorreo(txtEmail)
        validar.validarContraseña(txtClave)

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
        nombreEditText.setBackgroundResource(R.drawable.textboxpruebarojo)
    } else if (!nombre.matches(Regex("^[a-zA-Z]+$"))) {
        nombreEditText.error = "El nombre solo contiene letras"
        hayError = true
        nombreEditText.setBackgroundResource(R.drawable.textboxpruebarojo)
    } else {
        nombreEditText.error = null
        nombreEditText.setBackgroundResource(R.drawable.textboxprueba)
    }

    if (apellido.isEmpty()) {
        apellidoEditText.error = "Llena este campo"
        hayVacio = true
        apellidoEditText.setBackgroundResource(R.drawable.textboxpruebarojo)
    } else if (!apellido.matches(Regex("^[a-zA-Z]+$"))) {
        apellidoEditText.error = "El apellido solo contiene letras"
        hayError = true
        apellidoEditText.setBackgroundResource(R.drawable.textboxpruebarojo)
    } else {
        apellidoEditText.error = null
        apellidoEditText.setBackgroundResource(R.drawable.textboxprueba)
    }

    if (email.isEmpty()) {
        txtEmail.error = "Llena este campo"
        hayVacio = true
        txtEmail.setBackgroundResource(R.drawable.textboxpruebarojo)
    } else if (!email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+[.][a-z]+"))) {
        txtEmail.error = "El correo no tiene formato válido"
        hayError = true
        txtEmail.setBackgroundResource(R.drawable.textboxpruebarojo)
    } else {
        txtEmail.error = null
        txtEmail.setBackgroundResource(R.drawable.textboxprueba)
    }

    if (direccion.isEmpty()) {
        direccionEditText.error = "Llena este campo"
        hayVacio = true
        direccionEditText.setBackgroundResource(R.drawable.textboxpruebarojo)
    } else {
        direccionEditText.error = null
        direccionEditText.setBackgroundResource(R.drawable.textboxprueba)
    }

    if (clave.isEmpty()) {
        txtClave.error = "Llena este campo"
        hayVacio = true
        txtClave.setBackgroundResource(R.drawable.textboxpruebarojo)
    } else if (clave.length < 8) {
        txtClave.error = "La contraseña debe tener mínimo 8 caracteres"
        hayError = true
        txtClave.setBackgroundResource(R.drawable.textboxpruebarojo)
    } else {
        txtClave.error = null
        txtClave.setBackgroundResource(R.drawable.textboxprueba)
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