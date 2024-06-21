package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassCentro
import RecycleViewHelper.AdaptadorCentro
import RecycleViewHelper.ViewHolderCentro
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class activity_agendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agendar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val txtFecha = findViewById<TextView>(R.id.txtFecha)
        val rcvPaciente = findViewById<RecyclerView>(R.id.rcvPaciente)
        val layoutNoDoctor = findViewById<LinearLayout>(R.id.linearNoDoctor)
        val rcvCentro = findViewById<RecyclerView>(R.id.rcvCentro)
        rcvCentro.layoutManager = LinearLayoutManager(this)

        val btnEligeCentro = findViewById<ImageButton>(R.id.btnSelecSucursal)
        val doctorExists = checkIfDoctorExists()

        if (doctorExists) {
            layoutNoDoctor.visibility = View.GONE
            rcvCentro.visibility = View.VISIBLE
            loadSucursalData()
        } else {
            // Doctor data is not present, show the ImageButton layout
            layoutNoDoctor.visibility = View.VISIBLE
            rcvCentro.visibility = View.GONE
        }

        btnEligeCentro.setOnClickListener {
            val intent = Intent(this, fragment_Resultados::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        val rcvCentroMedico = findViewById<RecyclerView>(R.id.rcvCentro)
        rcvCentroMedico.layoutManager = LinearLayoutManager(this)

        fun obtenerDatos(): List<dataClassCentro>
        {
            val objConexion = ClaseConexion().CadenaConexion()
            val statement = objConexion?.createStatement()
            val resultset = statement?.executeQuery("insert into tbCentroMedico (favorito, ID_Doctor, ID_Sucursal) values (?, ?, ?)")!!
            val centros = mutableListOf<dataClassCentro>()
            while (resultset.next())
            {

            }
            return centros
        }
        CoroutineScope(Dispatchers.IO).launch {
            /*val centroMedico = obtenerDatos()
            withContext(Dispatchers.Main){
                val miAdapter = AdaptadorCentro(centroMedico)
                rcvCentro.adapter = miAdapter
            }*/
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ir_atras", true)
            startActivity(intent)
        }

        btnContinuar.setOnClickListener {
            showCustomDialog()
        }
    }



//Dialog customizado en tamaño
    private fun showCustomDialog() {
        val dialog = Dialog(this, R.style.CustomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_customizado)

        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(window.attributes)
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = layoutParams
        }

        val closeButton = dialog.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ir_atras", true)
            startActivity(intent)
        }

        val editButton = dialog.findViewById<TextView>(R.id.editButton)
        editButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
}


    private fun checkIfDoctorExists(): Boolean {
        // Implement your logic to check if the doctor data exists
        // For demonstration purposes, we return false to show the layoutNoDoctor
        return false
    }

    private fun loadSucursalData() {
        /*
        CoroutineScope(Dispatchers.IO).launch {
            val centro = obtenerDatos()
            withContext(Dispatchers.Main) {
                val miAdapter = ViewHolderCentro.AdaptadorCentro(centro) { centro ->
                    val intent = Intent(this@activity_agendar, fragment_Resultados::class.java)
                    intent.putExtra("ID_Centro", centro.id)
                    startActivityForResult(intent, REQUEST_CODE)
                }

                rcvCentro.adapter = miAdapter
            }
        }
        */
    }

    companion object {
        const val REQUEST_CODE = 1
    }
}