package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassResena
import RecycleViewHelper.AdaptadorResenas
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_misresenas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_misresenas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val rcvMisResenas = findViewById<RecyclerView>(R.id.rcvMisResenas)

        btnRegresar.setOnClickListener {
            finish()
        }

        rcvMisResenas.layoutManager= LinearLayoutManager(this)
        CoroutineScope(Dispatchers.Main).launch {
            val listaResenas = obtenerDatosReviews()
            val adapter = AdaptadorResenas(listaResenas)
            rcvMisResenas.adapter = adapter
        }
    }

    private suspend fun obtenerDatosReviews(): MutableList<dataClassResena> {
        return withContext(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val reseña = mutableListOf<dataClassResena>()

            if (objConexion == null) {
                Log.e("obtenerDatos", "No se pudo establecer una conexión con la base de datos.")
                return@withContext reseña // Retorna la lista vacía si no hay conexión
            }

            try {
                val statement = objConexion.prepareStatement(
                    """
                SELECT
                    rv.comentario,
                    rv.promEstrellas,
                    u.ID_Usuario,
                    u.nombreUsuario,
                    u.apellidoUsuario,
                    u.imgUsuario,
                    d.ID_Doctor
                FROM 
                    tbReviews rv
                INNER JOIN 
                    tbUsuarios u ON rv.ID_Usuario = u.ID_Usuario
                INNER JOIN
                    tbDoctores d ON rv.ID_Doctor = d.ID_Doctor
                WHERE 
                    u.emailUsuario = ?
                """
                )
                statement.setString(1, userEmail)
                Log.d("obtenerDatos", "Email del usuario: $userEmail")

                val resultSet = statement.executeQuery()
                while (resultSet.next()) {
                    // Extraer datos del resultSet
                    val comentario = resultSet.getString("comentario")
                    val promEstrellas = resultSet.getFloat("promEstrellas")
                    val nombreUsuario = resultSet.getString("nombreUsuario")
                    val apellidoUsuario = resultSet.getString("apellidoUsuario")
                    val imgUsuario = resultSet.getString("imgUsuario")
                    val ID_Usuario = resultSet.getInt("ID_Usuario")
                    val ID_Doctor = resultSet.getInt("ID_Doctor")

                    val valoresJuntos = dataClassResena(
                        comentario,
                        promEstrellas,
                        nombreUsuario,
                        apellidoUsuario,
                        imgUsuario,
                        ID_Doctor,
                        ID_Usuario
                    )
                    reseña.add(valoresJuntos)
                }
            } catch (e: Exception) {
                Log.e("obtenerDatos", "Error al obtener datos: ${e.message}", e)
            }

            reseña // Retorna la lista de reseñas
        }
    }
}