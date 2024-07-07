package delta.medic.mobile

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.net.Uri
import android.widget.TextView

class activity_register3 : AppCompatActivity() {

    private lateinit var btnAgregarFoto: ImageView
    private var fotoPerfil: Uri? = null //Uri es una referencia a un recurso, como una imagen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register3)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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



        btnAgregarFoto = findViewById(R.id.btnAgregarFoto)

        btnAgregarFoto.setOnClickListener {
            val intento = Intent()
                .setType("image/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intento, "Escoge una imagen"), 111) // el requestCode es personalizado
        }
        val txtTienesUnaCuenta = findViewById<TextView>(R.id.txtTienesUnaCuenta3)
        txtTienesUnaCuenta.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente3)
        btnSiguiente.setOnClickListener {
            val intent = Intent(this, activity_register4::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("apellido", apellido)
            intent.putExtra("direccion", direccion)
            intent.putExtra("email", email)
            intent.putExtra("clave", clave)
            intent.putExtra("aseguradora", aseguradora)
            intent.putExtra("sexo", sexo)
            intent.putExtra("telefono", telefono)
            intent.putExtra("foto",fotoPerfil.toString())
            startActivity(intent)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            val archivoElegido: Uri? = data?.data
            if (archivoElegido != null) {
                fotoPerfil = archivoElegido
                btnAgregarFoto.setImageURI(archivoElegido)
            }
        }
    }
}