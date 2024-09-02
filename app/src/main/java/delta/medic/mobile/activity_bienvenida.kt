package delta.medic.mobile

import android.content.Intent
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
import delta.medic.mobile.activity_login.UserData.userEmail

class activity_bienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bienvenida)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val txtOnBoard = findViewById<TextView>(R.id.txtOnBoard)
        val txtOnBoardTitle = findViewById<TextView>(R.id.txtOnBoardTitle)

        btnContinuar.setOnClickListener {

            val intent = Intent(this, activity_register1::class.java)
            startActivity(intent)
            finish()
        }
        /*
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                txtOnBoard.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtOnBoardTitle.setTextColor(ContextCompat.getColor(this, R.color.black))
            } // Night mode is not active, we're using the light theme.
            Configuration.UI_MODE_NIGHT_YES -> {
                txtOnBoard.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtOnBoardTitle.setTextColor(ContextCompat.getColor(this, R.color.white))
            } // Night mode is active, we're using dark theme.
        }

         */
    }
}