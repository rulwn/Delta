package delta.medic.mobile

import Modelo.ClaseConexion
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
import delta.medic.mobile.R.id.txtNoTienesCuenta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_login : AppCompatActivity() {
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
        btnContinuar.setOnClickListener {
          CoroutineScope(Dispatchers.Main).launch {
             val inicio = inicioSesion(txtEmail.text.toString(), txtClave.text.toString())
              if (inicio) {
                  println("SI FUNCIONO")
              }
          }
        }

        val txtNotienecuenta = findViewById<TextView>(txtNoTienesCuenta)
        txtNotienecuenta.setOnClickListener {
            val intent = Intent(this, activity_register1::class.java)
            startActivity(intent)
        }


    }
    private suspend fun inicioSesion(correo: String, clave:String): Boolean{
        //Las funciones suspend se pueden llamar desde otras corrutinas u otras funciones de suspension
        return withContext(Dispatchers.IO) {//Significa que todo se ejecuta en el hilo IO
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                val buscarUsuario = objConexion?.prepareStatement("select * from test_u where Correo = ? and Clave = ?")!!
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
}