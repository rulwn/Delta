package delta.medic.mobile

import Modelo.EmailSender
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
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        val txtTienesUnaCuenta = findViewById<TextView>(R.id.txtTienesUnaCuenta3)
        txtTienesUnaCuenta.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente3)
        btnSiguiente.setOnClickListener {
            val intent = Intent(this, activity_register4::class.java)
            startActivity(intent)

        }
        btnOmitir.setOnClickListener {
            activity_register1.imgUsuario = "no hay imagen"


            CoroutineScope(Dispatchers.Main).launch {
                println("Correo enviado ${activity_register1.email}")
                EmailSender().enviarCorreo(activity_register1.email, "Codigo de verificacion delta", "${activity_register1.codigoautenticacion}")
            }
            val intent = Intent(this, activity_register4::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val archivoElegido: Uri? = data?.data
            if (archivoElegido != null) {
                fotoPerfil = archivoElegido
                btnAgregarFoto.setImageURI(archivoElegido)
            }
        }
    }
}