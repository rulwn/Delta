package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassFavoritos
import RecycleViewHelper.AdaptadorFavoritos
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_doctoresfavoritos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctoresfavoritos)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val emailUsuario = activity_login.userEmail
        val rcvDoctoresFav = findViewById<RecyclerView>(R.id.rcvDoctoresFav)
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)

        rcvDoctoresFav.layoutManager =
            GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        CoroutineScope(Dispatchers.Main).launch {
            val listaFavoritos = obtenerFavoritos(emailUsuario)
            val adapter = AdaptadorFavoritos(listaFavoritos)
            adapter.emailUsuario = emailUsuario
            rcvDoctoresFav.adapter = adapter
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }

    suspend fun obtenerFavoritos(EmailUsuario: String): List<dataClassFavoritos> {
        val listaFavoritos = mutableListOf<dataClassFavoritos>()

        withContext(Dispatchers.IO) {
            // Crear una conexión a la base de datos
            val conexion = ClaseConexion().cadenaConexion()

            // Preparar la consulta
            val statement = conexion?.prepareStatement(
                """SELECT
                    u.ID_Usuario,
                    u.nombreUsuario,
                    u.imgUsuario,
                    d.ID_Doctor,
                    s.ID_Sucursal,
                    s.imgSucursal,
                    e.nombreEspecialidad
                    FROM
                    tbFavoritos f
                    INNER JOIN tbDoctores d ON d.ID_Doctor = f.ID_Doctor
                    INNER JOIN tbSucursales s ON s.ID_Sucursal = f.ID_Sucursal
                    INNER JOIN tbUsuarios u ON u.ID_Usuario = d.ID_Usuario
                    INNER JOIN tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
                    WHERE
                    f.ID_Usuario = (SELECT ID_Usuario FROM tbUsuarios WHERE emailUsuario = ?)"""
            )

            // Establecer el parámetro
            statement?.setString(1, EmailUsuario)

            // Ejecutar la consulta y obtener los resultados
            val resultado = statement?.executeQuery()

            // Procesar los resultados
            while (resultado?.next() == true) {
                val idUsuario = resultado.getInt("ID_Usuario")
                val idDoctor = resultado.getInt("ID_Doctor")
                val idSucursal = resultado.getInt("ID_Sucursal")
                val nombreUsuario = resultado.getString("nombreUsuario")
                val imgUsuario = resultado.getString("imgUsuario") ?: "no hay"
                val imgSucursal = resultado.getString("imgSucursal") ?: "no hay"
                val nombreEspecialidad = resultado.getString("nombreEspecialidad")

                // Crear un objeto dataClassFavoritos
                val favorito = dataClassFavoritos(
                    idUsuario,
                    idDoctor,
                    idSucursal,
                    nombreUsuario,
                    imgUsuario,
                    imgSucursal,
                    nombreEspecialidad
                )

                // Agregar el objeto a la lista
                listaFavoritos.add(favorito)
            }

            // Cerrar la conexión
            resultado?.close()
            statement?.close()
            conexion?.close()
        }

        return listaFavoritos
    }
}