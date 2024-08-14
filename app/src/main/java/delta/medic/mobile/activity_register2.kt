package delta.medic.mobile

import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class activity_register2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register2)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rbHombre = findViewById<RadioButton>(R.id.rbHombre)
        val rbMujer = findViewById<RadioButton>(R.id.rbMujer)
        val rbTerminos = findViewById<RadioButton>(R.id.rbTerminos)
        val txtTelefono = findViewById<EditText>(R.id.txtTelefono)
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente2)
        val txtTienesUnaCuenta = findViewById<TextView>(R.id.txtTienesUnaCuenta2)
        val txtFechaNacimientoPaciente = findViewById<EditText>(R.id.txtFechadeNacimiento)

        txtTienesUnaCuenta.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }

        txtFechaNacimientoPaciente.setOnClickListener {
            val calendario = java.util.Calendar.getInstance()
            val anio = calendario.get(java.util.Calendar.YEAR)
            val mes = calendario.get(java.util.Calendar.MONTH)
            val dia = calendario.get(java.util.Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                { _, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                    val fechaSeleccionada = "$anioSeleccionado/${mesSeleccionado + 1}/$diaSeleccionado"
                    txtFechaNacimientoPaciente.setText(fechaSeleccionada)
                },
                anio, mes, dia
            )
            datePickerDialog.show()
        }

        val imgFlechaAtras = findViewById<ImageView>(R.id.imgFlechaAtrasRegistro2)
        imgFlechaAtras.setOnClickListener {
            val intent = Intent(this, activity_register1::class.java)
            startActivity(intent)
        }

        btnSiguiente.setOnClickListener {
            val sexo: String
            var hayVacio = false
            var hayError = false

            if (rbHombre.isChecked) {
                sexo = "H"
            } else if (rbMujer.isChecked) {
                sexo = "M"
            } else {
                Toast.makeText(this, "Selecciona un género", Toast.LENGTH_LONG).show()
                hayVacio = true
            }

            val fechaNacimiento = txtFechaNacimientoPaciente.text.toString()
            if (fechaNacimiento.isEmpty()) {
                txtFechaNacimientoPaciente.error = "Llena este campo"
                hayVacio = true
            } else {
                txtFechaNacimientoPaciente.error = null
            }

            val telefono = txtTelefono.text.toString()
            if (telefono.isEmpty()) {
                txtTelefono.error = "Llena este campo"
                hayVacio = true
            } else if (!telefono.matches(Regex("^[0-9]{4}[0-9]{4}$"))) {
                txtTelefono.error = "El teléfono está incorrecto"
                hayError = true
            } else {
                txtTelefono.error = null
            }

            if (rbTerminos.isChecked.not()) {
                Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_LONG).show()
                hayVacio = true
            }

            if (hayVacio || hayError) {
                Toast.makeText(this, "Verificar todos los campos", Toast.LENGTH_LONG).show()
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    if (rbHombre.isChecked) {
                        activity_register1.variablesLogin.sexo = "H"
                    } else if (rbMujer.isChecked) {
                        activity_register1.variablesLogin.sexo = "M"
                    }
                    activity_register1.variablesLogin.fechaNacimiento =
                        txtFechaNacimientoPaciente.text.toString()
                    activity_register1.variablesLogin.telefono = txtTelefono.text.toString()
                    val intent = Intent(this@activity_register2, activity_register3::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
