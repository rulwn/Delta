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

        btnRegresar.setOnClickListener {
            finish()
        }

        btnChangeGender.setOnClickListener {
            val intent = Intent(this, activity_busqueda_rapida_mujer::class.java)
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
        btnAbdomen.setOnClickListener { showSpecialties("Abdomen", listOf("Gastroenterología", "Cirugía General", "Urología")) }
        btnLeftHand.setOnClickListener { showSpecialties("Brazo Izquierdo", listOf("Traumatología", "Cirugía de Mano", "Rehabilitación")) }
        btnRightHand.setOnClickListener { showSpecialties("Brazo Derecho", listOf("Traumatología", "Cirugía de Mano", "Rehabilitación")) }
        btnLeftLeg.setOnClickListener { showSpecialties("Pierna Izquierda", listOf("Ortopedia", "Cirugía Vascular", "Fisioterapia")) }
        btnRightLeg.setOnClickListener { showSpecialties("Pierna Derecha", listOf("Ortopedia", "Cirugía Vascular", "Fisioterapia")) }
    }

    private fun showSpecialties(bodyArea: String, specialties: List<String>) {
        val bottomSheet = SpecialtiesBottomSheetFragment.newInstance(specialties, bodyArea)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}