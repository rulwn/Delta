package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
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

            //lottieView.setAnimation("animation.json")
            //lottieView.playAnimation()
            val sharedPreferences = getSharedPreferences("PrimerUso", MODE_PRIVATE)
            val sharedPreferencesStatus = getSharedPreferences("Sesion", MODE_PRIVATE)

            val sesionIniciada = sharedPreferencesStatus.getBoolean("sesionIniciada", false)
            val primerUso = sharedPreferences.getBoolean("P_Ejecutado", false)
            val email = sharedPreferences.getString("email", null)

            if(sesionIniciada && email != null){
                delay(4000)
                userEmail = email
                startActivity(Intent(this@splash_screen, MainActivity::class.java))
                finish()
            }
            else if(!primerUso){
                delay(4000)
                startActivity(Intent(this@splash_screen, activity_bienvenida::class.java))
                finish()
            }
            else{
                delay(4000)
                startActivity(Intent(this@splash_screen, activity_login::class.java))
                finish()
            }
        }
    }
}