package delta.medic.mobile

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import delta.medic.mobile.databinding.ActivityConfiguracionBinding
import delta.medic.mobile.databinding.ActivityCuentaConfiBinding

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
        val btnCuenta = findViewById<LinearLayout>(R.id.LinearCuenta)
        val btnPrivacidad = findViewById<LinearLayout>(R.id.LinearPrivacidad)
        val btnNotificaciones = findViewById<LinearLayout>(R.id.LinearNotificaciones)
        val btnAyuda = findViewById<LinearLayout>(R.id.LinearAyuda)
        val btnApariencia = findViewById<LinearLayout>(R.id.LinearApariencia)
        val btnCerrar = findViewById<LinearLayout>(R.id.LinearCerrar)
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        btnRegresar.setOnClickListener {
            finish()
        }

        /*val textView10: TextView = findViewById(R.id.lbPersonalizarPerfil)
        val text = "Configuración"

        val spannableString = SpannableString(text)
        spannableString.setSpan(UnderlineSpan(), 0, text.length, 0)

        textView10.text = spannableString*/


        btnCuenta.setOnClickListener {
            val intent = Intent(this, activity_cuenta_confi::class.java)
            startActivity(intent)
        }

        btnPrivacidad.setOnClickListener {
            val intent = Intent(this, activity_privacidadyseguridad::class.java)
            startActivity(intent)
        }

        btnNotificaciones.setOnClickListener {
            val intent = Intent(this, activity_notificaciones_confi::class.java)
            startActivity(intent)
        }

        btnAyuda.setOnClickListener {
            val intent = Intent(this, activity_centroadeyuda::class.java)
            startActivity(intent)
        }

        btnApariencia.setOnClickListener {
            val intent = Intent(this, activity_apariencia::class.java)
            startActivity(intent)
        }
        btnCerrar.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
            finish()
        }
    }
}