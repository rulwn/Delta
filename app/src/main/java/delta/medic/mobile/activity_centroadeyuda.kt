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
import android.content.res.Configuration
import android.widget.Button
import androidx.core.content.ContextCompat

class activity_centroadeyuda : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_centroadeyuda)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val txtCentroAyuda = findViewById<TextView>(R.id.txtCentroAyuda)
        val txtPodemosAyudarte = findViewById<TextView>(R.id.txtPodemosAudarte)
        val txtContactanos = findViewById<TextView>(R.id.txtContactanos)
        val txtEmailCentroAyuda = findViewById<TextView>(R.id.txtEmailCentroAyuda)
        val txtEscribaComentario = findViewById<TextView>(R.id.txtEscribaComentarioCA)
        val txtPreguntasF = findViewById<TextView>(R.id.txtPreguntasF)
        val txtPregunta1 = findViewById<TextView>(R.id.txtPregunta1)
        val txtPregunta2 = findViewById<TextView>(R.id.txtPregunta2)
        val txtPregunta3 = findViewById<TextView>(R.id.txtPregunta3)
        val btnEnviarCorreo = findViewById<Button>(R.id.btnEnviarCorreo)


        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                txtCentroAyuda.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtPodemosAyudarte.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtContactanos.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtEmailCentroAyuda.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtEscribaComentario.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtPreguntasF.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtPregunta1.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtPregunta2.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtPregunta3.setTextColor(ContextCompat.getColor(this, R.color.black))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
            } // Night mode is not active, we're using the light theme.
            Configuration.UI_MODE_NIGHT_YES -> {
                txtCentroAyuda.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtPodemosAyudarte.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtContactanos.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtEmailCentroAyuda.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtEscribaComentario.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtPreguntasF.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtPregunta1.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtPregunta2.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtPregunta3.setTextColor(ContextCompat.getColor(this, R.color.white))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
            } // Night mode is active, we're using dark theme.
        }

        btnRegresar.setOnClickListener {
            finish()
        }

        btnEnviarCorreo.setOnClickListener {
            val email = txtEmailCentroAyuda.text.toString()
            val comentario = txtEscribaComentario.text.toString()

            if (email.isNotEmpty() && comentario.isNotEmpty()) {
                enviarCorreo(email, comentario)
            }
        }
    }

    private fun enviarCorreo(email: String, comentario: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("delta.medic.help@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Comentario desde la App")
            putExtra(Intent.EXTRA_TEXT, "Correo del usuario: $email\n\nComentario:\n$comentario")
        }

        try {
            startActivity(Intent.createChooser(intent, "Enviar correo..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            ex.printStackTrace()
        }
    }
}