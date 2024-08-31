package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
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

        btnRegresar.setOnClickListener {
            finish()
        }

        btnChangeGender.setOnClickListener {
            val intent = Intent(this, activity_busqueda_rapida_hombre::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }


        setupBodyAreaButtons()
    }

    private fun setupBodyAreaButtons() {
        val btnHead = findViewById<Button>(R.id.btnHead)
        val btnChest = findViewById<Button>(R.id.btnChest)
        val btnAbdomen = findViewById<Button>(R.id.btnAbdomen)
        val btnLeftHand = findViewById<Button>(R.id.btnLeftHand)
        val btnRightHand = findViewById<Button>(R.id.btnRightHand)
        val btnLeftLeg = findViewById<Button>(R.id.btnLeftLeg)
        val btnRightLeg = findViewById<Button>(R.id.btnRightLeg)

        btnHead.setOnClickListener { showSpecialties("Cabeza", listOf("Neurología", "Otorrinolaringología", "Cirugía Plástica")) }
        btnChest.setOnClickListener { showSpecialties("Pecho", listOf("Cardiología", "Cirugía Torácica", "Neumología")) }
        btnAbdomen.setOnClickListener { showSpecialties("Abdomen", listOf("Gastroenterología", "Cirugía General", "Ginecología")) }
        btnLeftHand.setOnClickListener { showSpecialties("Brazo Izquierdo", listOf("Traumatología", "Cirugía de Mano", "Rehabilitación")) }
        btnRightHand.setOnClickListener { showSpecialties("Brazo Derecho", listOf("Traumatología", "Cirugía de Mano", "Rehabilitación")) }
        btnLeftLeg.setOnClickListener { showSpecialties("Piernas", listOf("Ortopedia", "Cirugía Vascular", "Fisioterapia")) }
        btnRightLeg.setOnClickListener { showSpecialties("Piernas", listOf("Ortopedia", "Cirugía Vascular", "Fisioterapia")) }
    }

    private fun showSpecialties(bodyArea: String, specialties: List<String>) {
        val bottomSheet = SpecialtiesBottomSheetFragment.newInstance(specialties, bodyArea)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}