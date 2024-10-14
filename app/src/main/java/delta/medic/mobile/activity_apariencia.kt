package delta.medic.mobile

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class activity_apariencia : AppCompatActivity() {

     private lateinit var themeRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_apariencia)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        btnRegresar.setOnClickListener {
            finish()
        }

        themeRadioGroup = findViewById(R.id.themeRadioGroup)
        themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.modoClaro -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.modoOscuro -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                R.id.predeterminadoSistema -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            // Enviar el broadcast para informar a MainActivity que debe recrearse
            sendThemeChangeBroadcast()
        }
    }

    private fun sendThemeChangeBroadcast() {
        val intent = Intent("delta.medic.mobile.THEME_CHANGE")
        sendBroadcast(intent)
    }
}