package delta.medic.mobile
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import delta.medic.mobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            removeFragmentInicio()
            openFragment(fragment_busquedaRapidaHombre())

            if (intent.getBooleanExtra("ir_atras", false)) {
                navController.navigate(R.id.fragment_inicio)
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun removeFragmentInicio() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_inicio)
        fragment?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
            finish()
        }
    }
}
