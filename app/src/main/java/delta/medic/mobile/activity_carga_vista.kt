package delta.medic.mobile

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_carga_vista : AppCompatActivity() {

    var ID_User: Int = 0
    var nombreUsuario: String = ""
    var apellidoUsuario: String = ""
    var imgUsuario: String = ""
    var nombreEspecialidad: String = ""
    var ID_Sucursal: Int = 0
    var nombreSucursal: String = ""
    var telefonoSucursal: String = ""
    var direccionSucursal: String = ""
    var longSucursal: Double = 0.0
    var latiSucursal: Double = 0.0
    var imgSucursal: String = ""
    var whatsapp: String = ""
    var valoFinal: Double = 0.0
    var nombreServicio: String = ""
    var costo: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carga_vista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        var ID_Doctor = intent.getIntExtra("ID_Doctor", 0)
        Log.e("Err" ,"$ID_Doctor")
        var emailDoctor = intent.getStringExtra("doctorEmail")

        //Corrutina para obtener y mostrar toda la info
        CoroutineScope(Dispatchers.IO).launch {
            val conexion = ClaseConexion().cadenaConexion()
            val statement = conexion?.prepareStatement(
                """
        SELECT 
            u.ID_Usuario,
            u.nombreUsuario,
            u.apellidoUsuario,
            u.imgUsuario,
            e.nombreEspecialidad,
            s.ID_Sucursal,
            s.nombreSucursal,
            s.telefonoSucur,
            s.direccionSucur,
            s.longSucur,
            s.latiSucur,
            s.imgSucursal,
            s.whatsapp,
            s.valoFinal
        FROM 
            tbDoctores d
        INNER JOIN tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
        INNER JOIN tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
        INNER JOIN tbSucursales s ON d.ID_Sucursal = s.ID_Sucursal
        WHERE 
            u.emailUsuario = ? AND d.ID_Doctor = ?
        """
            )
            statement?.setString(1, emailDoctor)
            statement?.setInt(2, ID_Doctor)
            val resultSet = statement?.executeQuery()
            try {
            } catch (e: Exception) {
                Log.e("Error", "Error obteniendo información del doctor", e)
            }
            finally {
                withContext(Dispatchers.IO) {
                    if (resultSet?.next() == true) {
                        ID_User = resultSet.getInt("ID_Usuario")
                        nombreUsuario = resultSet.getString("nombreUsuario").toString()
                        apellidoUsuario = resultSet.getString("apellidoUsuario").toString()
                        imgUsuario = resultSet.getString("imgUsuario").toString()
                        nombreEspecialidad = resultSet.getString("nombreEspecialidad").toString()
                        ID_Sucursal = resultSet.getInt("ID_Sucursal")
                        nombreSucursal = resultSet.getString("nombreSucursal").toString()
                        telefonoSucursal = resultSet.getString("telefonoSucur").toString()
                        direccionSucursal = resultSet.getString("direccionSucur").toString()
                        longSucursal = resultSet.getDouble("longSucur")
                        latiSucursal = resultSet.getDouble("latiSucur")
                        imgSucursal = resultSet.getString("imgSucursal").toString()
                        whatsapp = resultSet.getString("whatsapp").toString()
                        valoFinal = resultSet.getDouble("valoFinal")

                        Log.d("INFO", "$ID_User $nombreUsuario $apellidoUsuario $imgUsuario $nombreEspecialidad $ID_Sucursal $nombreSucursal $telefonoSucursal $direccionSucursal $longSucursal $latiSucursal $imgSucursal $whatsapp $valoFinal $nombreServicio $costo")
                    } else {
                        Log.e("Error", "No se encontraron filas para el doctor.")
                    }

                    resultSet?.close()
                    statement?.close()
                    conexion?.close()
                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            val lottieView = findViewById<LottieAnimationView>(R.id.lottie_view)

            delay(3000)
            val intent = Intent(this@activity_carga_vista, activity_vistadoctores::class.java)
            intent.putExtra("ID_Doctor", ID_Doctor)
            intent.putExtra("doctorEmail", emailDoctor)
            intent.putExtra("ID_Usuario", ID_User)
            intent.putExtra("nombreUsuario", nombreUsuario)
            intent.putExtra("apellidoUsuario", apellidoUsuario)
            intent.putExtra("imgUsuario", imgUsuario)
            intent.putExtra("nombreEspecialidad", nombreEspecialidad)
            intent.putExtra("ID_Sucursal", ID_Sucursal)
            intent.putExtra("nombreSucursal", nombreSucursal)
            intent.putExtra("telefonoSucursal", telefonoSucursal)
            intent.putExtra("direccionSucursal", direccionSucursal)
            intent.putExtra("longSucursal", longSucursal)
            intent.putExtra("latiSucursal", latiSucursal)
            intent.putExtra("imgSucursal", imgSucursal)
            intent.putExtra("whatsapp", whatsapp)
            intent.putExtra("valoFinal", valoFinal)
            startActivity(intent)
            finish()
        }
    }
}