package delta.medic.mobile

import android.app.Activity
import android.content.Intent
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        btnAgregarFoto = findViewById(R.id.btnAgregarFoto)
        val btnOmitir = findViewById<TextView>(R.id.btnOmitirFoto)
        btnAgregarFoto.setOnClickListener {
            val intento = Intent(Intent.ACTION_PICK)
                intento.type = "image/*"
            startActivityForResult(Intent.createChooser(intento, "Escoge una imagen"), 111) // el requestCode es personalizado
        }

        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente3)
        btnSiguiente.setOnClickListener {
            val intent = Intent(this, activity_register4::class.java)
            startActivity(intent)

        }

        btnOmitir.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val archivoElegido: Uri? = data?.data
            if (archivoElegido != null) {
                fotoPerfil = archivoElegido
                btnAgregarFoto.setImageURI(archivoElegido)
            }
        }
    }
}