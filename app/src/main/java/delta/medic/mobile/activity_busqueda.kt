package delta.medic.mobile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast


class activity_busqueda : AppCompatActivity() {

    private lateinit var txtSearch: EditText
    private lateinit var imgCerrar: ImageView
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

        txtSearch = findViewById(R.id.txtSearch)
        imgCerrar = findViewById(R.id.imgCerrar)

        txtSearch.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (txtSearch.right - txtSearch.compoundDrawables[2].bounds.width())) {
                    val query = txtSearch.text.toString()
                    if (query.isNotEmpty()) {
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
        Toast.makeText(this, "Buscando: $query", Toast.LENGTH_SHORT).show()

    }

}