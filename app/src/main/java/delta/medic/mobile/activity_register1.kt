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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register1)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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
            val nombre =nombreEditText.text.toString()
            val apellido =apellidoEditText.text.toString()
            val email =txtEmail.text.toString()
            val direccion = direccionEditText.text.toString()
            val clave = txtClave.text.toString()

            val intent = Intent(this, activity_register2::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("apellido",apellido)
            intent.putExtra("email", email)
            intent.putExtra("direccion", direccion)
            intent.putExtra("clave",clave)
            startActivity(intent)
        }
    }
}