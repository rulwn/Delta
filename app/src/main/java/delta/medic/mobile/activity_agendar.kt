package delta.medic.mobile

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Html
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
import androidx.recyclerview.widget.RecyclerView


class activity_agendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agendar)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val btnSelecSucursal = findViewById<ImageButton>(R.id.btnSelecSucursal)
        val txtFecha = findViewById<TextView>(R.id.txtFecha)
        val rcvPaciente = findViewById<RecyclerView>(R.id.rcvPaciente)

        btnSelecSucursal.setOnClickListener {
            val intent = Intent(this, activity_busqueda::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
        val lbAgendarCita = findViewById<TextView>(R.id.lbAgendarCita)
        lbAgendarCita.setText(Html.fromHtml(getResources().getString(R.string.subrayado)))
/*
        rcvCentros.layoutManager = LinearLayoutManager(this)
        private suspend fun obtenerDatos(): List<dataClassCentro>
        {
            val objConexion = ClaseConexion().cadenaConexion()
            if (objConexion != null) {
            val statement = objConexion?.createStatement()
                val resultSet = statement?.executeQuery("select * from tbCentro")

                val ID_Centro = resultSet?.getInt("ID_Centro")


            }
            return productos
        }
        CoroutineScope(Dispatchers.IO).launch {
            val productosDB = obtenerDatos()
            withContext(Dispatchers.Main){
                val miAdapter = Adaptador(productosDB)
                rcvProductos.adapter = miAdapter
            }
        }

*/
        val layoutNoDoctor = findViewById<LinearLayout>(R.id.linearNoDoctor)
/*
        val btnEligeCentro = findViewById<ImageButton>(R.id.btnSelecSucursal)
        val doctorExists = checkIfDoctorExists()

        if (doctorExists) {
            layoutNoDoctor.visibility = View.GONE
            rcvCentro.visibility = View.VISIBLE
            // loadSucursalData()
        } else {
            // Doctor data is not present, show the ImageButton layout
            layoutNoDoctor.visibility = View.VISIBLE
            rcvCentro.visibility = View.GONE
        }

*/
        btnRegresar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("go_back", true)
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

/*
    private fun checkIfDoctorExists(): Boolean {
        // Implement your logic to check if the doctor data exists
        // For demonstration purposes, we return false to show the layoutNoDoctor
        return false
    }
*/
    companion object {
        const val REQUEST_CODE = 1
    }
}