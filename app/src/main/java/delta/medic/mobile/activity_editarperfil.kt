package delta.medic.mobile

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_editarperfil : AppCompatActivity() {

    suspend fun actualizarProductos(){

    }

    fun CargarDatosUsuario(txtnombre: EditText, txtApellido: EditText, txtCorreo:EditText,
    txtDirección: EditText, txtTeléfono: EditText, imgvFoto: EditText){
        txtnombre.setText(intent.getIntExtra("idUsuario"))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editarperfil)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*TODO Llamar a todos los elementos en pantalla para trabajar con ellos.*/

        //Estos TextViews han sido llamados para implementarles el cambio de tema luz :D
        val lbEditarPerfil = findViewById<TextView>(R.id.lbEditarPerfil)
        val lbNombreEP = findViewById<TextView>(R.id.lbNombreEP)
        val lbApellidoEP = findViewById<TextView>(R.id.lbApellidoEP)
        val lbCorreoEP = findViewById<TextView>(R.id.lbCorreoEP)
        val lbDirecciónEP = findViewById<TextView>(R.id.lbDirecciónEP)
        val lbTeléfonoEP = findViewById<TextView>(R.id.lbTeléfonoEP)

        //EditText
        val txtNombreEP = findViewById<EditText>(R.id.txtNombreEP)
        val txtApellidoEP = findViewById<EditText>(R.id.txtApellidoEP)
        val txtCorreoEP = findViewById<EditText>(R.id.txtCorreoEP)
        val txtDirecciónEP = findViewById<EditText>(R.id.txtDirecciónEP)
        val txtTeléfonoEP = findViewById<EditText>(R.id.txtTeléfonoEP)

        //ImageView
        val imgvFotoEP = findViewById<ImageView>(R.id.imgvFotoEP)
        val btnCancelarEP = findViewById<ImageView>(R.id.imgvCancelarEP)
        val btnActualizarUserEP = findViewById<ImageView>(R.id.imgvActualizarUserEP)



    }


}