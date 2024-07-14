package delta.medic.mobile

import Modelo.ClaseConexion
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_editarperfil : AppCompatActivity() {


    fun CargarDatosUsuario(txtnombre: EditText, txtApellido: EditText, txtCorreo:EditText,
    txtDirección: EditText, txtTeléfono: EditText, imgvFoto: ImageView){
        try {
            txtnombre.setText(intent.getStringExtra("nombreUsuario"))
            txtApellido.setText(intent.getStringExtra("apellidoUsuario"))
            txtCorreo.setText(intent.getStringExtra("emailUsuario"))
            txtDirección.setText(intent.getStringExtra("dirección"))
            txtTeléfono.setText(intent.getStringExtra("teléfono"))

            txtnombre.setHint(txtnombre.text.toString())
            txtApellido.setHint(txtApellido.text.toString())
            txtCorreo.setHint(txtCorreo.text.toString())
            txtDirección.setHint(txtDirección.text.toString())
            txtTeléfono.setHint(txtTeléfono.text.toString())
        }
        catch (e: Exception){
            println(e.message)
        }
    }

    fun actualizarDatosUsuario(nombre: String, apellido: String, correo: String,Dirección: String, teléfono: String){

        try{
            val id = intent.getIntExtra("idUsuario", 0)
            CoroutineScope(Dispatchers.IO).launch{

                val objConnection = ClaseConexion().cadenaConexion()

                val updateUserData = objConnection?.prepareStatement("UPDATE tbUsuarios SET nombreUsuario = ?, " +
                        "apellidoUsuario = ?, emailUsuario = ?, direccion = ?, telefonousuario = ? where ID_Usuario =?")!!
                updateUserData.setString(1,nombre)
                updateUserData.setString(2,apellido)
                updateUserData.setString(3,correo)
                updateUserData.setString(4,Dirección)
                updateUserData.setString(5,teléfono)
                updateUserData.setInt(6,id)
                updateUserData.executeUpdate()

                val commit = objConnection.prepareStatement("commit")!!
                commit.executeUpdate()


            }
        }
        catch (e: Exception){
            println(e.message)
        }

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

        CargarDatosUsuario(txtNombreEP, txtApellidoEP, txtCorreoEP, txtDirecciónEP, txtTeléfonoEP, imgvFotoEP)

        btnActualizarUserEP.setOnClickListener{
            actualizarDatosUsuario(txtNombreEP.text.toString(), txtApellidoEP.text.toString(), txtCorreoEP.text.toString(), txtDirecciónEP.text.toString(), txtTeléfonoEP.text.toString())
        }

    }




}