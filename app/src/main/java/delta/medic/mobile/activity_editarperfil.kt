package delta.medic.mobile

import Modelo.ClaseConexion
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

    //Para los que pregunten, estas son las validaciones
    fun validarNombre(nombre: String): String? {
        val regex = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]*(?: [A-ZÁÉÍÓÚÑ][a-záéíóúñ]*)*$".toRegex()
        return when {
            nombre.isBlank() -> "El nombre/apellido no puede estar vacío."
            nombre.contains("  ") -> "No se permiten dobles espacios."
            !nombre.matches(regex) -> "El nombre/apellido debe comenzar con mayúscula y no contener caracteres especiales."
            else -> null
        }
    }

    fun validarApellido(apellido: String): String?{
        val regex = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]*(?: [A-ZÁÉÍÓÚÑ][a-záéíóúñ]*)*$".toRegex()
        return when {
            apellido.isBlank() -> "El apellido no puede estar vacío."
            apellido.contains("  ") -> "No se permiten dobles espacios."
            !apellido.matches(regex) -> "El apellido debe comenzar con mayúscula y no contener caracteres especiales."
            else -> null
        }
    }

    fun validarCorreo(correo: String): String? {
        val regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return when {
            correo.isBlank() -> "El correo no puede estar vacío."
            !correo.matches(regex) -> "El correo ingresado no es válido."
            else -> null
        }
    }

    fun validarTelefono(telefono: String): String? {
        val regex = "^\\d{4}-\\d{4}$".toRegex()
        return when {
            telefono.isBlank() -> "El teléfono no puede estar vacío."
            !telefono.matches(regex) -> "El teléfono debe tener el formato 1234-5678."
            else -> null
        }
    }


    //Este coso lo que hace es si el usuario ha ingresado 4 numeros, le agrega un guión
    fun formatTelefono(telefono: String): String {
        return if (telefono.length == 4) {
            "$telefono-"
        } else {
            telefono
        }
    }

    //Estos son los Listeners por si cambia el texto en los EditText

    fun setTextChangedNombre(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true

                val input = s.toString()
                var sanitizedInput = input

                // Bloquear doble espacio
                sanitizedInput = sanitizedInput.replace("\\s{2,}".toRegex(), " ")

                // Verificar si hay cambios
                if (input != sanitizedInput) {
                    editText.setText(sanitizedInput)
                    editText.setSelection(sanitizedInput.length)  // Coloca el cursor al final
                }

                // Validar el formato
                val errorMessage = validarNombre(sanitizedInput.trim())
                if (errorMessage != null) {
                    editText.error = errorMessage
                    editText.requestFocus()
                }

                isUpdating = false
            }
        })
    }

    fun setTextChangedApellido(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true

                val input = s.toString()
                var sanitizedInput = input

                // Bloquear doble espacio
                sanitizedInput = sanitizedInput.replace("\\s{2,}".toRegex(), " ")

                // Verificar si hay cambios
                if (input != sanitizedInput) {
                    editText.setText(sanitizedInput)
                    editText.setSelection(sanitizedInput.length)  // Coloca el cursor al final
                }

                // Validar el formato
                val errorMessage = validarApellido(sanitizedInput.trim())
                if (errorMessage != null) {
                    editText.error = errorMessage
                    editText.requestFocus()
                }

                isUpdating = false
            }
        })
    }

    fun setTextChangedCorreo(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString().trim()

                // Validar formato
                val errorMessage = validarCorreo(input)
                if (errorMessage != null) {
                    editText.error = errorMessage
                    editText.requestFocus()
                }
            }
        })
    }

    fun setTextChangedTelefono(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true

                var input = s.toString()
                input = input.replace("[^\\d-]".toRegex(), "")  // Solo permite dígitos y guiones
                input = input.replace("(\\d{4})(?!-)".toRegex(), "$1-")  // Añade un guión después de 4 dígitos

                // Verificar si hay cambios
                if (s.toString() != input) {
                    editText.setText(input)
                    editText.setSelection(input.length)
                }

                // Validar formato
                val errorMessage = validarTelefono(input)
                if (errorMessage != null) {
                    editText.error = errorMessage
                    editText.requestFocus()
                }

                isUpdating = false
            }
        })
    }

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

        // Asignar los cosos para ver si el usuario cambia el texto
        setTextChangedNombre(txtNombreEP)
        setTextChangedApellido(txtApellidoEP)
        setTextChangedCorreo(txtCorreoEP)
        setTextChangedTelefono(txtTeléfonoEP)

        btnActualizarUserEP.setOnClickListener{

            val nombreError = validarNombre(txtNombreEP.text.toString())
            val apellidoError = validarApellido(txtApellidoEP.text.toString())
            val correoError = validarCorreo(txtCorreoEP.text.toString())
            val telefonoError = validarTelefono(txtTeléfonoEP.text.toString())

            when {
                nombreError != null -> {
                    txtNombreEP.error = nombreError
                    txtNombreEP.requestFocus()
                }
                apellidoError != null -> {
                    txtApellidoEP.error = apellidoError
                    txtApellidoEP.requestFocus()
                }
                correoError != null -> {
                    txtCorreoEP.error = correoError
                    txtCorreoEP.requestFocus()
                }
                telefonoError != null -> {
                    txtTeléfonoEP.error = telefonoError
                    txtTeléfonoEP.requestFocus()
                }
                else -> {
                    // Si no hay errores, procede con la actualización
                    actualizarDatosUsuario(
                        txtNombreEP.text.toString(),
                        txtApellidoEP.text.toString(),
                        txtCorreoEP.text.toString(),
                        txtDirecciónEP.text.toString(),
                        txtTeléfonoEP.text.toString()
                    )
                    Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                    activity_login.userEmail = txtCorreoEP.text.toString()

                    intent = Intent(this, activity_login::class.java)
                    startActivity(intent)

                    finish()
                    MainActivity().finish()
                }
            }
        }

        btnCancelarEP.setOnClickListener{
            finish()
        }

    }




}