package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.EmailSender
import Modelo.Encrypter
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import delta.medic.mobile.databinding.ActivityCuentaConfiBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.SQLException
import delta.medic.mobile.activity_login.UserData.userEmail
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

class activity_cuenta_confi : AppCompatActivity() {

    private var codigoRecu: Int = 0
    private lateinit var binding: ActivityCuentaConfiBinding

    private lateinit var txtNom: TextView
    private lateinit var txtApe: TextView
    private lateinit var txtCorr: TextView
    private lateinit var txtDire: TextView
    private lateinit var txtTel: TextView
    private lateinit var txtSex: TextView
    private lateinit var txtFech: TextView

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuenta_confi)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        // Inicializar los TextViews
        txtNom = findViewById(R.id.txtNom)
        txtApe = findViewById(R.id.txtApe)
        txtCorr = findViewById(R.id.txtCorr)
        txtDire = findViewById(R.id.txtDire)
        txtTel = findViewById(R.id.txtTel)
        txtSex = findViewById(R.id.txtSex)
        txtFech = findViewById(R.id.txtFech)

        val btnEliminarUsuario = findViewById<TextView>(R.id.btnEliminarUsuarioS)

        btnEliminarUsuario.setOnClickListener {
            showDeleteUserDialog()
        }

        //Llamar la función para cargar los datos del usuario
        CoroutineScope(Dispatchers.Main).launch {
            val fragmentUsuario = fragment_usuario()
            val user = fragmentUsuario.GetUserParameters(userEmail)
            val nombreUsuario = user.map { it.nombreUsuario }
            val apellidoUsuario = user.map { it.apellidoUsuario }
            val emailUsuario = user.map { it.emailUsuario }
            val direccion = user.map { it.dirección }
            val telefonoUsuario = user.map { it.teléfonoUsuario }
            val sexo = user.map { it.sexo }
            val fechaNacimiento = user.map { it.fechaNacimiento }

            withContext(Dispatchers.Main) {
                //Para solucionarlo se coloca replace
                txtNom.setText(nombreUsuario.toString().replace("[", "").replace("]", ""))
                txtApe.setText(apellidoUsuario.toString().replace("[", "").replace("]", ""))
                txtCorr.setText(emailUsuario.toString().replace("[", "").replace("]", ""))
                txtDire.setText(direccion.toString().replace("[", "").replace("]", ""))
                txtTel.setText(telefonoUsuario.toString().replace("[", "").replace("]", ""))
                txtSex.setText(sexo.toString().replace("[", "").replace("]", ""))
                txtFech.setText(fechaNacimiento.toString().replace("[", "").replace("]", ""))
            }
        }

        //Modo claro y oscuro
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val btnCambiarContrasena = findViewById<Button>(R.id.btnCambiarContrasena)
        val txtCuenta = findViewById<TextView>(R.id.txtCuentaConfi)

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                txtCuenta.setTextColor(ContextCompat.getColor(this, R.color.black))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
                txtNom.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtCorr.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtDire.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTel.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtFech.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtSex.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtApe.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                txtCuenta.setTextColor(ContextCompat.getColor(this, R.color.white))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                txtNom.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtCorr.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtDire.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTel.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtFech.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtSex.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtApe.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }

        btnRegresar.setOnClickListener {
            finish()
        }

        /*btnEliminarUsuario.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val isDeleted = deleteUser(userEmail)
                if (isDeleted) {
                    Toast.makeText(this@activity_cuenta_confi, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@activity_cuenta_confi, activity_login::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@activity_cuenta_confi, "No se encontró el usuario con email $userEmail", Toast.LENGTH_SHORT).show()
                }
            }
        }*/

        btnCambiarContrasena.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogLayout =
                LayoutInflater.from(this).inflate(R.layout.dialog_correo_recuperacion, null)
            builder.setView(dialogLayout)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(R.drawable.textboxprueba)
            val txtEmailRecuperacion =
                dialogLayout.findViewById<EditText>(R.id.txtEmailRecuperacion)
            val btnSiguienteAlert = dialogLayout.findViewById<Button>(R.id.btnSiguienteAlert)

            btnSiguienteAlert.setOnClickListener {
                val correo = txtEmailRecuperacion.text.toString()
                if(correo == userEmail){
                    CoroutineScope(Dispatchers.Main).launch {
                        // Llamada a la función suspendida verificarCorreo dentro de una corrutina
                        if (txtEmailRecuperacion.text.isNotEmpty() && withContext(Dispatchers.IO) {
                                verificarCorreo(
                                    correo
                                )
                            }) {
                            withContext(Dispatchers.IO) {
                                codigoRecu = (1000..9999).random()
                                EmailSender().enviarCorreo(
                                    "$correo",
                                    "Recuperación de contraseña Delta",
                                    "$codigoRecu"
                                )
                            }

                            dialog.dismiss() // Cerrar el primer dialog

                            // Mostrar el segundo dialog
                            val segundoBuilder = AlertDialog.Builder(this@activity_cuenta_confi)
                            val segundoLayout = LayoutInflater.from(this@activity_cuenta_confi)
                                .inflate(R.layout.dialog_codigo_recuperacion, null)
                            segundoBuilder.setView(segundoLayout)
                            val dialog2 = segundoBuilder.create()
                            dialog2.window?.setBackgroundDrawableResource(R.drawable.textboxprueba)
                            val txtRecu1 = segundoLayout.findViewById<EditText>(R.id.txtRecu1)
                            val txtRecu2 = segundoLayout.findViewById<EditText>(R.id.txtRecu2)
                            val txtRecu3 = segundoLayout.findViewById<EditText>(R.id.txtRecu3)
                            val txtRecu4 = segundoLayout.findViewById<EditText>(R.id.txtRecu4)
                            val btnRecuSig = segundoLayout.findViewById<Button>(R.id.btnRecuSig)

                            txtRecu1.addTextChangedListener {
                                if (txtRecu1.text.toString().isNotEmpty()) {
                                    txtRecu2.requestFocus()
                                }
                            }
                            txtRecu2.addTextChangedListener {
                                txtRecu3.requestFocus()
                            }
                            txtRecu3.addTextChangedListener {
                                txtRecu4.requestFocus()
                            }

                            txtRecu4.setOnKeyListener { v, keyCode, event ->
                                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                                    txtRecu4.setText("")
                                    txtRecu3.requestFocus()
                                    return@setOnKeyListener true
                                }
                                false
                            }
                            txtRecu3.setOnKeyListener { v, keyCode, event ->
                                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                                    txtRecu3.setText("")
                                    txtRecu2.requestFocus()
                                    return@setOnKeyListener true
                                }
                                false
                            }
                            txtRecu2.setOnKeyListener { v, keyCode, event ->
                                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                                    txtRecu2.setText("")
                                    txtRecu1.requestFocus()
                                    return@setOnKeyListener true
                                }
                                false
                            }

                            btnRecuSig.setOnClickListener {
                                val textosJuntos =
                                    txtRecu1.text.toString() + txtRecu2.text.toString() + txtRecu3.text.toString() + txtRecu4.text.toString()
                                val intentoRecu = textosJuntos.toInt()
                                if (intentoRecu == codigoRecu) {
                                    Log.d("Recuperacion", "Recuperacion exitosa")
                                    dialog2.dismiss()

                                    val tercerBuilder = AlertDialog.Builder(this@activity_cuenta_confi)
                                    val tercerLayout = LayoutInflater.from(this@activity_cuenta_confi)
                                        .inflate(R.layout.dialog_cambiar_clave, null)
                                    tercerBuilder.setView(tercerLayout)
                                    val dialog3 = tercerBuilder.create()
                                    dialog3.window?.setBackgroundDrawableResource(R.drawable.textboxprueba)
                                    val txtNuevaClave =
                                        tercerLayout.findViewById<EditText>(R.id.txtNuevaClave)
                                    val txtConfirmarNuevaClave =
                                        tercerLayout.findViewById<EditText>(R.id.txtConfirmarNuevaClave)
                                    val btnConfirmarCambio =
                                        tercerLayout.findViewById<Button>(R.id.btnConfirmarCambio)

                                    btnConfirmarCambio.setOnClickListener {
                                        if (txtConfirmarNuevaClave.text.toString() == txtNuevaClave.text.toString()) {
                                            val contra =
                                                Encrypter().encrypt(txtNuevaClave.text.toString())
                                            CoroutineScope(Dispatchers.IO).launch {
                                                try {
                                                    val objConexion = ClaseConexion().cadenaConexion()
                                                    val cambiarClave =
                                                        objConexion?.prepareStatement("update tbUsuarios set contrasena = ? where emailusuario = ?")!!
                                                    cambiarClave.setString(1, contra)
                                                    cambiarClave.setString(2, correo)
                                                    cambiarClave.executeUpdate()
                                                    val commit =
                                                        objConexion.prepareStatement("commit")!!
                                                    commit.executeUpdate()
                                                } catch (e: Exception) {
                                                    println("Error: $e")
                                                }
                                            }
                                        }
                                        dialog3.dismiss()
                                    }
                                    dialog3.show()
                                }
                            }
                            dialog2.show()
                        } else {
                            // Mostrar mensaje de error si el correo no es válido o está vacío
                            Toast.makeText(this@activity_cuenta_confi, "Correo inválido", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else{
                    Toast.makeText(this@activity_cuenta_confi, "No puedes ingresar correos que no sea el tuyo", Toast.LENGTH_SHORT)
                        .show()
                }


            }
            dialog.show()
        }

    }

    // Función para cargar datos del usuario desde la base de datos
    /*private suspend fun CargarDatosUsuario(userEmail: String): dataClassUsuario? {
        return withContext(Dispatchers.IO) {
            var usuario: dataClassUsuario? = null
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement(
                        "SELECT nombreUsuario, apellidoUsuario, emailUsuario, direccion, telefono, sexo, fechanacimiento " +
                                "FROM tbUsuarios WHERE emailUsuario = ?"
                    )
                    statement.setString(1, userEmail)

                    val resultSet = statement.executeQuery()
                    if (resultSet.next()) {
                        usuario = dataClassUsuario(
                            nombreUsuario = resultSet.getString("nombreUsuario"),
                            apellidoUsuario = resultSet.getString("apellidoUsuario"),
                            emailUsuario = resultSet.getString("emailUsuario"),
                            dirección = resultSet.getString("direccion"),
                            teléfonoUsuario = resultSet.getString("telefono"),
                            sexo = resultSet.getString("sexo"),
                            fechaNacimiento = resultSet.getString("fechanacimiento")
                        )
                    }

                    resultSet.close()
                    statement.close()
                    objConexion.close()
                } else {
                    println("No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: SQLException) {
                println("Error en la consulta SQL: ${e.message}")
            } catch (e: Exception) {
                println("Este es el error: ${e.message}")
            }
            usuario
        }
    }
        */


    //Verificar el correo para el cambio de contraseña
    private suspend fun verificarCorreo(correo: String): Boolean {
        //Las funciones suspend se pueden llamar desde otras corrutinas u otras funciones de suspension
        return withContext(Dispatchers.IO) {//Significa que la funcion se ejecuta en el hilo IO
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                val buscarUsuario =
                    objConexion?.prepareStatement("select * from tbUsuarios where emailusuario = ?")!!
                buscarUsuario.setString(1, correo)
                val filas =
                    buscarUsuario.executeQuery() //Filas es igual al numero de filas que el select encuentre, idealmente será solo 1
                filas.next()//si filas tiene un valor, retornara true
            } catch (e: Exception) {
                println(e)
                false//Si el executeQuery falla y por lo tanto no se encuentran filas, retorna false
            }
        }
    }

    // Mostrar el diálogo para eliminar el usuario
    @SuppressLint("MissingInflatedId")
    private fun showDeleteUserDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogLayout = LayoutInflater.from(this).inflate(R.layout.dialog_borrar_cuenta, null)
        builder.setView(dialogLayout)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.drawable.textboxprueba)

        val txtPasswordConfirm = dialogLayout.findViewById<EditText>(R.id.txtCorreoBorrar)
        val txtPasswordRepeat = dialogLayout.findViewById<EditText>(R.id.txtCorreoRepetidoBorrar)
        val btnConfirmDelete = dialogLayout.findViewById<Button>(R.id.btnBorrarCuenta)

        btnConfirmDelete.setOnClickListener {
            val enteredPassword = Encrypter().encrypt(txtPasswordConfirm.text.toString())
            val repeatedPassword = Encrypter().encrypt(txtPasswordRepeat.text.toString())

            CoroutineScope(Dispatchers.Main).launch {
                if (enteredPassword.isNotEmpty() && enteredPassword == repeatedPassword) {
                    if (withContext(Dispatchers.IO) { verifyPassword(userEmail, enteredPassword) }) {
                        // Si la contraseña es correcta, eliminar el usuario
                        val isDeleted = deleteUser(userEmail)
                        if (isDeleted) {
                            Toast.makeText(this@activity_cuenta_confi, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@activity_cuenta_confi, activity_login::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@activity_cuenta_confi, "No se encontró el usuario con email $userEmail", Toast.LENGTH_SHORT).show()
                        }
                        dialog.dismiss()
                    } else {
                        txtPasswordConfirm.error = "Contraseña incorrecta"
                    }
                } else {
                    txtPasswordRepeat.error = "Las contraseñas no coinciden"
                }
            }
        }
    }

    // Verificar la contraseña del usuario
    suspend fun verifyPassword(emailUsuario: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            var isValid = false
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement(
                        "SELECT COUNT(*) FROM tbUsuarios WHERE emailUsuario = ? AND contrasena = ?"
                    )
                    statement.setString(1, emailUsuario)
                    statement.setString(2, password)

                    val resultSet = statement.executeQuery()
                    if (resultSet.next()) {
                        isValid = resultSet.getInt(1) > 0
                    }

                    resultSet.close()
                    statement.close()
                    objConexion.close()
                }
            } catch (e: SQLException) {
                println("Error en la consulta SQL: ${e.message}")
            }
            isValid
        }
    }


    // Eliminar usuario
    suspend fun deleteUser(emailUsuario: String): Boolean {
        return withContext(Dispatchers.IO) {
            var isDeleted = false
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement("DELETE FROM tbUsuarios WHERE emailUsuario = ?")
                    statement.setString(1, emailUsuario)

                    val affectedRows = statement.executeUpdate()
                    if (affectedRows > 0) {
                        isDeleted = true
                        println("Usuario con email $emailUsuario eliminado correctamente.")
                    } else {
                        println("No se encontraron usuarios con el email $emailUsuario para eliminar.")
                    }

                    statement.close()
                    objConexion.close()
                } else {
                    println("No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: SQLException) {
                println("Error en la consulta SQL: ${e.message}")
            } catch (e: Exception) {
                println("Este es el error: ${e.message}")
            }
            isDeleted
        }
    }
}