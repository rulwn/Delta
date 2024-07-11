package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import delta.medic.mobile.ui.SpecialtiesBottomSheetFragment

class activity_busqueda_rapida_hombre : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_busqueda_rapida_hombre)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val btnChangeGender = findViewById<ImageView>(R.id.btnChangeGender1)
        val bodyImageView = findViewById<ImageView>(R.id.bodyImageView)
        btnRegresar.setOnClickListener {
            finish()
        }
        btnChangeGender.setOnClickListener {
            val intent = Intent(this, activity_busqueda_rapida_mujer::class.java)
            startActivity(intent)
        }
        bodyImageView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.x
                val y = event.y
                handleBodyPartClick(x, y)
            }
            true
        }
    }
    private fun handleBodyPartClick(x: Float, y: Float) {
        val specialties = when {
            isChestArea(x, y) -> listOf("Cardiología", "Torax")
            isHeadArea(x, y) -> listOf("Neurología", "Otorrinolaringología")
            // Agrega más áreas del cuerpo según sea necesario
            else -> emptyList()
        }
        if (specialties.isNotEmpty()) {
            val bottomSheet = SpecialtiesBottomSheetFragment.newInstance(specialties)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }
    private fun isChestArea(x: Float, y: Float): Boolean {
        //nose m olvide q son coordenadas para el pecho
        return x in 100f..200f && y in 300f..400f
    }

    private fun isHeadArea(x: Float, y: Float): Boolean {
        //y estas para la cabeza we
        return x in 100f..200f && y in 100f..200f
    }
}