package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassResena
import Modelo.dataClassServicios
import RecycleViewHelper.AdaptadorResenas
import RecycleViewHelper.AdaptadorServicios
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.imageview.ShapeableImageView
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class activity_vistadoctores : AppCompatActivity(), OnMapReadyCallback {

    var latitud: Double = 0.0
    var longitud: Double = 0.0
    var nombreUser: String = ""
    var apellidoUser: String = ""
    var imgUser: String = ""
    var ID_Sucursal: Int = 0
    var isFav: Boolean = false
    var doctorEmail: String = ""

    var ID_Doctor: Int = 0  // Inicializar sin valor hasta que se obtenga del Intent
    var ID_User: Int = 0
    var nombreUsuario: String = ""
    var apellidoUsuario: String = ""
    var imgUsuario: String = ""
    var imgSucursal: String = ""


    private lateinit var adaptadorResenas: AdaptadorResenas
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vistadoctores)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar el valor de ID_Doctor desde el Intent
        ID_Doctor = intent?.getIntExtra("ID_Doctor", -1) ?: -1
        doctorEmail = intent?.getStringExtra("doctorEmail") ?: ""

        if (ID_Doctor == -1) {
            Log.e("Error", "ID_Doctor no válido")
            Toast.makeText(this, "Error: No se recibió un ID de Doctor válido", Toast.LENGTH_SHORT).show()
            finish() // Finaliza la actividad si el ID_Doctor no es válido
            return
        }

        //Crear lista mutable de adaptadorResenas
        adaptadorResenas = AdaptadorResenas(mutableListOf())

        //Llamar elementos de activity
        val nombreSucursal = findViewById<TextView>(R.id.nombreSucursal)
        val numeroClinica = findViewById<TextView>(R.id.numeroClinica)
        val direccion_Clinica = findViewById<TextView>(R.id.direccion_Clinica)
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val nombreDoctor = findViewById<TextView>(R.id.nombreDoctor)
        val Especialidad = findViewById<TextView>(R.id.Especialidad)
        val img_clinic = findViewById<ImageView>(R.id.img_clinic)
        val imgDoctor = findViewById<ShapeableImageView>(R.id.imgDoctor)
        val toggleButton = findViewById<ToggleButton>(R.id.toggleButton)
        val button_reservar = findViewById<TextView>(R.id.button_reservar)
        val btnSubir = findViewById<Button>(R.id.btnSubir)
        val RatingBar = findViewById<RatingBar>(R.id.ratingBar2)
        val txtReview = findViewById<TextView>(R.id.txtReview)
        val numeroWha = findViewById<TextView>(R.id.numeroWha)
        val txtRating = findViewById<TextView>(R.id.txtRating)

        //Crear variables globales
        var imgSucursal: String = "";
        var nombreUsuario: String = "";
        var apellidoUsuario: String = "";
        var imgUsuario: String = "";

        //Funcion para regresar con ID_Doctor
        btnRegresar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Mandar info a activity_agendar

        var DoctorExist: Boolean = true
        Log.e("ID_Doctor", ID_Doctor.toString())
        button_reservar.setOnClickListener {
            val intent = Intent(this, activity_agendar::class.java)
            intent.putExtra("ID_Doctor", ID_Doctor)
            intent.putExtra("DoctorExist", DoctorExist)
            startActivity(intent)
        }

        //Función para obtener info de user para Reviews
        CoroutineScope(Dispatchers.IO).launch {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement =
                objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE emailUsuario = ?")
            statement?.setString(1, userEmail)
            val resultSet = statement?.executeQuery()
            try {
                withContext(Dispatchers.Main) {
                    if (resultSet?.next() == true) {
                        ID_User = resultSet.getInt("ID_Usuario")
                        Log.e("ID_User", ID_User.toString())
                        nombreUser = resultSet.getString("nombreUsuario")
                        apellidoUser = resultSet.getString("apellidoUsuario")
                        imgUser = resultSet.getString("imgUsuario")
                        val txtUserReview = findViewById<TextView>(R.id.txtUserReview)
                        val nombreCompleto = "${nombreUser ?: ""} ${apellidoUser ?: ""}".trim()
                        txtUserReview.text = nombreCompleto
                        val imgProfileReview = findViewById<ImageView>(R.id.imgProfileReview)
                        Glide.with(imgProfileReview)
                            .load(imgUser)
                            .circleCrop()
                            .into(imgProfileReview)
                    }
                }
            } catch (e: Exception) {
                Log.e("Error", "Error obteniendo información del usuario", e)
            } finally {
                withContext(Dispatchers.IO) {
                    resultSet?.close()
                    statement?.close()
                    objConexion?.close()
                }
            }
        }

        //Corrutina para obtener y mostrar toda la info
        CoroutineScope(Dispatchers.IO).launch {
            val nombreDoctorIntent = intent.getStringExtra("nombreUsuario")
            val apellidoDoctorIntent = intent.getStringExtra("apellidoUsuario")
            val imgDoctorIntent = intent.getStringExtra("imgUsuario")
            val imgSucursalIntent = intent.getStringExtra("imgSucursal")
            val nombreEspecialidadIntent = intent.getStringExtra("nombreEspecialidad")
            val ID_SucursalIntent = intent.getIntExtra("ID_Sucursal", 0)
            val nombreSucursalIntent = intent.getStringExtra("nombreSucursal")
            val telefonoSucursalIntent = intent.getStringExtra("telefonoSucursal")
            val direccionSucursalIntent = intent.getStringExtra("direccionSucursal")
            val longSucursalIntent = intent.getDoubleExtra("longSucursal", 0.0)
            val latiSucursalIntent = intent.getDoubleExtra("latiSucursal", 0.0)
            val whatsappIntent = intent.getStringExtra("whatsapp")
            val valoFinalIntent = intent.getDoubleExtra("valoFinal", 0.0)

            try {
                withContext(Dispatchers.Main) {
                        //Usuario
                    nombreUsuario = nombreDoctorIntent.toString()
                    apellidoUsuario = apellidoDoctorIntent.toString()
                    val nombreCompleto = "Dr. ${nombreUsuario ?: ""} ${apellidoUsuario ?: ""}".trim()
                    nombreDoctor.text = nombreCompleto
                    imgUsuario = imgDoctorIntent.toString()
                    Glide.with(imgDoctor)
                        .load(imgUsuario)
                        .circleCrop()
                        .into(imgDoctor)
                    imgSucursal = imgSucursalIntent.toString()
                    Glide.with(img_clinic)
                        .load(imgSucursal)
                        .into(img_clinic)

                        //Especialidad
                        Especialidad.text = nombreEspecialidadIntent.toString()
                        //Sucursal
                        ID_Sucursal = ID_SucursalIntent
                        nombreSucursal.text = nombreSucursalIntent.toString()
                        numeroClinica.text = telefonoSucursalIntent.toString()
                        direccion_Clinica.text = direccionSucursalIntent.toString()
                        latitud = latiSucursalIntent
                        longitud = longSucursalIntent
                        numeroWha.text = whatsappIntent.toString()
                        txtRating.text = valoFinalIntent.toString()
                }
            } catch (e: Exception) {
                Log.e("Error", "Error obteniendo información del doctor", e)
            }
        }

        //Función para mostrar servicios
        val rcvServicios = findViewById<RecyclerView>(R.id.rcvServicios)
        val txtNoServices = findViewById<TextView>(R.id.txtNoServices)
        rcvServicios.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        CoroutineScope(Dispatchers.IO).launch {
            val centrosDB = obtenerDatos(ID_Doctor)
            withContext(Dispatchers.Main) {
                if (centrosDB.isEmpty()) {
                    txtNoServices.visibility = View.VISIBLE
                    rcvServicios.visibility = View.GONE
                } else {
                    txtNoServices.visibility = View.GONE
                    rcvServicios.visibility = View.VISIBLE

                    val miAdapter = AdaptadorServicios(centrosDB)
                    rcvServicios.adapter = miAdapter
                }
            }
        }

        //Función para mostrar reseñas
        val textViewError = findViewById<TextView>(R.id.lblNoComments)
        val rcvResenas = findViewById<RecyclerView>(R.id.rcvResenas)
        rcvResenas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        CoroutineScope(Dispatchers.IO).launch {
            val review = obtenerDatosReviews(ID_Doctor)
            withContext(Dispatchers.Main) {
                if (review.isNullOrEmpty()) {
                    textViewError.visibility = View.VISIBLE
                    rcvResenas.visibility = View.GONE
                } else {
                    textViewError.visibility = View.GONE
                    val miAdapter = AdaptadorResenas(review)
                    rcvResenas.adapter = miAdapter
                    rcvResenas.visibility = View.VISIBLE
                }
            }
        }

        //Mapa de google
        mapView = findViewById(R.id.mapUbicacion)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        suspend fun getFavStatus(): Boolean {
            val conexion = ClaseConexion().cadenaConexion()
            var isFav = false

            // Verifica si la conexión es nula
            if (conexion == null) {
                println("Error: No se pudo obtener la conexión.")
                return isFav
            }

            try {
                conexion.prepareStatement(
                    "SELECT COUNT(*) FROM tbFavoritos WHERE ID_Usuario = (SELECT ID_Usuario FROM tbUsuarios WHERE emailUsuario = ?) AND ID_Doctor = ? AND ID_Sucursal = ?"
                ).use { statement ->
                    statement.setString(1, userEmail)
                    statement.setInt(2, ID_Doctor)
                    statement.setInt(3, ID_Sucursal)

                    statement.executeQuery().use { resultSet ->
                        if (resultSet.next()) {
                            isFav = resultSet.getInt(1) > 0
                        }
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                try {
                    conexion.close()
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }
            println("getFavStatus result: $isFav")
            return isFav
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun updateToggleButton(toggleButton: ToggleButton, isFav: Boolean) {
            if (isFav) {
                toggleButton.background = getDrawable(R.drawable.corazon_favoritos)
                toggleButton.isChecked = true
            } else {
                toggleButton.background = getDrawable(R.drawable.corazon_vacio)
                toggleButton.isChecked = false
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            isFav = getFavStatus()
            withContext(Dispatchers.Main) {
                updateToggleButton(toggleButton, isFav)
            }
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        suspend fun validarRecientes(){
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                objConexion?.prepareCall("{CALL PROC_STATE_VALIDATION_RECIENTES(?,?,?)}")
                    ?.use { validation ->
                        validation.setString(1, userEmail)
                        validation.setInt(2, ID_Sucursal)
                        validation.setInt(3, ID_Doctor)
                        validation.execute()
                    }
            } catch (e: Exception) {
                println("Error: $e")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            validarRecientes()
        }

        //Funciones para favorito
        toggleButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val conexion = ClaseConexion().cadenaConexion()
                val favStatus = if (this@activity_vistadoctores.isFav) {
                    "T"
                } else {
                    "F"
                }

                //println("Estado inicial toggleButton.isChecked: $isFav")
                println("ID_Sucursal: $ID_Sucursal")

                conexion?.prepareCall("{CALL PROC_ADMIN_FAVORITOS(?,?,?,?)}").use { callable ->
                    callable?.setString(1, userEmail)
                    callable?.setInt(2, ID_Doctor)
                    callable?.setInt(3, ID_Sucursal)
                    callable?.setString(4, favStatus)  // Usar STRING en lugar de BOOLEAN
                    callable?.executeUpdate()
                }

                withContext(Dispatchers.Main) {
                    if (isFav) {
                        isFav = false
                        toggleButton.background = getDrawable(R.drawable.corazon_vacio) // Cambia a icono de no favorito
                    } else {
                        isFav = true
                        toggleButton.background = getDrawable(R.drawable.corazon_favoritos) // Cambia a icono de favorito
                    }
                }
            }
        }

        //Funcion para insertar reseña
        fun insertResenas(resena: dataClassResena, lista: MutableList<dataClassResena>) {
            CoroutineScope(Dispatchers.IO).launch {
                var conexion: Connection? = null
                var statement: PreparedStatement? = null

                try {
                    conexion = ClaseConexion().cadenaConexion()
                    statement = conexion?.prepareStatement(
                        "INSERT INTO tbReviews (promEstrellas, comentario, ID_Doctor, ID_Usuario) VALUES (?, ?, ?, ?)"
                    )
                    statement?.apply {
                        setDouble(1, resena.promEstrellas.toDouble())
                        setString(2, resena.comentario)
                        setInt(3, resena.ID_Doctor)
                        setInt(4, resena.ID_Usuario)
                        executeUpdate()
                    }

                    lista.add(resena)

                    withContext(Dispatchers.Main) {
                        adaptadorResenas.agregarItem(resena)
                        adaptadorResenas.actualizarLista(lista)
                        Toast.makeText(
                            this@activity_vistadoctores,
                            "Reseña insertada con éxito.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    Log.e("insertResenas", "Error al insertar reseña: ${e.message}")
                } finally {
                    statement?.close()
                    conexion?.close()
                }
            }
        }

        //Función para mandar a llamar la funcion de insertar reseña
        btnSubir.setOnClickListener {
            // Validar primero si el campo de texto de la reseña no está vacío
            if (txtReview.text.toString().isNotEmpty()) {
                // Si el campo de texto está lleno, validar el RatingBar
                if (RatingBar.rating > 0f) {
                    // Si ambas validaciones pasan, ejecutar el resto del código
                    val nuevaResena = dataClassResena(
                        txtReview.text.toString(),
                        RatingBar.rating,
                        nombreUser,
                        apellidoUser,
                        imgUser,
                        ID_Doctor,
                        ID_User
                    )
                    // Obtener la lista de reseñas
                    val listaResenas = adaptadorResenas.obtenerLista()
                    insertResenas(nuevaResena, listaResenas)
                    // Configurar el RecyclerView
                    rcvResenas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

                    CoroutineScope(Dispatchers.IO).launch {
                        val conexion = ClaseConexion().cadenaConexion()

                        try {
                            // Insertar en tbReviews
                            val insertReviewQuery = """
                        INSERT INTO tbReviews (ID_Doctor, promEstrellas, comentario, ID_Usuario)
                        VALUES (?, ?, ?, ?)
                    """
                            conexion?.prepareStatement(insertReviewQuery).use { statement ->
                                statement?.setInt(1, ID_Doctor)
                                statement?.setDouble(2, RatingBar.rating.toDouble())
                                statement?.setString(3, txtReview.text.toString())
                                statement?.setInt(4, ID_User)
                                statement?.executeUpdate()
                            }

                            // Obtener la sucursal asociada al doctor
                            val obtenerSucursalQuery = """
                        SELECT d.ID_Sucursal
                        FROM tbDoctores d
                        WHERE d.ID_Doctor = ?
                    """
                            var idSucursal: Int? = null
                            conexion?.prepareStatement(obtenerSucursalQuery).use { statement ->
                                statement?.setInt(1, ID_Doctor)
                                val resultSet = statement?.executeQuery()
                                if (resultSet?.next() == true) {
                                    idSucursal = resultSet.getInt("ID_Sucursal")
                                }
                            }

                            // Recalcular el valoFinal llamando al procedimiento almacenado
                            idSucursal?.let {
                                conexion?.prepareCall("{CALL actualizar_valoFinal_sucursal(?)}").use { callable ->
                                    callable?.setInt(1, it)
                                    callable?.executeUpdate()
                                }
                            }

                            // Obtener el valoFinal recalculado
                            var valoFinalActualizado: Double? = null
                            idSucursal?.let {
                                val queryValoFinal = "SELECT valoFinal FROM tbSucursales WHERE ID_Sucursal = ?"
                                conexion?.prepareStatement(queryValoFinal).use { statement ->
                                    statement?.setInt(1, it)
                                    val resultSet = statement?.executeQuery()
                                    if (resultSet?.next() == true) {
                                        valoFinalActualizado = resultSet.getDouble("valoFinal")
                                    }
                                }
                            }

                            // Cargar las reseñas después de insertar
                            val review = obtenerDatosReviews(ID_Doctor)

                            withContext(Dispatchers.Main) {
                                // Actualizar la UI con el valoFinal recalculado
                                valoFinalActualizado?.let {
                                    txtRating.text = it.toString() // Actualiza el TextView del valoFinal
                                }

                                // Mostrar las reseñas
                                if (review.isNullOrEmpty()) {
                                    textViewError.visibility = View.VISIBLE
                                    rcvResenas.visibility = View.GONE
                                } else {
                                    textViewError.visibility = View.GONE
                                    val miAdapter = AdaptadorResenas(review)
                                    rcvResenas.adapter = miAdapter
                                    rcvResenas.visibility = View.VISIBLE
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                // Manejar el error
                                Log.e("Error", "Error al insertar la reseña o recalcular el valoFinal", e)
                            }
                        } finally {
                            conexion?.close()
                        }
                    }
                } else {
                    // Si el RatingBar tiene valor 0, mostrar mensaje de error
                    Toast.makeText(this, "Seleccione una calificación con las estrellas.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Si el campo de texto está vacío, mostrar mensaje de error
                txtReview.error = "Ingrese una reseña"
            }
        }

        //Función para actualizar valoFinal de sucursal
        CoroutineScope(Dispatchers.IO).launch {
            val conexion = ClaseConexion().cadenaConexion()

            // Primero ejecutamos el procedimiento para recalcular el valoFinal
            conexion?.prepareCall("{CALL actualizar_valoFinal_sucursal(?)}").use { callable ->
                callable?.setInt(1, ID_Sucursal)
                callable?.executeUpdate()
            }

            // Luego realizamos la consulta para obtener el valoFinal actualizado
            val query = "SELECT valoFinal FROM tbSucursales WHERE ID_Sucursal = ?"
            conexion?.prepareStatement(query).use { statement ->
                statement?.setInt(1, ID_Sucursal)
                val resultSet = statement?.executeQuery()

                if (resultSet?.next() == true) {
                    val valoFinalActualizado = resultSet.getDouble("valoFinal")

                    withContext(Dispatchers.Main) {
                        // Aquí puedes mostrar el valoFinal actualizado en la interfaz de usuario
                        Log.d("valoFinalActualizado", valoFinalActualizado.toString())
                        // Actualiza las vistas o realiza otras acciones necesarias
                    }
                }
            }
        }
    }

    //Funcion fuera del oncreate para información de las reseñas
    private suspend fun obtenerDatosReviews(ID_Doctor: Int): MutableList<dataClassResena> {
        val objConexion = ClaseConexion().cadenaConexion()!!
        val reseña = mutableListOf<dataClassResena>()
        if (objConexion != null) {
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
    d.ID_Doctor = ?
""")
                statement.setInt(1, ID_Doctor)
                val resultSet = statement.executeQuery()
                while (resultSet.next()) {
                    val comentario = resultSet.getString("comentario")
                    val promEstrellas = resultSet.getFloat("promEstrellas")
                    val nombreUsuario = resultSet.getString("nombreUsuario")
                    val apellidoUsuario = resultSet.getString("apellidoUsuario")
                    val imgUsuario = resultSet.getString("imgUsuario")
                    val valoresJuntos = dataClassResena(
                        comentario,
                        promEstrellas,
                        nombreUsuario,
                        apellidoUsuario,
                        imgUsuario,
                        ID_Doctor,
                        ID_User
                    )
                    reseña.add(valoresJuntos)
                }
            } catch (e: Exception) {
                Log.e("obtenerDatos", "Error al obtener datos: ${e.message}")
            }
        } else {
            Log.e("obtenerDatos", "No se pudo establecer una conexión con la base de datos.")
        }
        return reseña
    }

//Función fuera del oncreate para información de los servicios

    private suspend fun obtenerDatos(ID_Doctor: Int): MutableList<dataClassServicios> {
        val objConexion = ClaseConexion().cadenaConexion()!!
        val service = mutableListOf<dataClassServicios>()
        if (objConexion != null) {
            try {
                val statement = objConexion.prepareStatement(
                    """
SELECT
    srv.nombreServicio,
    srv.costo,
    a.nombreAseguradora,
    d.ID_Doctor
FROM 
    tbServicios srv
INNER JOIN 
    tbAseguradoras a ON srv.ID_Aseguradora = a.ID_Aseguradora
INNER JOIN
    tbDoctores d ON srv.ID_Doctor = d.ID_Doctor
WHERE 
    d.ID_Doctor = ?
""")
                statement.setInt(1, ID_Doctor)
                val resultSet = statement.executeQuery()
                while (resultSet.next()) {
                    val nombreServicio = resultSet.getString("nombreServicio")
                    val costo = resultSet.getFloat("costo")
                    val nombreAseguradora = resultSet.getString("nombreAseguradora")
                    val valoresJuntos =
                        dataClassServicios(nombreServicio, costo, nombreAseguradora, ID_Doctor)

                    service.add(valoresJuntos)
                }
            } catch (e: Exception) {
                Log.e("obtenerDatos", "Error al obtener datos: ${e.message}")
            }
        } else {
            Log.e("obtenerDatos", "No se pudo establecer una conexión con la base de datos.")
        }
        return service
    }

    //Funcion del mapa para mostrar ubicación exacta
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        if (latitud != 0.0 && longitud != 0.0) {
            print("$latitud Y $longitud")
            val location = LatLng(longitud, latitud)
            googleMap.addMarker(MarkerOptions().position(location).title("Ubicación de Sucursal"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        } else {
            Log.e("Map", "Latitud y longitud son cero")
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val mapViewBundle = Bundle()
        mapView.onSaveInstanceState(mapViewBundle)
        outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
    }
}