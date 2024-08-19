package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        GlobalScope.launch(Dispatchers.Main) {
            val lottieView = findViewById<LottieAnimationView>(R.id.lottie_view)
            lottieView.setImageAssetsFolder("images/")

            val userPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
            val isLogedIn = userPreferences.getBoolean("IsLogedIn", false)
            val isWelcomed = userPreferences.getBoolean("IsWelcomed", false)
            val email = userPreferences.getString("email", null)

            Log.d("Preferences", "IsLogedIn: $isLogedIn")
            Log.d("Preferences", "IsWelcomed: $isWelcomed")
            Log.d("Preferences", "Email: $email")

            delay(3000)
            if (!isWelcomed) {
                val intent = Intent(this@splash_screen, activity_bienvenida::class.java)
                startActivity(intent)
            }
            else if (!isLogedIn || email.isNullOrEmpty()) {
                val intent = Intent(this@splash_screen, activity_login::class.java)
                startActivity(intent)
            }
            else {
                userEmail = email
                val intent = Intent(this@splash_screen, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}