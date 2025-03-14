package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.EmailSender
import Modelo.Encrypter
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.InsetDrawable
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
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import delta.medic.mobile.R.id.txtNoTienesCuenta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_login : AppCompatActivity() {
    private var codigoRecu: Int = 0
    private lateinit var auth: FirebaseAuth

    companion object UserData {
        lateinit var userEmail: String
        private const val RC_SIGN_IN = 9001
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        auth = FirebaseAuth.getInstance()
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
        val btnGoogle = findViewById<Button>(R.id.btnGoogle)
        val txtOnBoard = findViewById<TextView>(R.id.txtOnBoard)
        val txtOnBoardTitle = findViewById<TextView>(R.id.txtOnBoardTitle)

        val currentUser = auth.currentUser

/*
val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
when (currentNightMode) {
    Configuration.UI_MODE_NIGHT_NO -> {
        txtOnBoard.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtOnBoardTitle.setTextColor(ContextCompat.getColor(this, R.color.black))
        btnGoogle.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        btnContinuar.setBackgroundColor(ContextCompat.getColor(this, R.color.Azul1))
        txtOlvidarContra.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtNotienecuenta.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtClave.setHintTextColor(ContextCompat.getColor(this, R.color.GrisHumo))
        txtEmail.setHintTextColor(ContextCompat.getColor(this, R.color.GrisHumo))
    } // Night mode is not active, we're using the light theme.
    Configuration.UI_MODE_NIGHT_YES -> {
        txtOnBoard.setTextColor(ContextCompat.getColor(this, R.color.white))
        txtOnBoardTitle.setTextColor(ContextCompat.getColor(this, R.color.white))
        btnGoogle.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        btnContinuar.setBackgroundColor(ContextCompat.getColor(this, R.color.Turquesa2))
        txtOlvidarContra.setTextColor(ContextCompat.getColor(this, R.color.white))
        txtNotienecuenta.setTextColor(ContextCompat.getColor(this, R.color.white))
        txtClave.setHintTextColor(ContextCompat.getColor(this, R.color.GrisHumo))
        txtEmail.setHintTextColor(ContextCompat.getColor(this, R.color.GrisHumo))
    } // Night mode is active, we're using dark theme.
}

 */
if (currentUser != null) {
    // The user is already signed in, navigate to MainActivity
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
}
btnGoogle.setOnClickListener {
    signIn()
    userEmail = "xam@gmail.com"
}
btnContinuar.setOnClickListener {

    CoroutineScope(Dispatchers.Main).launch {
        val intentoClave = Encrypter().encrypt(txtClave.text.toString())
        val inicio = inicioSesion(txtEmail.text.toString(), intentoClave)
        if (inicio) {
            Log.wtf("Intento de inicio", "Sesion iniciada")
            userEmail = txtEmail.text.toString()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@activity_login, "Sesion iniciada", Toast.LENGTH_SHORT)
                    .show()
                txtEmail.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_check,
                    0
                )
                txtClave.setBackgroundResource(R.drawable.textboxprueba)
                txtEmail.setBackgroundResource(R.drawable.textboxprueba)
                val userPreferences =
                    getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
                val editor = userPreferences.edit()
                editor.putBoolean("IsLogedIn", true)
                editor.putBoolean("IsWelcomed", true)
                editor.putString("email", userEmail)
                editor.apply()
                Log.d("Preferences", "Email: $userEmail")
                val intent = Intent(this@activity_login, activity_carga::class.java)
                startActivity(intent)
                finish()
            }

        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@activity_login,
                    "Error de Inicio de sesión, verifica las credenciales.",
                    Toast.LENGTH_SHORT
                ).show()
                val exitDrawable =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_exit)
                val insetDrawable =
                    InsetDrawable(exitDrawable, 0, 0, 16, 0) // 16dp de padding a la derecha

                txtEmail.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    insetDrawable,
                    null
                )
                txtEmail.setBackgroundResource(R.drawable.textboxpruebarojo)
                txtClave.setBackgroundResource(R.drawable.textboxpruebarojo)
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
        userEmail = txtEmailRecuperacion.text.toString()

        CoroutineScope(Dispatchers.Main).launch {
            // Llamada a la función suspendida verificarCorreo dentro de una corrutina
            if (txtEmailRecuperacion.text.isNotEmpty() && withContext(Dispatchers.IO) {
                    verificarCorreo(
                        userEmail
                    ) })
            {
                withContext(Dispatchers.IO) {
                    codigoRecu = (1000..9999).random()
                    EmailSender().enviarCorreo(
                        "$userEmail",
                        "Recuperación de contraseña Delta",
                        "$codigoRecu"
                    )
                }

                dialog.dismiss() // Cerrar el primer dialog

                // Mostrar el segundo dialog
                val segundoBuilder = AlertDialog.Builder(this@activity_login)
                val segundoLayout = LayoutInflater.from(this@activity_login)
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

                        val tercerBuilder = AlertDialog.Builder(this@activity_login)
                        val tercerLayout = LayoutInflater.from(this@activity_login)
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
                                        cambiarClave.setString(2, userEmail)
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
                Toast.makeText(this@activity_login, "Correo no registrado o incorrecto", Toast.LENGTH_LONG).show()
            }
        }
    }
    dialog.show()
}

}

private suspend fun inicioSesion(correo: String, clave: String): Boolean {
//Las funciones suspend se pueden llamar desde otras corrutinas u otras funciones de suspension
return withContext(Dispatchers.IO) {//Significa que la funcion se ejecuta en el hilo IO
    try {
        val objConexion = ClaseConexion().cadenaConexion()
        val buscarUsuario =
            objConexion?.prepareStatement("select * from tbUsuarios where emailusuario = ? and contrasena = ?")!!
        buscarUsuario.setString(1, correo)
        buscarUsuario.setString(2, clave)
        val filas =
            buscarUsuario.executeQuery() //Filas es igual al numero de filas que el select encuentre, idealmente será solo 1
        filas.next()//si filas tiene un valor, retornara true
    } catch (e: Exception) {
        println(e)
        false//Si el executeQuery falla y por lo tanto no se encuentran filas, retorna false
    }
}
}

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

val callback = onBackPressedDispatcher.addCallback(this) {
Toast.makeText(this@activity_login, "No puedes regresar", Toast.LENGTH_SHORT).show()
}

private fun signIn() {
val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestIdToken(getString(R.string.default_web_client_id))
    .requestEmail()
    .build()

val googleSignInClient = GoogleSignIn.getClient(this, gso)
val signInIntent = googleSignInClient.signInIntent
startActivityForResult(signInIntent, RC_SIGN_IN)
// esto es un intento de conseguir el email val user = auth.currentUser


}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
super.onActivityResult(requestCode, resultCode, data)

if (requestCode == RC_SIGN_IN) {
    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
    try {
        val account = task.getResult(ApiException::class.java)
        firebaseAuthWithGoogle(account.idToken!!)
    } catch (e: ApiException) {
        Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT)
            .show()
    }
}
}

private fun firebaseAuthWithGoogle(idToken: String) {
val credential = GoogleAuthProvider.getCredential(idToken, null)
auth.signInWithCredential(credential)
    .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
            // Autenticación exitosa, obtiene el usuario actual
            val user = auth.currentUser
            if (user != null) {
                Toast.makeText(this, "Signed in as ${user.displayName}", Toast.LENGTH_SHORT)
                    .show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this,
                    "No se pudo obtener la información del usuario",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
        }
    }
}

}