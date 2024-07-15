package delta.medic.mobile

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController

class activity_register1 : AppCompatActivity() {
    companion object variablesLogin{
        var nombre = ""
        var apellido = ""
        var email = ""
        var direccion = ""
        var clave = ""
        var sexo = ""
        var telefono = ""
        var fechaNacimiento = ""
        var imgUsuario = ""
        val codigoautenticacion =(100000..999999).random()
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

        txtTienesUnaCuenta.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }


        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        btnSiguiente.setOnClickListener {
            nombre =nombreEditText.text.toString()
            apellido=apellidoEditText.text.toString()
            email =txtEmail.text.toString()
            direccion = direccionEditText.text.toString()
            clave = txtClave.text.toString()

            val intent = Intent(this, activity_register2::class.java)
            startActivity(intent)
        }
    }
}