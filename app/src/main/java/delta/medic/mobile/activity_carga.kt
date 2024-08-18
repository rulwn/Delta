package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class activity_carga : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carga)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        GlobalScope.launch(Dispatchers.Main) {

            val lottieView = findViewById<LottieAnimationView>(R.id.lottie_view)


            val userPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
            val isLogedIn = userPreferences.getBoolean("IsLogedIn", false)
            val isWelcomed = userPreferences.getBoolean("IsWelcomed", false)
            val email = userPreferences.getString("email", null)



            Log.d("Preferences", "IsLogedIn: $isLogedIn")
            Log.d("Preferences", "IsWelcomed: $isWelcomed")
            Log.d("Preferences", "Email: $email")

            if (isLogedIn == false || email == null) {
                val intent = Intent(this@activity_carga, activity_login::class.java)
                startActivity(intent)
            }
            else if (isWelcomed == false){
                val intent = Intent(this@activity_carga, activity_bienvenida::class.java)
                startActivity(intent)
            } else {
                activity_login.userEmail = email
                val intent = Intent(this@activity_carga, MainActivity::class.java)
                startActivity(intent)
            }

            finish()
        }

    }
}