package delta.medic.mobile

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
        btnSiguiente.setOnClickListener {
            val intent = Intent(this, activity_register3::class.java)
            startActivity(intent)
        }
        fun OnclickCalendarFecha(v: View?){
            val txtFecha = findViewById<EditText>(R.id.txtFechadeNacimiento)
            txtFecha.setOnClickListener {
                val selectedCalendar = Calendar.getInstance()
                val ano = selectedCalendar.get(Calendar.YEAR)
                val mes = selectedCalendar.get(Calendar.MONTH)
                val Diadelmes = selectedCalendar.get(Calendar.DAY_OF_MONTH)
                val listener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
                    txtFecha.setText("$d/$m/$y")
                }
                DatePickerDialog(this, listener, ano, mes, Diadelmes).show()
            }
        }
        val imgFlechaAtras = findViewById<ImageView>(R.id.imgFlechaAtrasRegistro1)
        imgFlechaAtras.setOnClickListener {
            val intent = Intent(this, activity_register1::class.java)
            startActivity(intent)
        }
    }
}