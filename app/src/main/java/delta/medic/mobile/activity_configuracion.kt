package delta.medic.mobile

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import delta.medic.mobile.databinding.ActivityConfiguracionBinding
import delta.medic.mobile.databinding.ActivityCuentaConfiBinding
import android.content.res.Configuration
import androidx.core.content.ContextCompat

class activity_configuracion : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_configuracion)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnTuCuenta = findViewById<ImageView>(R.id.btnTuCuenta)
        val btnPrivacidadySeguridad = findViewById<ImageView>(R.id.btnPrivacidadySeguridad)
        val btnNotificaciones = findViewById<ImageView>(R.id.btnNotificaciones)
        val btnCentroAyuda = findViewById<ImageView>(R.id.btnCentroAyuda)
        val btnApariencia = findViewById<ImageView>(R.id.btnApariencia)
        val btnCerrarSesion = findViewById<ImageView>(R.id.btnCerrarSesion)
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                btnTuCuenta.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnPrivacidadySeguridad.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnNotificaciones.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnCentroAyuda.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnApariencia.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnCerrarSesion.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
            } // Night mode is not active, we're using the light theme.
            Configuration.UI_MODE_NIGHT_YES -> {
                btnTuCuenta.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnPrivacidadySeguridad.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnNotificaciones.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnCentroAyuda.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnApariencia.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnCerrarSesion.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
            } // Night mode is active, we're using dark theme.
        }
        btnRegresar.setOnClickListener {
            finish()
        }

        /*val textView10: TextView = findViewById(R.id.lbPersonalizarPerfil)
        val text = "Configuración"

        val spannableString = SpannableString(text)
        spannableString.setSpan(UnderlineSpan(), 0, text.length, 0)

        textView10.text = spannableString*/


        btnTuCuenta.setOnClickListener {
            val intent = Intent(this, activity_cuenta_confi::class.java)
            startActivity(intent)
        }

        btnPrivacidadySeguridad.setOnClickListener {
            val intent = Intent(this, activity_privacidadyseguridad::class.java)
            startActivity(intent)
        }

        btnNotificaciones.setOnClickListener {
            val intent = Intent(this, activity_notificaciones_confi::class.java)
            startActivity(intent)
        }

        btnCentroAyuda.setOnClickListener {
            val intent = Intent(this, activity_centroadeyuda::class.java)
            startActivity(intent)
        }

        btnApariencia.setOnClickListener {
            val intent = Intent(this, activity_apariencia::class.java)
            startActivity(intent)
        }
        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
            finish()
        }
    }
}