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
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable

class activity_configuracion : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_configuracion)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
        val btnIdiomas = findViewById<ImageView>(R.id.btnIdiomas)
        val consCuenta = findViewById<ConstraintLayout>(R.id.consCuenta)
        val consPriv = findViewById<ConstraintLayout>(R.id.consPriv)
        val consNoti = findViewById<ConstraintLayout>(R.id.consNoti)
        val consAyuda = findViewById<ConstraintLayout>(R.id.consAyuda)
        val consApariencia = findViewById<ConstraintLayout>(R.id.consApariencia)
        val consCerrarSesion = findViewById<ConstraintLayout>(R.id.consCerarr)
        val consIdiomas = findViewById<ConstraintLayout>(R.id.consIdiomas)
        val txtidiomas = findViewById<TextView>(R.id.txtIdiomas)
        val imgvIdiomas = findViewById<ImageView>(R.id.imgvIdiomas)

        // Configuración para que el botón imvIdiomas abra la sección de idiomas
        consIdiomas.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                btnIdiomas.setColorFilter(ContextCompat.getColor(this, R.color.black))
                imgvIdiomas.setColorFilter(ContextCompat.getColor(this, R.color.black))
                txtidiomas.setTextColor(ContextCompat.getColor(this, R.color.black))
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
            }
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
                btnIdiomas.setColorFilter(ContextCompat.getColor(this, R.color.white))
                imgvIdiomas.setColorFilter(ContextCompat.getColor(this, R.color.white))
                txtidiomas.setTextColor(ContextCompat.getColor(this, R.color.white))

            }
        }

        btnRegresar.setOnClickListener {
            finish()
        }

        // Configuración del cambio de apariencia
        consApariencia.setOnClickListener {
            showThemeSelectionDialog() // Mostrar un diálogo para que el usuario elija el tema
        }

        consCuenta.setOnClickListener {
            val intent = Intent(this, activity_cuenta_confi::class.java)
            startActivity(intent)
        }

        consPriv.setOnClickListener {
            val intent = Intent(this, activity_privacidadyseguridad::class.java)
            startActivity(intent)
        }

        consNoti.setOnClickListener {
            val intent = Intent(this, activity_notificaciones_confi::class.java)
            startActivity(intent)
        }

        consAyuda.setOnClickListener {
            val intent = Intent(this, activity_centroadeyuda::class.java)
            startActivity(intent)
        }

        consCerrarSesion.setOnClickListener {
            val sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("email", null)
            editor.putBoolean("IsLogedIn", false)
            editor.apply()

            val intent = Intent(this, splash_screen::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Función para cambiar el tema entre modo claro y oscuro
    fun switchTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        recreate() // Recargar la actividad para aplicar los cambios
    }

    // Diálogo para que el usuario seleccione el tema (claro u oscuro)
    private fun showThemeSelectionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccionar Apariencia")
            .setMessage("Elige el modo de la aplicación")
            .setPositiveButton("Modo Oscuro") { _, _ ->
                switchTheme(true) // Cambiar a modo oscuro
            }
            .setNegativeButton("Modo Claro") { _, _ ->
                switchTheme(false) // Cambiar a modo claro
            }
        val dialog = builder.create()
        dialog.show()
    }

}
