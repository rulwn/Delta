package delta.medic.mobile

import android.content.res.Configuration
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_item_especialidad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item_especialidad)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val specialtyTextView = findViewById<TextView>(R.id.specialtyTextView)

/*
val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
when (currentNightMode) {
    Configuration.UI_MODE_NIGHT_NO -> {
        specialtyTextView.setTextColor(ContextCompat.getColor(this, R.color.Azul3))
    } // Night mode is not active, we're using the light theme.
    Configuration.UI_MODE_NIGHT_YES -> {
        specialtyTextView.setTextColor(ContextCompat.getColor(this, R.color.Azul3))
    } // Night mode is active, we're using dark theme.
}

 */
}
}