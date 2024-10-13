package delta.medic.mobile
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import delta.medic.mobile.databinding.ActivityMainBinding
import android.content.Context
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import delta.medic.mobile.activity_login.UserData.userEmail
import delta.medic.mobile.ui.dashboard.DashboardFragment
import androidx.core.app.NotificationManagerCompat
import delta.medic.mobile.MainActivity
class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        val userPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        userEmail = userPreferences.getString("email", null).toString()

        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val user = Firebase.auth.currentUser

        val navView: BottomNavigationView = binding.navView
        val tuerquita = findViewById<ImageView>(R.id.imgVSettingsPerfil)
        tuerquita.visibility = View.GONE
        tuerquita.setOnClickListener {
            val intent = Intent(this, activity_configuracion::class.java)
            startActivity(intent)
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_inicio,
                R.id.fragment_control,
                R.id.fragment_agendar,
                R.id.fragment_notificaciones,
                R.id.fragment_usuario,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        updateNavigationBarAppearance(navView)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragment_usuario -> tuerquita.visibility = View.VISIBLE
                else -> tuerquita.visibility = View.GONE
            }

            val icBusqueda = findViewById<ImageView>(R.id.imgIconoSearch)
            icBusqueda.setOnClickListener {
                val intent = Intent(this, activity_busqueda::class.java)
                startActivity(intent)
            }

            val btnAgendar = findViewById<ImageButton>(R.id.imgBtnAgendar)
            btnAgendar.setOnClickListener {
                val intent = Intent(this, activity_agendar::class.java)
                startActivity(intent)
            }

            val icBusquedaRapida = findViewById<ImageView>(R.id.imgIconoFastSearch)
            icBusquedaRapida.setOnClickListener {
                val intent = Intent(this, activity_busqueda_rapida_hombre::class.java)
                startActivity(intent)
            }

            if (intent.getBooleanExtra("ir_atras", false)) {
                navController.navigate(R.id.fragment_inicio)
                finish()
            }

            if (intent.getBooleanExtra("go_back", false)) {
                navController.navigate(R.id.fragment_inicio)
            }
        }
    }

    fun signOutAndStartSignInActivity() {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener(this) {
            val intent = Intent(this@MainActivity, activity_login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateNavigationBarAppearance(navView: BottomNavigationView) {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {

                navView.setBackgroundResource(R.drawable.fondonav)
                navView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.iconColorDark)
                navView.itemTextColor = ContextCompat.getColorStateList(this, R.color.iconColorDark)
            }
            Configuration.UI_MODE_NIGHT_YES -> {

                navView.setBackgroundResource(R.drawable.nav_negra)
                navView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.iconColorLight)
                navView.itemTextColor = ContextCompat.getColorStateList(this, R.color.iconColorLight)
            }
        }
    }
}
