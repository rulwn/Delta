package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassSucursal
import RecycleViewHelper.AdaptadorSucursal
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
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
        val rcvSucursal = findViewById<RecyclerView>(R.id.rcvSucursal)
        val rcvPaciente = findViewById<RecyclerView>(R.id.rcvPaciente)
/*
        val rcvProductos = findViewById<RecyclerView>(R.id.rcvSucursal)
        rcvProductos.layoutManager = LinearLayoutManager(this)

        fun obtenerDatos(): List<dataClassSucursal>
        {
            val objConexion = ClaseConexion().CadenaConexion()
            val statement = objConexion?.createStatement()
            val resultset = statement?.executeQuery("")!!
            val centros = mutableListOf<dataClassSucursal>()
            while (resultset.next())
            {

            }
            return centros
        }
        CoroutineScope(Dispatchers.IO).launch {
            val productosDB = obtenerDatos()
            withContext(Dispatchers.Main){
                val miAdapter = AdaptadorSucursal(productosDB)
                rcvSucursal.adapter = miAdapter
            }
        }
*/
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
}