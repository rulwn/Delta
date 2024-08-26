package delta.medic.mobile

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_privacidadyseguridad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_privacidadyseguridad)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val txtPrivacidad = findViewById<TextView>(R.id.txtPrivacidad)
        val txtTerminos1 = findViewById<TextView>(R.id.txtTerminos1)
        val txtTerminos2 = findViewById<TextView>(R.id.txtTerminos2)
        val txtTerminos3 = findViewById<TextView>(R.id.txtTerminos3)
        val txtTerminos4 = findViewById<TextView>(R.id.txtTerminos4)
        val txtTerminos5 = findViewById<TextView>(R.id.txtTerminos5)
        val txtTerminos6 = findViewById<TextView>(R.id.txtTerminos6)
        val txtTerminos7 = findViewById<TextView>(R.id.txtTerminos7)
        val txtTerminos8 = findViewById<TextView>(R.id.txtTerminos8)
        val txtTerminos9 = findViewById<TextView>(R.id.txtTerminos9)
        val txtTerminos10 = findViewById<TextView>(R.id.txtTerminos10)
        val txtTerminos11 = findViewById<TextView>(R.id.txtTerminos11)
        val txtTerminos12 = findViewById<TextView>(R.id.txtTerminos12)
        val txtTerminos13 = findViewById<TextView>(R.id.txtTerminos13)
        val txtTerminos14 = findViewById<TextView>(R.id.txtTerminos14)
        val txtTerminos15 = findViewById<TextView>(R.id.txtTerminos15)
        val txtTerminos16 = findViewById<TextView>(R.id.txtTerminos16)
        val txtTerminos17 = findViewById<TextView>(R.id.txtTerminos17)
        val txtTerminos18 = findViewById<TextView>(R.id.txtTerminos18)
        val txtTerminos19 = findViewById<TextView>(R.id.txtTerminos19)
        val txtTerminos20 = findViewById<TextView>(R.id.txtTerminos20)
        val txtTerminos21 = findViewById<TextView>(R.id.txtTerminos21)
        val txtTerminos22 = findViewById<TextView>(R.id.txtTerminos22)
        val txtTerminos23 = findViewById<TextView>(R.id.txtTerminos23)
        val txtTerminos24 = findViewById<TextView>(R.id.txtTerminos24)
        val txtTerminos25 = findViewById<TextView>(R.id.txtTerminos25)


        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                txtPrivacidad.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos1.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos2.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos3.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos4.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos5.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos6.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos7.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos8.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos9.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos10.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos11.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos12.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos13.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos14.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos15.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos16.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos17.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos18.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos19.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos20.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos21.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos22.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos23.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos24.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtTerminos25.setTextColor(ContextCompat.getColor(this, R.color.black))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
            } // Night mode is not active, we're using the light theme.
            Configuration.UI_MODE_NIGHT_YES -> {
                txtPrivacidad.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos1.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos2.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos3.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos4.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos5.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos6.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos7.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos8.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos9.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos10.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos11.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos12.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos13.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos14.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos15.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos16.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos17.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos18.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos19.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos20.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos21.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos22.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos23.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos24.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtTerminos25.setTextColor(ContextCompat.getColor(this, R.color.white))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
            } // Night mode is active, we're using dark theme.
        }

        btnRegresar.setOnClickListener {
            finish()
        }

    }
}