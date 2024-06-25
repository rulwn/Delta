package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*val textView10: TextView = findViewById(R.id.lbPersonalizarPerfil)
        val text = "Configuración"

        val spannableString = SpannableString(text)
        spannableString.setSpan(UnderlineSpan(), 0, text.length, 0)

        textView10.text = spannableString*/


        binding.imgTuCuenta.setOnClickListener {
            val intent = Intent(this, activity_cuenta_confi::class.java)
            startActivity(intent)
        }

        binding.imgPrivacidadySeguridad.setOnClickListener {
                val intent = Intent(this, activity_privacidadyseguridad::class.java)
                startActivity(intent)
            }

        binding.imgNotificaciones.setOnClickListener {
            val intent = Intent(this, activity_notificaciones_confi::class.java)
            startActivity(intent)
        }

        binding.imgCentroAyuda.setOnClickListener {
            val intent = Intent(this, activity_centroadeyuda::class.java)
            startActivity(intent)
        }

        binding.imgApariencia.setOnClickListener {
            val intent = Intent(this, activity_apariencia::class.java)
            startActivity(intent)
        }
    }
}