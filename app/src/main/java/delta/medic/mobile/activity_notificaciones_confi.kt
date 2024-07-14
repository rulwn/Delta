package delta.medic.mobile

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Switch
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class activity_notificaciones_confi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notificaciones_confi)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val canalID = "NotificacionesCanal"

        val switchNotificaciones = findViewById<Switch>(R.id.switchNotificacionesSiNo)

        fun CanalNotificaciones() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Nombre del Canal"
                val descriptionText = "Descripción del Canal"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(canalID, name, importance).apply {
                    description = descriptionText
                }
                val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

         fun mostrarNotificacion() {
            val builder = NotificationCompat.Builder(this, canalID)
                .setSmallIcon(R.drawable.ic_notificaciones)
                .setContentTitle("Notificación")
                .setContentText("Descripción de la notificación")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, builder.build())
        }

        switchNotificaciones.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                CanalNotificaciones()
                mostrarNotificacion()
                Toast.makeText(this, "Notificaciones activadas", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notificaciones desactivadas", Toast.LENGTH_SHORT).show()
            }
        }

        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val txtNotificacionesConfi = findViewById<TextView>(R.id.txtNotificacionesConfi)
        val txtPregunta = findViewById<TextView>(R.id.txtPregunta)

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                txtNotificacionesConfi.setTextColor(ContextCompat.getColor(this, R.color.black))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
                txtPregunta.setTextColor(ContextCompat.getColor(this, R.color.black))
            } // Night mode is not active, we're using the light theme.
            Configuration.UI_MODE_NIGHT_YES -> {
                txtNotificacionesConfi.setTextColor(ContextCompat.getColor(this, R.color.white))
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                txtPregunta.setTextColor(ContextCompat.getColor(this, R.color.white))
            } // Night mode is active, we're using dark theme.
        }
        btnRegresar.setOnClickListener {
            finish()
        }
    }
}