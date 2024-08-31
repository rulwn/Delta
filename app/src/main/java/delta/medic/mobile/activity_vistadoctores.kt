package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassDoctorInfo
import Modelo.dataClassResena
import Modelo.dataClassServicios
import Modelo.dataClassUsuario
import RecycleViewHelper.AdaptadorCentro
import RecycleViewHelper.AdaptadorFavoritos
import RecycleViewHelper.AdaptadorResenas
import RecycleViewHelper.AdaptadorServicios
import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.google.android.material.imageview.ShapeableImageView
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.CallableStatement
import java.sql.ResultSet
import java.sql.SQLException

class activity_vistadoctores : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    suspend fun getFavStatus(email: String, ID_Doctor: Int, ID_Sucursal: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.prepareStatement(
                "SELECT * FROM TBFAVORITOS WHERE ID_Usuario = (SELECT ID_Usuario FROM tbUsuarios WHERE emailUsuario = ?) AND ID_Doctor = ? AND ID_Sucursal = ?"
            )
            statement?.setString(1, email)
            statement?.setInt(2, ID_Doctor)
            statement?.setInt(3, ID_Sucursal)
            val resultSet = statement?.executeQuery()

            resultSet?.next() ?: false
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


        var ID_Doctor = intent.getIntExtra("ID_Doctor", 0)
        Log.e("ID_Doctor", ID_Doctor.toString())


        val bundle = intent.extras
        var idUsuario = bundle?.getInt("idUsuario")
        var idSucursal = bundle?.getInt("idSucursal")


        val rcvServicios = findViewById<RecyclerView>(R.id.rcvServicios)
        rcvServicios.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        CoroutineScope(Dispatchers.IO).launch {
            val centrosDB = obtenerDatos(ID_Doctor)
            withContext(Dispatchers.Main) {
                val miAdapter = AdaptadorServicios(centrosDB)
                rcvServicios.adapter = miAdapter
            }
        }

        val textViewError = findViewById<TextView>(R.id.lblNoComments)
        val rcvResenas = findViewById<RecyclerView>(R.id.rcvResenas)
        rcvResenas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        CoroutineScope(Dispatchers.Main).launch {
            println("$userEmail $ID_Doctor $idSucursal")
            val isFavorite = getFavStatus(userEmail, ID_Doctor, idSucursal!!)

            if (isFavorite) {
                toggleButton.background = getDrawable(R.drawable.corazon_favoritos)
                toggleButton.isChecked = true
            } else {
                toggleButton.background = getDrawable(R.drawable.corazon_vacio)
                toggleButton.isChecked = false
            }
        }



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

        button_reservar.setOnClickListener {
            val intent = Intent(this, activity_agendar::class.java)
            intent.putExtra("ID_Doctor", ID_Doctor)
            startActivity(intent)
        }

        //Aquí se obtienen los datos por medio del ID_Doctor
        rcvServicios.layoutManager = LinearLayoutManager(this)
//Aquí se obtienen los datos por medio del ID_Doctor
        CoroutineScope(Dispatchers.IO).launch {
            val conexion = ClaseConexion().cadenaConexion()
            val statement = conexion?.prepareStatement(
                """
            SELECT 
                d.ID_Doctor,
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
                se.nombreServicio,
                se.costo
            FROM 
                tbDoctores d
            INNER JOIN tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
            INNER JOIN tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
            INNER JOIN tbSucursales s ON d.ID_Sucursal = s.ID_Sucursal
            INNER JOIN tbCentrosMedicos cm ON d.ID_Doctor = cm.ID_Doctor
            INNER JOIN tbServicios se ON cm.ID_Centro = se.ID_Centro
            WHERE 
                d.ID_Doctor = ?
            """
            )!!
            statement.setInt(1, ID_Doctor)
            val resultSet = statement.executeQuery()
            withContext(Dispatchers.Main) {
                while (resultSet.next()) {
                    //latitud = resultSet.getDouble("latiSucur")
                    //longitud = resultSet.getDouble("longSucur")
                    nombreSucursal.text = resultSet.getString("nombreSucursal")
                    numeroClinica.text = resultSet.getString("telefonoSucur")
                    direccion_Clinica.text = resultSet.getString("direccionSucur")
                    //imgSucursal = resultSet.getString("imgSucursal")
                    //imgUsuario = resultSet.getString("imgUsuario")
                    //nombreUsuario = resultSet.getString("nombreUsuario")
                    //apellidoUsuario = resultSet.getString("apellidoUsuario")
                    idUsuario = resultSet.getInt("ID_Usuario")
                    idSucursal = resultSet.getInt("ID_Sucursal")
                    //val nombreCompleto = "Dr. ${nombreUsuario ?: ""} ${apellidoUsuario ?: ""}".trim()
                    //nombreDoctor.text = nombreCompleto
                    Especialidad.text = resultSet.getString("nombreEspecialidad")

                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val centrosDB = obtenerDatos(ID_Doctor)
            withContext(Dispatchers.Main) {
                val miAdapter = AdaptadorServicios(centrosDB)
                rcvServicios.adapter = miAdapter
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            println("$userEmail $ID_Doctor $idSucursal")
            val isFavorite = getFavStatus(userEmail, ID_Doctor, idSucursal!!)

            if (isFavorite) {
                toggleButton.background = getDrawable(R.drawable.corazon_favoritos)
                toggleButton.isChecked = true
            } else {
                toggleButton.background = getDrawable(R.drawable.corazon_vacio)
                toggleButton.isChecked = false
            }
        }



        CoroutineScope(Dispatchers.IO).launch {
            val centrosDB = obtenerDatosReviews(ID_Doctor)
            withContext(Dispatchers.Main) {
                if (centrosDB.isNullOrEmpty()) {
                    /*
                    textViewError.visibility = View.VISIBLE
                    rcvResenas.visibility = View.GONE*/
                } else {

                    //textViewError.visibility = View.GONE
                    val miAdapter = AdaptadorResenas(centrosDB)
                    rcvResenas.adapter = miAdapter
                    rcvResenas.visibility = View.VISIBLE
                    //textViewError.visibility = View.GONE
                }
            }
        }

        button_reservar.setOnClickListener {
            val intent = Intent(this, activity_agendar::class.java)
            intent.putExtra("ID_Doctor", ID_Doctor)
            startActivity(intent)
        }




        CoroutineScope(Dispatchers.IO).launch {
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                val idUser = userEmail
                objConexion?.prepareCall("{CALL PROC_STATE_VALIDATION_RECIENTES(?,?)}")
                    ?.use { validation ->
                        validation.setString(1, idUser)
                        validation.setInt(2, idSucursal!!)
                        validation.execute()
                    }
            } catch (e: Exception) {
                println("Error: $e")
            }
        }
/*
        gitCoroutineScope(Dispatchers.IO).launch {
            val doctorInfo = obtenerInfoDoctor(ID_Doctor)
            withContext(Dispatchers.Main) {
                if (doctorInfo != null) {
                    nombreDoctor.text = "${doctorInfo.nombreUsuario} ${doctorInfo.apellidoUsuario}"
                    Especialidad.text = doctorInfo.nombreEspecialidad
                    nombreSucursal.text = doctorInfo.nombreSucursal
                    numeroClinica.text = doctorInfo.telefonoSucur
                    direccion_Clinica.text = doctorInfo.direccionSucur

                    Glide.with(this@activity_vistadoctores)
                        .load(doctorInfo.imgUsuario)
                        .circleCrop()
                        .into(imgDoctor)

                    Glide.with(this@activity_vistadoctores)
                        .load(doctorInfo.imgSucursal)
                        .into(img_clinic)
                }
            }
        }
*/
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
            if (!toggleButton.isChecked) {
                CoroutineScope(Dispatchers.IO).launch {
                    val conexion = ClaseConexion().cadenaConexion()
                    val statement = conexion?.prepareStatement(
                        "INSERT INTO TBFAVORITOS(ID_Sucursal, ID_Doctor, ID_Usuario)\n" +
                                "    VALUES (?,?,(SELECT ID_Usuario FROM tbUsuarios WHERE emailUsuario = ?))"
                    )
                    statement?.setInt(1, idSucursal!!)
                    statement?.setInt(2, ID_Doctor)
                    statement?.setString(3, userEmail)
                    statement?.executeUpdate()
                    conexion?.commit()
                    statement?.close()
                    withContext(Dispatchers.Main) {
                        toggleButton.background = getDrawable(R.drawable.corazon_favoritos)
                        toggleButton.isChecked = true
                    }
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val conexion = ClaseConexion().cadenaConexion()
                    if (conexion != null) {
                        try {
                            conexion.prepareCall("{CALL PROC_DELT_FAVORITOS(?,?,?)}")
                                .use { eliminarFavorito ->
                                    eliminarFavorito.setString(1, userEmail)
                                    eliminarFavorito.setInt(2, ID_Doctor)
                                    eliminarFavorito.setInt(3, idSucursal!!)
                                    eliminarFavorito.executeUpdate()
                                    withContext(Dispatchers.Main) {
                                        toggleButton.background =
                                            getDrawable(R.drawable.corazon_vacio)
                                        toggleButton.isChecked = false
                                    }
                                }

                        } catch (e: Exception) {
                            println("Error en activity_vistadoctores: $e")
                        }

                    }


                }

            }
        }



    }

////////////////////////////Funcion para información de las reseñas//////////////////////////////////
    private suspend fun obtenerDatosReviews(ID_Doctor : Int): MutableList<dataClassResena> {
        val objConexion = ClaseConexion().cadenaConexion()!!
        val reseña = mutableListOf<dataClassResena>()
        if (objConexion != null) {
            try {
                val statement = objConexion.prepareStatement("""
SELECT
    rv.comentario,
    rv.promEstrellas,
    u.nombreUsuario,
    u.apellidoUsuario,
    u.imgUsuario,
    d.ID_Doctor
FROM 
    tbReviews rv
INNER JOIN 
    tbUsuarios u ON rv.ID_Usuario = u.ID_Usuario
INNER JOIN
    tbCentrosMedicos cm ON rv.ID_Centro = cm.ID_Centro
INNER JOIN
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
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
                    val valoresJuntos = dataClassResena(comentario, promEstrellas, nombreUsuario, apellidoUsuario, imgUsuario, ID_Doctor)
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

    private suspend fun obtenerDatos(ID_Doctor : Int): MutableList<dataClassServicios> {
        val objConexion = ClaseConexion().cadenaConexion()!!
        val service = mutableListOf<dataClassServicios>()
        if (objConexion != null) {
            try {
                val statement = objConexion.prepareStatement("""
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
    tbCentrosMedicos cm ON srv.ID_Centro = cm.ID_Centro
INNER JOIN
    tbDoctores d ON cm.ID_Doctor = d.ID_Doctor
WHERE 
    d.ID_Doctor = ?
""")
                statement.setInt(1, ID_Doctor)
                val resultSet = statement.executeQuery()
                while (resultSet.next()) {
                    val nombreServicio = resultSet.getString("nombreServicio")
                    val costo = resultSet.getFloat("costo")
                    val nombreAseguradora = resultSet.getString("nombreAseguradora")
                    val valoresJuntos = dataClassServicios(nombreServicio, costo, nombreAseguradora, ID_Doctor)

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
        val latitudDB = intent.getDoubleExtra("latiSucur", 0.0)
        val longitudDB = intent.getDoubleExtra("longSucur", 0.0)
        println("$latitudDB y $longitudDB")
        val location = LatLng(latitudDB, longitudDB)

        this.googleMap.addMarker(MarkerOptions().position(location).title("Esta es la ubicación de la sucursal"))
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
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