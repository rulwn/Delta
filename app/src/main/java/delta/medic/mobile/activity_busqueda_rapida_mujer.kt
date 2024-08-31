package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import delta.medic.mobile.ui.SpecialtiesBottomSheetFragment

class activity_busqueda_rapida_mujer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_busqueda_rapida_mujer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val btnChangeGender = findViewById<ImageView>(R.id.btnChangeGender2)
        val bodyImageView = findViewById<ImageView>(R.id.bodyImageView)


        btnRegresar.setOnClickListener {
            finish()
        }
        btnChangeGender.setOnClickListener {
            val intent = Intent(this, activity_busqueda_rapida_hombre::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
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
        val (specialties, bodyArea) = when {
            isHeadArea(x, y) -> listOf("Neurología", "Otorrinolaringología", "Cirugía Plástica") to "Cabeza"
            isChestArea(x, y) -> listOf("Cardiología", "Cirugía Torácica", "Neumología") to "Pecho"
            isAbdomenArea(x, y) -> listOf("Gastroenterología", "Cirugía General", "Ginecología") to "Abdomen"
            isHandArea(x, y) -> listOf("Traumatología", "Cirugía de Mano", "Rehabilitación") to "Manos"
            isLegArea(x, y) -> listOf("Ortopedia", "Cirugía Vascular", "Fisioterapia", "Ginecologia") to "Piernas"
            else -> emptyList<String>() to ""
        }
        if (specialties.isNotEmpty()) {
            val bottomSheet = SpecialtiesBottomSheetFragment.newInstance(specialties, bodyArea)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    private fun isChestArea(x: Float, y: Float): Boolean {
        return x in 400f..800f && y in 400f..900f
    }


    private fun isHeadArea(x: Float, y: Float): Boolean {
        return x in 300f..800f && y in 50f..350f
    }


    private fun isAbdomenArea(x: Float, y: Float): Boolean {
        return x in 400f..800f && y in 850f..1150f
    }


    private fun isHandArea(x: Float, y: Float): Boolean {
        return (x in 50f..300f && y in 700f..1000f) || (x in 800f..1050f && y in 700f..1000f)
    }

    private fun isLegArea(x: Float, y: Float): Boolean {
        return x in 400f..800f && y in 1200f..1600f
    }
}