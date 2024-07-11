package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Date
import kotlin.properties.Delegates

class activity_register1 : AppCompatActivity() {
    companion object variablesLogin {
        lateinit var nombreUsuario: String
        lateinit var apellidoUsuario: String
        lateinit var correoUsuario: String
        lateinit var direccionUsuario: String
        lateinit var claveUsuario: String
        lateinit var sexoUsuario : String
        lateinit var telefonoUsuario: String
        lateinit var fecha_Nacimiento: String
        lateinit var fotoUsuario: String
        var seguroUsuario: Int? = null
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


        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        btnSiguiente.setOnClickListener {
            nombreUsuario =nombreEditText.text.toString()
            apellidoUsuario=apellidoEditText.text.toString()
            correoUsuario =txtEmail.text.toString()
            direccionUsuario = direccionEditText.text.toString()
            claveUsuario = txtClave.text.toString()

            val intent = Intent(this, activity_register2::class.java)
            startActivity(intent)
        }
    }
}