package delta.medic.mobile

import RecycleViewHelper.AdaptadorRecientes
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
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
    private lateinit var rvRecentSearches: RecyclerView
    private val recentSearches = mutableListOf<String>()
    private lateinit var adapter: AdaptadorRecientes

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_busqueda)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtSearch = findViewById(R.id.txtSearch)
        val imgCerrar = findViewById<ImageView>(R.id.imgCerrar)
        rvRecentSearches = findViewById(R.id.rvRecentSearches)

        adapter = AdaptadorRecientes(recentSearches)
        rvRecentSearches.layoutManager = LinearLayoutManager(this)
        rvRecentSearches.adapter = adapter

        // Manejar el botón "chequesito" (Enter) del teclado en móvil
        txtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                executeSearch()
                true // Indicamos que hemos manejado el evento
            } else {
                false
            }
        }

        // Manejar la tecla Enter en computadoras
        txtSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                executeSearch()
                true
            } else {
                false
            }
        }

        txtSearch.setOnTouchListener { _, event ->
            try {
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (txtSearch.right - txtSearch.compoundDrawables[2].bounds.width())) {
                        executeSearch()
                        return@setOnTouchListener true
                    }
                }
            } catch (e: Exception) {
                println("Este es el error ${e.message}")
            }
            false
        }

        imgCerrar.setOnClickListener {
            txtSearch.text.clear()
            finish()
        }

        intent.getStringExtra("query")?.let {
            txtSearch.setText(it)
            performSearch(it)
        }
    }

    // Función común para ejecutar la búsqueda
    private fun executeSearch() {
        val query = txtSearch.text.toString()
        if (query.isNotEmpty()) {
            recentSearches.add(query)
            adapter.notifyDataSetChanged()
            performSearch(query)
            getSearchEditText()
        } else {
            Toast.makeText(this, "Ingrese un término de búsqueda", Toast.LENGTH_SHORT).show()
        }
    }

    fun getSearchEditText(): EditText {
        return txtSearch
    }

    private fun performSearch(query: String) {
        try {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.main, fragment_Resultados().apply {
                    arguments = Bundle().apply {
                        putString("query", query)
                    }
                })
                addToBackStack(null)
                commit()
            }
        } catch (e: Exception) {
            println("Este es el error ${e.message}")
        }
    }
}
