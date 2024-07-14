package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.EmailSender
import Modelo.Encrypter
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import delta.medic.mobile.R.id.txtNoTienesCuenta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_login : AppCompatActivity() {
    private var codigoRecu: Int = 0
    companion object UserData{
        lateinit var userEmail: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtClave = findViewById<EditText>(R.id.txtClave)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val txtOlvidarContra = findViewById<TextView>(R.id.txtOlvidarContra)
        val txtNotienecuenta = findViewById<TextView>(txtNoTienesCuenta)
        btnContinuar.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {
                val intentoClave =  Encrypter().encrypt(txtClave.text.toString())
             val inicio = inicioSesion(txtEmail.text.toString(), intentoClave)
              if (inicio) {
                  Log.wtf("Intento de inicio","Sesion iniciada")
                  userEmail = txtEmail.text.toString()

              } else {
                  withContext(Dispatchers.Main){
                      Toast.makeText(this@activity_login, "", Toast.LENGTH_SHORT).show()
                  }
              }
          }
        }


        txtNotienecuenta.setOnClickListener {
            val intent = Intent(this, activity_register1::class.java)
            startActivity(intent)
        }
        txtOlvidarContra.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogLayout = LayoutInflater.from(this).inflate(R.layout.dialog_correo_recuperacion, null)
            builder.setView(dialogLayout)
            val dialog =builder.create()
            val txtEmailRecuperacion =dialogLayout.findViewById<EditText>(R.id.txtEmailRecuperacion)
            val btnSiguienteAlert = dialogLayout.findViewById<Button>(R.id.btnSiguienteAlert)
            btnSiguienteAlert.setOnClickListener {
                userEmail = txtEmailRecuperacion.text.toString()
               CoroutineScope(Dispatchers.IO).launch {
                    codigoRecu = (1000..9999).random()
                   EmailSender().enviarCorreo("$userEmail","Recuperación de contraseña Delta","$codigoRecu")
               }
                dialog.dismiss()
                val segundoBuilder = AlertDialog.Builder(this)
                val segundoLayout = LayoutInflater.from(this).inflate(R.layout.dialog_codigo_recuperacion, null)
                segundoBuilder.setView(segundoLayout)
                val dialog2 = segundoBuilder.create()
                val txtRecu1 = segundoLayout.findViewById<EditText>(R.id.txtRecu1)
                val txtRecu2 = segundoLayout.findViewById<EditText>(R.id.txtRecu2)
                val txtRecu3 = segundoLayout.findViewById<EditText>(R.id.txtRecu3)
                val txtRecu4 = segundoLayout.findViewById<EditText>(R.id.txtRecu4)
                val btnRecuSig = segundoLayout.findViewById<Button>(R.id.btnRecuSig)

                txtRecu1.addTextChangedListener {
                        text ->
                    if(txtRecu1.text.toString() != ""){
                        txtRecu2.requestFocus()
                    }

                }
                txtRecu2.addTextChangedListener {
                        text ->
                    txtRecu3.requestFocus()
                }
                txtRecu3.addTextChangedListener {
                        text ->
                    txtRecu4.requestFocus()
                }

                txtRecu4.setOnKeyListener { v, keyCode, event ->
                    //Revisar si se oprimio borrar        Revisar si se solto la tecla de borrar
                    if(keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN){
                        txtRecu4.setText("")
                        txtRecu3.requestFocus()
                        return@setOnKeyListener true
                    }
                    false
                }
                txtRecu3.setOnKeyListener { v, keyCode, event ->
                    //Revisar si se oprimio borrar        Revisar si se solto la tecla de borrar
                    if(keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN){
                        txtRecu3.setText("")
                        txtRecu2.requestFocus()
                        return@setOnKeyListener true
                    }
                    false
                }
                txtRecu2.setOnKeyListener { v, keyCode, event ->
                    //Revisar si se oprimio borrar        Revisar si se solto la tecla de borrar
                    if(keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN){
                        txtRecu2.setText("")
                        txtRecu1.requestFocus()
                        return@setOnKeyListener true
                    }
                    false
                }
                btnRecuSig.setOnClickListener {
                    val textosJuntos = txtRecu1.text.toString() + txtRecu2.text.toString() + txtRecu3.text.toString() + txtRecu4.text.toString()
                    val intentoRecu = textosJuntos.toInt()
                    if(intentoRecu == codigoRecu){
                        Log.d("Recuperacion","Recuperacion exitosa")
                    }
                }
                dialog2.show()
            }
            dialog.show()
        }
    }
    private suspend fun inicioSesion(correo: String, clave:String): Boolean{
        //Las funciones suspend se pueden llamar desde otras corrutinas u otras funciones de suspension
        return withContext(Dispatchers.IO) {//Significa que la funcion se ejecuta en el hilo IO
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                val buscarUsuario = objConexion?.prepareStatement("select * from tbUsuarios where emailusuario = ? and contrasena = ?")!!
                buscarUsuario.setString(1, correo)
                buscarUsuario.setString(2,clave)
                val filas = buscarUsuario.executeQuery() //Filas es igual al numero de filas que el select encuentre, idealmente será solo 1
                filas.next()//si filas tiene un valor, retornara true
            } catch (e: Exception) {
                println(e)
                false//Si el executeQuery falla y por lo tanto no se encuentran filas, retorna false
            }
        }
    }
    val callback = onBackPressedDispatcher.addCallback(this) {
        Toast.makeText(this@activity_login, "No puedes regresar", Toast.LENGTH_SHORT).show()
    }
}