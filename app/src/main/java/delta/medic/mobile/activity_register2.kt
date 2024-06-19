package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dc_Aseguradoras
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente2)
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val email = intent.getStringExtra("email")
        val clave = intent.getStringExtra("clave")

        val spSeguro = findViewById<Spinner>(R.id.spSeguro)

        CoroutineScope(Dispatchers.IO).launch {
            val listaSeguros =obtenerSeguros()
            val nombreSeguro =listaSeguros.map { it.nombreAseguradora }
            withContext(Dispatchers.Main){
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
                this,  // Use 'this' for Activity context
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
            val intent = Intent(this, activity_register3::class.java)
            startActivity(intent)
        }
    }
    private fun obtenerSeguros(): List<dc_Aseguradoras> {
        val objConexion =ClaseConexion().CadenaConexion()
        val statement = objConexion?.createStatement()!!
        val resultSet = statement.executeQuery("select * from tbAseguradora")
        val lista = mutableListOf<dc_Aseguradoras>()
        while (resultSet.next()) {
            val id_Aseguradora = resultSet.getDouble("id_Aseguradora")
            val nombreAseguradora = resultSet.getString("nombreAseguradora")
            val valoresJuntos =dc_Aseguradoras(id_Aseguradora, nombreAseguradora)
            lista.add(valoresJuntos)
        }
        return lista
    }
}