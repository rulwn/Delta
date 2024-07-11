package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dc_Aseguradoras
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_register2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register2)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val rbHombre = findViewById<RadioButton>(R.id.rbHombre)
        val rbMujer = findViewById<RadioButton>(R.id.rbMujer)
        val rbTerminos = findViewById<RadioButton>(R.id.rbTerminos)
        val txtTelefono = findViewById<EditText>(R.id.txtTelefono)
        val spSeguro = findViewById<Spinner>(R.id.spSeguro)
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente2)
        val txtTienesUnaCuenta = findViewById<TextView>(R.id.txtTienesUnaCuenta2)
        txtTienesUnaCuenta.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }


        CoroutineScope(Dispatchers.IO).launch{
            val listaSeguros =obtenerSeguros()
            withContext(Dispatchers.Main){
                val nombreSeguro =listaSeguros.map { it.nombreAseguradora}
                val adaptador = ArrayAdapter(this@activity_register2, android.R.layout.simple_spinner_dropdown_item, nombreSeguro)
                spSeguro.adapter = adaptador


            }
        }


        val txtFechaNacimientoPaciente = findViewById<EditText>(R.id.txtFechadeNacimiento)
        txtFechaNacimientoPaciente.setOnClickListener {
            val calendario = java.util.Calendar.getInstance()
            val anio = calendario.get(java.util.Calendar.YEAR)
            val mes = calendario.get(java.util.Calendar.MONTH)
            val dia = calendario.get(java.util.Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                { view, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                    val fechaSeleccionada = "$anioSeleccionado/${mesSeleccionado + 1}/$diaSeleccionado"
                    txtFechaNacimientoPaciente.setText(fechaSeleccionada)
                },
                anio, mes, dia
            )
            datePickerDialog.show()
        }
        val imgFlechaAtras = findViewById<ImageView>(R.id.imgFlechaAtrasRegistro1)
        imgFlechaAtras.setOnClickListener {
            val intent = Intent(this, activity_register1::class.java)
            startActivity(intent)
        }


        btnSiguiente.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {

                if(rbHombre.isChecked) {
                    activity_register1.variablesLogin.sexo = "H"
                } else if (rbMujer.isChecked) {
                    activity_register1.variablesLogin.sexo = "M"
                }
                activity_register1.variablesLogin.fechaNacimiento = txtFechaNacimientoPaciente.text.toString()
                activity_register1.variablesLogin.telefono = txtTelefono.text.toString()
                activity_register1.variablesLogin.idAseguradora = spSeguro.selectedItemPosition
                val intent = Intent(this@activity_register2, activity_register3::class.java)

                startActivity(intent)
            }
        }
    }
    private suspend fun obtenerSeguros(): List<dc_Aseguradoras> {
        return withContext(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()!!
            val resultSet = statement.executeQuery("select * from tbAseguradora")
            val lista = mutableListOf<dc_Aseguradoras>()
            while (resultSet.next()) {
                val id_Aseguradora = resultSet.getInt("ID_ASEGURADORA")
                val nombreAseguradora = resultSet.getString("NOMBREASEGURADORA")
                val valoresJuntos = dc_Aseguradoras(id_Aseguradora, nombreAseguradora)
                lista.add(valoresJuntos)
            }
            lista
        }
    }
}