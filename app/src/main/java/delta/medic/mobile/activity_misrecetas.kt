package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassMisRecetas
import Modelo.dataClassPacientes
import RecycleViewHelper.AdaptadorMisRecetas
import RecycleViewHelper.AdaptadorPacientes
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_misrecetas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_misrecetas)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val rcvMisRecetas = findViewById<RecyclerView>(R.id.rcvMisRecetas)

        //Adapter
        rcvMisRecetas.layoutManager= LinearLayoutManager(this)
        CoroutineScope(Dispatchers.Main).launch {
            val listaPacientes = obtenerRecetas(activity_login.userEmail)
            val adapter = AdaptadorMisRecetas(listaPacientes)
            rcvMisRecetas.adapter = adapter
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }

    suspend fun obtenerRecetas(emailUsuario: String): List<dataClassMisRecetas> {
        val listaRecetas = mutableListOf<dataClassMisRecetas>()

        withContext(Dispatchers.IO) {
            val conexion = ClaseConexion().cadenaConexion()

            if (conexion != null) {
                val statement = conexion.prepareStatement(
                    """SELECT 
                                rec.fechaReceta,
                                rec.ubicacionPdf,
                                docUsuarios.nombreUsuario AS nombreDoctor,
                                docUsuarios.apellidoUsuario AS apellidoDoctor
                            FROM 
                                tbIndicaciones indi
                            INNER JOIN 
                                tbTiempos tiem ON indi.id_tiempo = tiem.id_tiempo
                            INNER JOIN 
                                tbRecetas rec ON indi.id_receta = rec.id_receta
                            INNER JOIN 
                                tbFichasMedicas fichi ON rec.id_receta = fichi.id_receta
                            INNER JOIN 
                                tbCitasMedicas citas ON fichi.id_cita = citas.id_cita
                            INNER JOIN 
                                tbCentrosMedicos cm ON cm.ID_Centro = citas.ID_Centro
                            INNER JOIN 
                                tbDoctores d ON d.ID_Doctor = cm.ID_Doctor
                            INNER JOIN 
                                tbUsuarios docUsuarios ON d.ID_Usuario = docUsuarios.ID_Usuario
                            INNER JOIN 
                                tbPacientes pacs ON citas.id_paciente = pacs.id_paciente
                            INNER JOIN 
                                tbUsuarios usua ON pacs.id_usuario = usua.id_usuario
                            WHERE 
                                usua.emailusuario = ?"""
                )

                statement?.setString(1, emailUsuario)

                val resultado = statement?.executeQuery()

                while (resultado?.next() == true) {
                    val fechaReceta = resultado.getString("fechaReceta")
                    val ubicacionPdf = resultado.getString("ubicacionPdf") ?: "no tiene ubicación"
                    val nombreDoctor = resultado.getString("nombreDoctor")
                    val apellidoDoctor = resultado.getString("apellidoDoctor")

                    val receta = dataClassMisRecetas(nombreDoctor, apellidoDoctor, fechaReceta, ubicacionPdf)
                    listaRecetas.add(receta)
                }
                resultado?.close()
                statement?.close()
                conexion.close()
            } else {
                // Manejar error de conexión
                println("Error al conectar con la base de datos.")
            }
        }
        return listaRecetas
    }
}