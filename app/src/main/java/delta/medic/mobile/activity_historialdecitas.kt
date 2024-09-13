package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassCitas
import RecycleViewHelper.AdaptadorCitas
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class activity_historialdecitas : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historialdecitas)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val txtEstadoCita = findViewById<TextView>(R.id.txtEstadoCitas)
        val txtHistorialCitas = findViewById<TextView>(R.id.txtHistorial)

/*
val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
when (currentNightMode) {
    Configuration.UI_MODE_NIGHT_NO -> {
        btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
        txtHistorialCitas.setTextColor(ContextCompat.getColor(this, R.color.black))
        txtEstadoCita.setTextColor(ContextCompat.getColor(this, R.color.black))
    } // Night mode is not active, we're using the light theme.
    Configuration.UI_MODE_NIGHT_YES -> {
        btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
        txtHistorialCitas.setTextColor(ContextCompat.getColor(this, R.color.white))
        txtEstadoCita.setTextColor(ContextCompat.getColor(this, R.color.white))
    } // Night mode is active, we're using dark theme.
}

 */

btnRegresar.setOnClickListener {
    finish()
}

val rcvHistorialCitas = findViewById<RecyclerView>(R.id.rcvHistorialCitas)
rcvHistorialCitas.layoutManager = LinearLayoutManager(this)

CoroutineScope(Dispatchers.IO).launch {
    try {
        val citasDB = obtenerDatos()
        withContext(Dispatchers.Main) {
            if (citasDB.isEmpty()) {
                txtEstadoCita.visibility = View.VISIBLE
                txtEstadoCita.text = "Aún no tienes citas programadas"
            } else {
                txtEstadoCita.visibility = View.GONE
                val miAdaptador = AdaptadorCitas(citasDB)
                rcvHistorialCitas.adapter = miAdaptador
            }
        }
    } catch (e: Exception) {
        println("Error al obtener los datos de la base de datos: ${e.message}")
    }
}
}

suspend fun obtenerDatos(): List<dataClassCitas> {
return withContext(Dispatchers.IO) {
    val citas = mutableListOf<dataClassCitas>()
    try {
        val objConexion = ClaseConexion().cadenaConexion()
        if (objConexion != null) {
            val statement = objConexion.prepareStatement(
                """
SELECT 
    citas.ID_Cita,
    citas.diacita,
    citas.horacita,
    citas.motivo,
    citas.estadoCita,
    citas.ID_Doctor,
    usua.ID_Usuario,
    usua.nombreUsuario,
    usua.apellidoUsuario,
    usua.imgUsuario,
    esp.nombreEspecialidad
FROM 
    tbCitasMedicas citas
INNER JOIN tbDoctores docs ON citas.ID_Doctor = docs.ID_Doctor
INNER JOIN tbEspecialidades esp ON docs.ID_Especialidad = esp.ID_Especialidad
INNER JOIN tbUsuarios usua ON docs.ID_Usuario = usua.ID_Usuario
WHERE 
    usua.emailUsuario = ?
ORDER BY 
    citas.diacita DESC, 
    citas.horacita DESC
                """.trimIndent()
            )!!
            statement.setString(1, userEmail)
            val resultset = statement.executeQuery()
            while (resultset.next()) {
                val ID_Cita = resultset.getInt("ID_Cita")
                val diaCita = resultset.getDate("diaCita")
                val horaCita = resultset.getTimestamp("horaCita")
                val motivo = resultset.getString("motivo")
                val EstadoCita = resultset.getString("EstadoCita")
                val ID_Paciente = resultset.getInt("ID_Paciente")
                val nombrePaciente = resultset.getString("nombrePaciente")
                val parentesco = resultset.getString("parentesco")
                val ID_Usuario = resultset.getInt("ID_Usuario")
                val nombreDoctor = resultset.getString("nombreUsuario")
                val apellidoDoctor = resultset.getString("apellidoUsuario")
                val especialidad = resultset.getString("nombreespecialidad")
                val cita = dataClassCitas(
                    ID_Cita,
                    diaCita,
                    horaCita,
                    motivo,
                    EstadoCita,
                    ID_Usuario,
                    nombreDoctor,
                    apellidoDoctor,
                    especialidad
                )
                citas.add(cita)
                println("DiaCita: $diaCita")
            }
        } else {
            println("No se pudo establecer una conexión con la base de datos.")
        }
    } catch (e: Exception) {
        println("Este es el error ${e.message}")
    }
    citas
}
}
}
