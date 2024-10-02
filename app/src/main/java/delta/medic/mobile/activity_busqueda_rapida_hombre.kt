package delta.medic.mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import delta.medic.mobile.ui.SpecialtiesBottomSheetFragment

class activity_busqueda_rapida_hombre : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
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
        val txtBusquedaRHombre = findViewById<TextView>(R.id.txtBusquedaRHombre)
        val bodyImageView = findViewById<ImageView>(R.id.bodyImageView)
        val txtDolor = findViewById<TextView>(R.id.txtDolor)

        /*
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnChangeGender.setColorFilter(ContextCompat.getColor(this, R.color.GrisAzulito))
                txtBusquedaRHombre.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtDolor.setTextColor(ContextCompat.getColor(this, R.color.GrisAzulito))
                bodyImageView.setColorFilter(ContextCompat.getColor(this, R.color.GrisAzulito))
            } // Night mode is not active, we're using the light theme.
            Configuration.UI_MODE_NIGHT_YES -> {
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnChangeGender.setColorFilter(ContextCompat.getColor(this, R.color.white))
                txtBusquedaRHombre.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtDolor.setTextColor(ContextCompat.getColor(this, R.color.white))
                bodyImageView.setColorFilter(ContextCompat.getColor(this, R.color.white))
            } // Night mode is active, we're using dark theme.
        }

         */

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

        btnHead.setOnClickListener { showSpecialties("Cabeza", listOf("Neurología", "Otorrinolaringología", "Dermatologia")) }
        btnChest.setOnClickListener { showSpecialties("Pecho", listOf("Cardiología", "Mastologo", "Neumología", "Cardiología")) }
        btnAbdomen.setOnClickListener { showSpecialties("Abdomen", listOf("Gastroenterología", "Nefrólogo ", "Urologia")) }
        btnLeftHand.setOnClickListener { showSpecialties("Brazo Izquierdo", listOf("Traumatología", "Reumatologia ", "Rehabilitación")) }
        btnRightHand.setOnClickListener { showSpecialties("Brazo Derecho", listOf("Traumatología", "Reumatologia ", "Rehabilitación")) }
        btnLeftLeg.setOnClickListener { showSpecialties("Piernas", listOf("Ortopedia", "Podologia ", "Fisioterapia")) }
        btnRightLeg.setOnClickListener { showSpecialties("Piernas", listOf("Ortopedia", "Podologia", "Fisioterapia")) }
    }

    private fun showSpecialties(bodyArea: String, specialties: List<String>) {
        val bottomSheet = SpecialtiesBottomSheetFragment.newInstance(specialties, bodyArea)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }
}