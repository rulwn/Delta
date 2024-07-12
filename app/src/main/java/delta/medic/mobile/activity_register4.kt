package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.Encrypter
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_register4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register4)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val num1edit = findViewById<EditText>(R.id.num1edit)
        val num2edit = findViewById<EditText>(R.id.num2edit)
        val num3edit = findViewById<EditText>(R.id.num3edit)
        val num4edit = findViewById<EditText>(R.id.num4edit)
        val num5edit = findViewById<EditText>(R.id.num5edit)
        val num6edit = findViewById<EditText>(R.id.num6edit)

        num1edit.addTextChangedListener {
            text ->
            if(num1edit.text.toString() != ""){
                num2edit.requestFocus()
            }
        }

        num2edit.addTextChangedListener {
                text ->
            num3edit.requestFocus()
        }
        num3edit.addTextChangedListener {
                text ->
            num4edit.requestFocus()
        }
        num4edit.addTextChangedListener {
                text ->
            num5edit.requestFocus()
        }
        num5edit.addTextChangedListener {
                text ->
            num6edit.requestFocus()
        }
        num6edit.setOnKeyListener { v, keyCode, event ->
            //Revisar si se oprimio borrar        Revisar si se solto la tecla de borrar
            if(keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN){
                num6edit.setText("")
                num5edit.requestFocus()
                return@setOnKeyListener true
            }
            false
        }
        num5edit.setOnKeyListener { v, keyCode, event ->
            //Revisar si se oprimio borrar        Revisar si se solto la tecla de borrar
            if(keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN){
                num5edit.setText("")
                num4edit.requestFocus()
                return@setOnKeyListener true
            }
            false
        }
        num4edit.setOnKeyListener { v, keyCode, event ->
            //Revisar si se oprimio borrar        Revisar si se solto la tecla de borrar
            if(keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN){
                num4edit.setText("")
                num3edit.requestFocus()
                return@setOnKeyListener true
            }
            false
        }
        num3edit.setOnKeyListener { v, keyCode, event ->
            //Revisar si se oprimio borrar        Revisar si se solto la tecla de borrar
            if(keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN){
                num3edit.setText("")
                num2edit.requestFocus()
                return@setOnKeyListener true
            }
            false
        }
        num2edit.setOnKeyListener { v, keyCode, event ->
            //Revisar si se oprimio borrar        Revisar si se solto la tecla de borrar
            if(keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN){
                num2edit.setText("")
                num1edit.requestFocus()
                return@setOnKeyListener true
            }
            false
        }
        val btnSiguiente = findViewById<Button>(R.id.btnRegistrarse)
        btnSiguiente.setOnClickListener {
            var intento = num1edit.text.toString() + num2edit.text.toString() + num3edit.text.toString() + num4edit.text.toString() + num5edit.text.toString() + num6edit.text.toString()
            var intentoNum = intento.toInt()
           if(intentoNum == activity_register1.variablesLogin.codigoautenticacion) {
               CoroutineScope(Dispatchers.IO).launch{
                   val objConexion = ClaseConexion().cadenaConexion()
                   val agregarUsuario = objConexion?.prepareStatement("insert into tbUsuarios values(?,?,?,?,?,?,?,?,?,?,?,?)")!!
                   agregarUsuario.setInt(1,1)
                   agregarUsuario.setString(2, activity_register1.nombre)
                   agregarUsuario.setString(3,activity_register1.apellido)
                   agregarUsuario.setString(4,activity_register1.email)
                   agregarUsuario.setString(5, Encrypter().encrypt(activity_register1.clave))
                   agregarUsuario.setString(6,activity_register1.direccion)
                   agregarUsuario.setString(7,activity_register1.telefono)
                   agregarUsuario.setString(8, activity_register1.sexo)
                   agregarUsuario.setString(9, activity_register1.fechaNacimiento)
                   agregarUsuario.setString(10, activity_register1.imgUsuario)
                   agregarUsuario.setInt(11, 3)
                   agregarUsuario.setInt(12, activity_register1.idAseguradora)
                   agregarUsuario.executeUpdate()
                   objConexion.commit()
               }
           } else {
               Toast.makeText(this, "Codigo de verificacion incorrecto", Toast.LENGTH_SHORT).show()
           }

//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }
    }
}