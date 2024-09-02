package delta.medic.mobile

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_misrecetas : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_misrecetas)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtRecetas = findViewById<TextView>(R.id.txtRecetas)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

/*
val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
when (currentNightMode) {
    Configuration.UI_MODE_NIGHT_NO -> {
        txtRecetas.setTextColor(ContextCompat.getColor(this, R.color.black))
        btnRegresar.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
    } // Night mode is not active, we're using the light theme.
    Configuration.UI_MODE_NIGHT_YES -> {
        txtRecetas.setTextColor(ContextCompat.getColor(this, R.color.white))
        btnRegresar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
    } // Night mode is active, we're using dark theme.
}

 */
}
}