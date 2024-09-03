package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassPacientes
import Modelo.dataClassUsuario
import RecycleViewHelper.AdaptadorPacientes
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

class activity_pacientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pacientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Values
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val rcvPacientes = findViewById<RecyclerView>(R.id.rcvPacientes)
        val fabCrearPacientes = findViewById<ExtendedFloatingActionButton>(R.id.fabCrearPacientes)
        val txtPacientes = findViewById<TextView>(R.id.txtPacientes)

        //Adapter
        rcvPacientes.layoutManager= LinearLayoutManager(this)
        CoroutineScope(Dispatchers.Main).launch {
            val listaPacientes = obtenerPacientes(activity_login.userEmail)
            val adapter = AdaptadorPacientes(listaPacientes)
            rcvPacientes.adapter = adapter
        }

        //Listener
        btnRegresar.setOnClickListener {
            finish()
        }


    }

    suspend fun obtenerPacientes(emailUsuario: String): List<dataClassPacientes> {
        val listaPacientes = mutableListOf<dataClassPacientes>()

        withContext(Dispatchers.IO) {
            val conexion = ClaseConexion().cadenaConexion()

            if (conexion != null) {
                val statement = conexion.prepareStatement(
                    "SELECT * FROM tbPacientes WHERE ID_Usuario = (SELECT ID_Usuario FROM tbUsuarios WHERE emailUsuario = ?)"
                )

                statement?.setString(1, emailUsuario)

                val resultado = statement?.executeQuery()

                while (resultado?.next() == true) {
                    val idPaciente = resultado.getInt("ID_Paciente")
                    val nombrePaciente = resultado.getString("nombrePaciente")
                    val apellidoPaciente = resultado.getString("apellidoPaciente")
                    val imgPaciente = resultado.getString("imgPaciente") ?: "no hay"
                    val parentesco = resultado.getString("parentesco")
                    val idUsuario = resultado.getInt("ID_Usuario")

                    val paciente = dataClassPacientes(idPaciente, nombrePaciente, apellidoPaciente, imgPaciente, parentesco, idUsuario)
                    listaPacientes.add(paciente)
                }
                resultado?.close()
                statement?.close()
                conexion.close()
            } else {
                // Manejar error de conexión
                println("Error al conectar con la base de datos.")
            }
        }
        return listaPacientes
    }

}