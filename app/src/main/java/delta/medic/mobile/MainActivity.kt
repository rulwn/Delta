package delta.medic.mobile
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
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
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import delta.medic.mobile.activity_login.UserData.userEmail

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_inicio, R.id.fragment_control, R.id.fragment_agendar, R.id.fragment_usuario, R.id.fragment_notificaciones
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

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
            //findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.fragment_busquedaRapidaHombre)
            /*supportFragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment_activity_main, fragment_busquedaRapidaHombre())
                addToBackStack(null)
                commit()
            }*/
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


    fun makeNotification() {
        val channelId = "CHANEL_ID_NOTIFICATION"
        val builder = NotificationCompat.Builder(applicationContext,channelId)
            .setSmallIcon(R.drawable.ic_notificaciones)
            .setContentTitle("Notificación")
            .setContentText("Texto de la notificación")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(applicationContext, activity_notificaciones_confi::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("data", "Algunos valores")
        }
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_MUTABLE)
        builder.setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel = notificationManager.getNotificationChannel(channelId)
            if (notificationChannel == null) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                notificationChannel = NotificationChannel(channelId, "Descripción", importance).apply {
                    lightColor = Color.GREEN
                    enableVibration(true)
                }
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

        notificationManager.notify(0, builder.build())
    }
}
