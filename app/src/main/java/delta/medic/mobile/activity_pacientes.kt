package delta.medic.mobile

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class activity_pacientes : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pacientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val txtPacientes = findViewById<TextView>(R.id.txtPacientes)
        val rcvPacientes = findViewById<RecyclerView>(R.id.rcvPacientes)
        val fabCrearPacientes = findViewById<ExtendedFloatingActionButton>(R.id.fabCrearPacientes)
        val txtFrase = findViewById<TextView>(R.id.txtFrase)

/*
val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
when (currentNightMode) {
    Configuration.UI_MODE_NIGHT_NO -> {
        btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
        txtPacientes.setTextColor(ContextCompat.getColor(this, R.color.black))
        fabCrearPacientes.setBackgroundColor(ContextCompat.getColor(this, R.color.Azul1))
        txtFrase.setTextColor(ContextCompat.getColor(this, R.color.GrisHumo))
        rcvPacientes.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
    } // Night mode is not active, we're using the light theme.
    Configuration.UI_MODE_NIGHT_YES -> {
        txtPacientes.setTextColor(ContextCompat.getColor(this, R.color.white))
        btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
        txtPacientes.setTextColor(ContextCompat.getColor(this, R.color.white))
        fabCrearPacientes.setBackgroundColor(ContextCompat.getColor(this, R.color.Turquesa2))
        rcvPacientes.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
    } // Night mode is active, we're using dark theme.
}

 */

btnRegresar.setOnClickListener {
    finish()
}
}
}