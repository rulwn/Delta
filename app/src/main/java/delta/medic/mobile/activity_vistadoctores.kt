package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.Encrypter
import Modelo.dataClassCentro
import Modelo.dataClassServicios
import RecycleViewHelper.AdaptadorCentro
import RecycleViewHelper.AdaptadorFavoritos
import RecycleViewHelper.AdaptadorServicios
import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
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

class activity_vistadoctores : AppCompatActivity(), OnMapReadyCallback {

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
        var btnRegresar = findViewById<ImageView>(R.id.btnRegresar)

        var nombreDoctor = findViewById<TextView>(R.id.nombreDoctor)
        var Especialidad = findViewById<TextView>(R.id.Especialidad)
        var nombreSucursal = findViewById<TextView>(R.id.nombreSucursal)
        var numeroClinica = findViewById<TextView>(R.id.numeroClinica)
        var direccion_Clinica = findViewById<TextView>(R.id.direccion_Clinica)
        var img_clinic = findViewById<ImageView>(R.id.img_clinic)
        var imgDoctor = findViewById<ShapeableImageView>(R.id.imgDoctor)
        var toggleButton = findViewById<ToggleButton>(R.id.toggleButton)
        val button_reservar = findViewById<TextView>(R.id.button_reservar)

        if (intent.getBooleanExtra("Fav", false)) {
            toggleButton.isChecked = true
        } else {
            toggleButton.isChecked = false
        }

        var ID_Doctor = intent.getIntExtra("ID_Doctor", 0)
        var latitud = intent.getDoubleExtra("latiSucur", 0.0)
        var longitud = intent.getDoubleExtra("longSucur", 0.0)
        var nombreUsuario = intent.getStringExtra("nombreUsuario")
        var apellidoUsuario = intent.getStringExtra("apellidoUsuario")
        var nombreCompleto = "Dr. ${nombreUsuario ?: ""}${apellidoUsuario ?: ""}"
        Log.e("Apellido", "$apellidoUsuario")
        nombreDoctor.text = nombreCompleto
        Especialidad.text = intent.getStringExtra("nombreEspecialidad")
        nombreSucursal.text = intent.getStringExtra("nombreSucursal")
        numeroClinica.text = intent.getStringExtra("telefonoSucur")
        direccion_Clinica.text = intent.getStringExtra("direccionSucur")

        var imgSucursal = intent.getStringExtra("imgSucursal")
        var imgUsuario = intent.getStringExtra("imgUsuario")

        val bundle = intent.extras
        var idUsuario = bundle?.getInt("idUsuario")
        var idSucursal = bundle?.getInt("idSucursal")
        val rcvServicios = findViewById<RecyclerView>(R.id.rcvServicios)
        rcvServicios.layoutManager = LinearLayoutManager(this)
        CoroutineScope(Dispatchers.IO).launch {
            val centrosDB = obtenerDatos(ID_Doctor)
            withContext(Dispatchers.Main) {
                val miAdapter = AdaptadorServicios(centrosDB)
                rcvServicios.adapter = miAdapter
            }
        }

        button_reservar.setOnClickListener {
            val intent = Intent(this, activity_agendar::class.java)
            intent.putExtra("ID_Doctor", ID_Doctor)
            startActivity(intent)
        }

        //Aquí se obtienen los datos por medio del ID_Doctor
        CoroutineScope(Dispatchers.IO).launch {
            val conexion = ClaseConexion().cadenaConexion()
            val statement = conexion?.prepareStatement(
                "SELECT \n" +
                        "    d.ID_Doctor,\n" +
                        "    u.ID_Usuario,\n" +
                        "    u.nombreUsuario, \n" +
                        "    u.apellidoUsuario, \n" +
                        "    u.imgUsuario, \n" +
                        "    e.nombreEspecialidad,\n" +
                        "    s.ID_Sucursal,\n" +
                        "    s.nombreSucursal, \n" +
                        "    s.telefonoSucur, \n" +
                        "    s.direccionSucur, \n" +
                        "    s.longSucur, \n" +
                        "    s.latiSucur, \n" +
                        "    s.imgSucursal, \n" +
                        "    se.nombreServicio, \n" +
                        "    se.costo\n" +
                        "FROM \n" +
                        "    tbDoctores d\n" +
                        "    INNER JOIN tbUsuarios u ON d.ID_Usuario = u.ID_Usuario\n" +
                        "    INNER JOIN tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad\n" +
                        "    INNER JOIN tbSucursales s ON d.ID_Sucursal = s.ID_Sucursal\n" +
                        "    INNER JOIN tbCentrosMedicos cm ON d.ID_Doctor = cm.ID_Doctor\n" +
                        "    INNER JOIN tbServicios se ON cm.ID_Centro = se.ID_Centro\n" +
                        "WHERE \n" +
                        "    d.ID_Doctor = ?"
            )!!
            statement.setInt(1, ID_Doctor)
            val resultSet = statement.executeQuery()
            withContext(Dispatchers.Main) {
                while (resultSet.next()) {
                    latitud = resultSet.getDouble("latiSucur")
                    longitud = resultSet.getDouble("longSucur")
                    nombreSucursal.text = resultSet.getString("nombreSucursal")
                    numeroClinica.text = resultSet.getString("telefonoSucur")
                    direccion_Clinica.text = resultSet.getString("direccionSucur")
                    imgSucursal = resultSet.getString("imgSucursal")
                    imgUsuario = resultSet.getString("imgUsuario")
                    nombreUsuario = resultSet.getString("nombreUsuario")
                    apellidoUsuario = resultSet.getString("apellidoUsuario")
                    idUsuario = resultSet.getInt("ID_Usuario")
                    idSucursal = resultSet.getInt("ID_Sucursal")
                    nombreCompleto = "Dr. ${nombreUsuario ?: ""}${apellidoUsuario ?: ""}"
                    nombreDoctor.text = nombreCompleto
                    Especialidad.text = resultSet.getString("nombreEspecialidad")

                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    objConexion.prepareCall("{CALL PROC_STATE_VALIDATION_RECIENTES(?,?)")
                        .use { validation ->
                            validation.setInt(1, ID_Doctor)
                            validation.setInt(2, idUsuario!!)
                            validation.execute()
                        }
                }
            } catch (e: Exception) {
                println("Error: $e")
            }
        }
        Glide.with(this)
            .load(imgSucursal)
            .into(img_clinic)

        Glide.with(this)
            .load(imgUsuario)
            .circleCrop()
            .into(imgDoctor)

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

//funciones fuera
    suspend fun EjecutarProcesoAlmacenado(idUsuario: Int, idSucursal: String) {
        withContext(Dispatchers.IO){

        }
    }

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