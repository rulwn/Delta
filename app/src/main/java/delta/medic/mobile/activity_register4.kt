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
        val btnSiguiente = findViewById<Button>(R.id.btnRegistrarse)

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

            //al cambiar el texto del num6, se activa el boton Siguiente

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



        btnSiguiente.setOnClickListener {
            if(num6edit.textSize < 1){
                Toast.makeText(this@activity_register4, "Por favor, pon el codigo completo", Toast.LENGTH_SHORT).show()
            } else {
                var intento = num1edit.text.toString() + num2edit.text.toString() + num3edit.text.toString() + num4edit.text.toString() + num5edit.text.toString() + num6edit.text.toString()
                var intentoNum = intento.toInt()
                if(intentoNum == activity_register1.variablesLogin.codigoautenticacion) {
                    CoroutineScope(Dispatchers.IO).launch{
                        try {
                            val objConexion = ClaseConexion().cadenaConexion()
                            val agregarUsuario = objConexion?.prepareStatement("insert into tbUsuarios (nombreusuario, apellidousuario,emailusuario,contrasena,direccion,telefonousuario,sexo,fechanacimiento,imgusuario,id_TipoUsuario) values(?,?,?,?,?,?,?,?,?,?)")!!
                            agregarUsuario.setString(1, activity_register1.nombre)
                            agregarUsuario.setString(2,activity_register1.apellido)
                            agregarUsuario.setString(3,activity_register1.email)
                            agregarUsuario.setString(4, Encrypter().encrypt(activity_register1.clave))
                            agregarUsuario.setString(5,activity_register1.direccion)
                            agregarUsuario.setString(6,activity_register1.telefono)
                            agregarUsuario.setString(7, activity_register1.sexo)
                            agregarUsuario.setString(8, activity_register1.fechaNacimiento)
                            agregarUsuario.setString(9, activity_register1.imgUsuario)
                            agregarUsuario.setInt(10,3)
                            agregarUsuario.executeUpdate()
                            val commit = objConexion.prepareStatement("commit")!!
                            commit.executeUpdate()
                            objConexion.commit()

                            withContext(Dispatchers.Main){
                                Toast.makeText(this@activity_register4, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@activity_register4, activity_login::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } catch (e: Exception) {
                            println("Error: $e")
                        }
                    }
                } else {
                    Toast.makeText(this, "Codigo de verificacion incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }
    }
}