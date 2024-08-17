package delta.medic.mobile

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import delta.medic.mobile.databinding.ActivityConfiguracionBinding
import android.content.res.Configuration
import android.widget.LinearLayout
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

        val linearCuenta = findViewById<LinearLayout>(R.id.LinearCuenta)
        val linearPrivacidad = findViewById<LinearLayout>(R.id.LinearPrivacidad)
        val linearNotificaciones = findViewById<LinearLayout>(R.id.LinearNotificaciones)
        val linearCentroAyuda = findViewById<LinearLayout>(R.id.LinearAyuda)
        val linearApariencia = findViewById<LinearLayout>(R.id.LinearApariencia)
        val linearCerrarSesion = findViewById<LinearLayout>(R.id.LinearCerrar)
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val txtConfi = findViewById<TextView>(R.id.txtConfiguracion)
        val txtCuenta = findViewById<TextView>(R.id.txtCuentaPerfil)
        val txtPriv = findViewById<TextView>(R.id.txtPrivacidadySeguridad)
        val txtNoti = findViewById<TextView>(R.id.txtNotiiii)
        val txtCentroAyuda = findViewById<TextView>(R.id.txtCentroAyuda1)
        val txtApariencia = findViewById<TextView>(R.id.txtApariencia1)
        val txtCerrarSesion = findViewById<TextView>(R.id.txtCerrarSesion)
        val btnTuCuenta = findViewById<ImageView>(R.id.btnTuCuenta)
        val btnPrivacidadySeguridad = findViewById<ImageView>(R.id.btnPrivacidadySeguridad)
        val btnNotificaciones = findViewById<ImageView>(R.id.btnNotificaciones)
        val btnCentroAyuda = findViewById<ImageView>(R.id.btnCentroAyuda)
        val btnApariencia = findViewById<ImageView>(R.id.btnApariencia)
        val imgvFotoPerfil = findViewById<ImageView>(R.id.imgvPerfil)
        val imgvPriv = findViewById<ImageView>(R.id.imgvPriv)
        val imgvNoti = findViewById<ImageView>(R.id.imgvNoti)
        val imgvAyuda = findViewById<ImageView>(R.id.imgvAyuda)
        val imgvApariencia = findViewById<ImageView>(R.id.imgvApariencia)
        val imgvCerrarSesion = findViewById<ImageView>(R.id.imgCerrarSesion)
        val btnCerrarSesion = findViewById<ImageView>(R.id.btnCerrarSesion)





        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                txtCuenta.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtPriv.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtNoti.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtCentroAyuda.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtApariencia.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtCerrarSesion.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtConfi.setTextColor(ContextCompat.getColor(this, R.color.black))
                btnTuCuenta.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnPrivacidadySeguridad.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnNotificaciones.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnCentroAyuda.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnApariencia.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnCerrarSesion.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
                imgvFotoPerfil.setColorFilter(ContextCompat.getColor(this, R.color.black))
                imgvPriv.setColorFilter(ContextCompat.getColor(this, R.color.black))
                imgvNoti.setColorFilter(ContextCompat.getColor(this, R.color.black))
                imgvAyuda.setColorFilter(ContextCompat.getColor(this, R.color.black))
                imgvApariencia.setColorFilter(ContextCompat.getColor(this, R.color.black))
                imgvCerrarSesion.setColorFilter(ContextCompat.getColor(this, R.color.black))
            } // Night mode is not active, we're using the light theme.
            Configuration.UI_MODE_NIGHT_YES -> {
                txtCuenta.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtPriv.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtNoti.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtCentroAyuda.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtApariencia.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtConfi.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtCerrarSesion.setTextColor(ContextCompat.getColor(this, R.color.white))
                btnTuCuenta.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnPrivacidadySeguridad.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnNotificaciones.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnCentroAyuda.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnApariencia.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnCerrarSesion.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                imgvFotoPerfil.setColorFilter(ContextCompat.getColor(this, R.color.white))
                imgvPriv.setColorFilter(ContextCompat.getColor(this, R.color.white))
                imgvNoti.setColorFilter(ContextCompat.getColor(this, R.color.white))
                imgvAyuda.setColorFilter(ContextCompat.getColor(this, R.color.white))
                imgvApariencia.setColorFilter(ContextCompat.getColor(this, R.color.white))
                imgvCerrarSesion.setColorFilter(ContextCompat.getColor(this, R.color.white))

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


        linearCuenta.setOnClickListener {
            val intent = Intent(this, activity_cuenta_confi::class.java)
            startActivity(intent)
        }

        linearPrivacidad.setOnClickListener {
            val intent = Intent(this, activity_privacidadyseguridad::class.java)
            startActivity(intent)
        }

        linearNotificaciones.setOnClickListener {
            val intent = Intent(this, activity_notificaciones_confi::class.java)
            startActivity(intent)
        }

        linearCentroAyuda.setOnClickListener {
            val intent = Intent(this, activity_centroadeyuda::class.java)
            startActivity(intent)
        }

        linearApariencia.setOnClickListener {
            val intent = Intent(this, activity_apariencia::class.java)
            startActivity(intent)
        }
        linearCerrarSesion.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
            finish()
            val mainActivity = MainActivity()
            mainActivity.signOutAndStartSignInActivity()
        }
    }
}