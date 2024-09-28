package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassCentro
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
    var ID_User: Int = 0
    var nombreUser: String = ""
    var apellidoUser: String = ""
    var imgUser: String = ""
    var ID_Sucursal : Int = 0
    var isFav: Boolean = false

    private lateinit var adaptadorResenas: AdaptadorResenas
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var doctorInfo: dataClassCentro? = null

    companion object {
        private const val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    suspend fun getData(
        ID_Doctor: Int,
        userEmail: String,
        nombreSucursal: TextView,
        numeroClinica: TextView,
        direccion_Clinica: TextView,
        nombreDoctor: TextView,
        Especialidad: TextView,
        imgDoctor: ShapeableImageView,
        toggleButton: ToggleButton
    ): dataClassCentro? {
        val conexion = ClaseConexion().cadenaConexion() ?: return null
        return try {
            conexion.prepareStatement(
                """
            SELECT 
    (SELECT ID_Usuario FROM tbUsuarios WHERE emailUsuario = ?) AS ID_Usuario,
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
    se.nombreServicio,
    se.costo
FROM 
    tbDoctores d
INNER JOIN tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
INNER JOIN tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
INNER JOIN tbSucursales s ON d.ID_Sucursal = s.ID_Sucursal
INNER JOIN tbServicios se ON d.ID_Doctor = se.ID_Doctor
WHERE 
    d.ID_Doctor = ?
            """
            ).use { statement ->
                statement.setString(1, userEmail)
                statement.setInt(2, ID_Doctor)
                statement.executeQuery().use { resultSet ->
                    var doctorInfo: dataClassCentro? = null
                    if (resultSet.next()) {
                        doctorInfo = dataClassCentro(
                            latiSucur = resultSet.getDouble("latiSucur"),
                            longSucur = resultSet.getDouble("longSucur"),
                            imgUsuario = resultSet.getString("imgUsuario"),
                            imgSucursal = resultSet.getString("imgSucursal"),
                            ID_Sucursal = resultSet.getInt("ID_Sucursal"),
                            ID_Usuario = resultSet.getInt("ID_Usuario"),
                            nombreUsuario = resultSet.getString("nombreUsuario"),
                            apellidoUsuario = resultSet.getString("apellidoUsuario"),
                            nombreEspecialidad = resultSet.getString("nombreEspecialidad"),
                            nombreSucursal = resultSet.getString("nombreSucursal"),
                            telefonoSucur = resultSet.getString("telefonoSucur"),
                            direccionSucur = resultSet.getString("direccionSucur"),
                            ID_Doctor = ID_Doctor
                        )
                        withContext(Dispatchers.Main) {
                            latitud = doctorInfo.latiSucur
                            longitud = doctorInfo.longSucur
                            nombreSucursal.text = doctorInfo.nombreSucursal
                            numeroClinica.text = doctorInfo.telefonoSucur
                            direccion_Clinica.text = doctorInfo.direccionSucur
                            val nombreCompleto =
                                "Dr. ${doctorInfo.nombreUsuario} ${doctorInfo.apellidoUsuario}".trim()
                            nombreDoctor.text = nombreCompleto
                            Especialidad.text = doctorInfo.nombreEspecialidad
                        }

                        isFav = getFavStatus(userEmail, ID_Doctor, doctorInfo.ID_Sucursal)
                        validarRecientes(userEmail, doctorInfo.ID_Sucursal, doctorInfo.ID_Doctor)
                        withContext(Dispatchers.Main) {
                            println("${doctorInfo.ID_Sucursal} ${doctorInfo.ID_Usuario} $ID_Doctor $isFav")
                            updateToggleButton(toggleButton, isFav)
                        }
                    }
                    doctorInfo
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Log.e("Err get data doctor", e.message.toString())
            }
            null
        } finally {
            conexion.close()
        }
    }


    suspend fun getFavStatus(userEmail: String, ID_Doctor: Int, ID_Sucursal: Int): Boolean {
        // Lógica para obtener el estado del favorito
        val conexion = ClaseConexion().cadenaConexion()
        var isFav = false

        try {
            conexion?.prepareStatement(
                "SELECT COUNT(*) FROM tbFavoritos WHERE ID_Usuario = (SELECT ID_Usuario FROM tbUsuarios WHERE emailUsuario = ?) AND ID_Doctor = ? AND ID_Sucursal = ?"
            )?.use { statement ->
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
            conexion?.close()
        }

        println("getFavStatus result: $isFav")
        return isFav
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    suspend fun validarRecientes(email: String, ID_Sucursal: Int, ID_Doctor: Int){
        try {
            val objConexion = ClaseConexion().cadenaConexion()
            objConexion?.prepareCall("{CALL PROC_STATE_VALIDATION_RECIENTES(?,?,?)}")
                ?.use { validation ->
                    validation.setString(1, email)
                    validation.setInt(2, ID_Sucursal)
                    validation.setInt(3, ID_Doctor)
                    validation.execute()
                }
        } catch (e: Exception) {
            println("Error: $e")
        }
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

        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val nombreDoctor = findViewById<TextView>(R.id.nombreDoctor)
        val Especialidad = findViewById<TextView>(R.id.Especialidad)
        val nombreSucursal = findViewById<TextView>(R.id.nombreSucursal)
        val numeroClinica = findViewById<TextView>(R.id.numeroClinica)
        val direccion_Clinica = findViewById<TextView>(R.id.direccion_Clinica)
        val img_clinic = findViewById<ImageView>(R.id.img_clinic)
        val imgDoctor = findViewById<ShapeableImageView>(R.id.imgDoctor)
        val toggleButton = findViewById<ToggleButton>(R.id.toggleButton)
        val button_reservar = findViewById<TextView>(R.id.button_reservar)
        val btnSubir = findViewById<Button>(R.id.btnSubir)
        val RatingBar = findViewById<RatingBar>(R.id.ratingBar2)
        val txtReview = findViewById<TextView>(R.id.txtReview)
        val numeroWha = findViewById<TextView>(R.id.numeroWha)
        val txtRating = findViewById<TextView>(R.id.txtRating)

        var imgSucursal: String = "";
        var nombreUsuario: String = "";
        var apellidoUsuario: String = "";
        var imgUsuario: String = "";

        var ID_Doctor = intent.getIntExtra("ID_Doctor", 0)
        var DoctorExist: Boolean = true
        Log.e("ID_Doctor", ID_Doctor.toString())

        button_reservar.setOnClickListener {
            val intent = Intent(this, activity_agendar::class.java)
            intent.putExtra("ID_Doctor", ID_Doctor)
            intent.putExtra("DoctorExist", DoctorExist)
            startActivity(intent)
        }

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

        CoroutineScope(Dispatchers.IO).launch {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement =
                objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE emailUsuario = ?")!!
            statement.setString(1, userEmail)
            val resultSet = statement?.executeQuery()
            withContext(Dispatchers.Main) {
                if (resultSet!!.next()) {
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
        }

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
    s.valoFinal,
    se.nombreServicio,
    se.costo
FROM 
    tbDoctores d
INNER JOIN tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
INNER JOIN tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
INNER JOIN tbSucursales s ON d.ID_Sucursal = s.ID_Sucursal
INNER JOIN tbServicios se ON d.ID_Doctor = se.ID_Doctor
WHERE 
    u.emailUsuario = ? AND d.ID_Doctor = ?
        """
            )!!
            statement.setString(1, userEmail)
            statement.setInt(2, ID_Doctor)
            val resultSet = statement.executeQuery()
            withContext(Dispatchers.Main) {
                if (resultSet.next()) {
                    imgSucursal = resultSet.getString("imgSucursal")
                    imgUsuario = resultSet.getString("imgUsuario")
                    Glide.with(imgDoctor)
                        .load(imgUsuario)
                        .circleCrop()
                        .into(imgDoctor)
                    Glide.with(img_clinic)
                        .load(imgSucursal)
                        .into(img_clinic)
                    val wsp = resultSet.getString("whatsapp")
                    numeroWha.text = wsp
                    Log.e("wsp", wsp)
                    val valoFinal = resultSet.getDouble("valoFinal")
                    txtRating.text = valoFinal.toString()
                    nombreUsuario = resultSet.getString("nombreUsuario")
                    apellidoUsuario = resultSet.getString("apellidoUsuario")
                    ID_Sucursal = resultSet.getInt("ID_Sucursal")
                    Log.e("ID_Sucursal", "$ID_Sucursal")
                    val nombreCompleto =
                        "Dr. ${nombreUsuario ?: ""} ${apellidoUsuario ?: ""}".trim()
                    nombreDoctor.text = nombreCompleto
                    Especialidad.text = resultSet.getString("nombreEspecialidad")
                }
            }
        }

        val textViewError = findViewById<TextView>(R.id.lblNoComments)
        val rcvResenas = findViewById<RecyclerView>(R.id.rcvResenas)
        rcvResenas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
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

        CoroutineScope(Dispatchers.IO).launch {
            val doctorInfo = getData(
                ID_Doctor,
                userEmail,
                nombreSucursal,
                numeroClinica,
                direccion_Clinica,
                nombreDoctor,
                Especialidad,
                imgDoctor,
                toggleButton
            )
        }

        mapView = findViewById(R.id.mapUbicacion)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        btnRegresar.setOnClickListener {
            finish()
        }

        toggleButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val conexion = ClaseConexion().cadenaConexion()
                val favStatus = if (this@activity_vistadoctores.isFav) {
                    "T"
                } else {
                    "F"
                }

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
                        toggleButton.background =
                            getDrawable(R.drawable.corazon_vacio) // Cambia a icono de no favorito
                    } else {
                        isFav = true
                        toggleButton.background =
                            getDrawable(R.drawable.corazon_favoritos) // Cambia a icono de favorito
                    }
                }
            }
        }

        adaptadorResenas = AdaptadorResenas(mutableListOf())
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
                    rcvResenas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

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

    ////////////////////////////Funcion para información de las reseñas//////////////////////////////////
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

///////////////////////////Función para información de los servicios//////////////////////////////////

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

    ///////////////////////////////Funcion del mapa para ubicación//////////////////////////////////////
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