package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassCentro
import RecycleViewHelper.AdaptadorCentro
import RecycleViewHelper.AdaptadorRecientes
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class activity_busqueda : AppCompatActivity() {

    private lateinit var txtSearch: EditText
    private lateinit var imgCerrar: ImageView
    private lateinit var rvRecentSearches: RecyclerView
    private val recentSearches = mutableListOf<String>()
    private lateinit var adapter: AdaptadorRecientes

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_busqueda)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtSearch = findViewById(R.id.txtSearch)
        imgCerrar = findViewById(R.id.imgCerrar)
        rvRecentSearches = findViewById(R.id.rvRecentSearches)

        adapter = AdaptadorRecientes(recentSearches)
        rvRecentSearches.layoutManager = LinearLayoutManager(this)
        rvRecentSearches.adapter = adapter

        suspend fun obtenerDatos(): List<dataClassCentro>
        {
            return withContext(Dispatchers.IO) {
                val objConexion = ClaseConexion()?.cadenaConexion()
                val statement = objConexion?.createStatement()
                val resultset = statement?.executeQuery(
                    """
        SELECT 
            d.ID_Doctor,
            e.nombreEspecialidad,
            s.nombreSucursal,
            s.direccionSucur
        FROM
            tbCentrosMedicos cm
        INNER JOIN
            tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
        INNER JOIN
            tbSucursales s ON cm.ID_Sucursal = s.ID_Sucursal
        INNER JOIN
            tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
        """
                )!!
                val centroMedico = mutableListOf<dataClassCentro>()
                while (resultset.next()) {
                    val ID_Doctor = resultset.getInt("ID_Doctor")
                    val nombreEspecialidad = resultset.getString("nombreEspecialidad")
                    val direccionSucur = resultset.getString("direccionSucur")
                    val centro = dataClassCentro(ID_Doctor, nombreEspecialidad, direccionSucur)
                    centroMedico.add(centro)
                }
                centroMedico
            }

        }
        CoroutineScope(Dispatchers.IO).launch {
            val centrosDB = obtenerDatos()
            withContext(Dispatchers.Main) {
                val miAdapter = AdaptadorCentro(centrosDB)
                rvRecentSearches.adapter = miAdapter
            }
        }


        txtSearch.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (txtSearch.right - txtSearch.compoundDrawables[2].bounds.width())) {
                    val query = txtSearch.text.toString()
                    if (query.isNotEmpty()) {
                        recentSearches.add(query)
                        adapter.notifyDataSetChanged()
                        performSearch(query)
                    } else {
                        Toast.makeText(this, "Ingrese un término de búsqueda", Toast.LENGTH_SHORT).show()
                    }
                    return@setOnTouchListener true
                }
            }
            false
        }

        imgCerrar.setOnClickListener {
            txtSearch.text.clear()
        }
    }

    private fun performSearch(query: String) {
        val fragment = fragment_Resultados().apply {
            arguments = Bundle().apply {
                putString("query", query)
            }
        }
        supportFragmentManager.commit {
            replace(R.id.main, fragment)
            addToBackStack(null)
        }
    }
}