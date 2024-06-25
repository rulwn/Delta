package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_register4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register4)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val direccion = intent.getStringExtra("direccion")
        val email = intent.getStringExtra("email")
        val clave = intent.getStringExtra("clave")
        val aseguradora = intent.getIntExtra("aseguradora", 0)
        val sexo = intent.getStringExtra("sexo")
        val telefono = intent.getStringExtra("telefono")
        val foto = intent.getStringExtra("foto")


        val btnSiguiente = findViewById<Button>(R.id.btnRegistrarse)
        btnSiguiente.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}