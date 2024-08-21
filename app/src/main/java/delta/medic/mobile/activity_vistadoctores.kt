package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.Encrypter
import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vistadoctores)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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

        val ID_Doctor = intent.getIntExtra("ID_Doctor", 0)
        val nombreUsuario = intent.getStringExtra("nombreUsuario")
        val apellidoUsuario = intent.getStringExtra("apellidoUsuario")
        val nombreCompleto = "Dr. $nombreUsuario " + "$apellidoUsuario"
        Log.e("Apellido", "$apellidoUsuario")
        nombreDoctor.text = nombreCompleto
        Especialidad.text = intent.getStringExtra("nombreEspecialidad")
        nombreSucursal.text = intent.getStringExtra("nombreSucursal")
        numeroClinica.text = intent.getStringExtra("telefonoSucur")
        direccion_Clinica.text = intent.getStringExtra("direccionSucur")

        var ubicacionSucur = intent.getStringExtra("ubicacionSucur")
        var imgSucursal = intent.getStringExtra("imgSucursal")
        var imgUsuario = intent.getStringExtra("imgUsuario")

        val bundle = intent.extras
        val idUsuario = bundle?.getInt("idUsuario")
        val idSucursal = bundle?.getInt("idSucursal")
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val callableStatement: CallableStatement = objConexion.prepareCall("{CALL PROC_STATE_VALIDATION_RECIENTES(?, ?)}")
                        // Establecer los parámetros del procedimiento
                        //callableStatement.setInt(1, idUsuario)
                        //callableStatement.setInt(2, idSucursal)

                        // Ejecutar el procedimiento y procesar el resultado
                        val resultSet: ResultSet = callableStatement.executeQuery()
                        while (resultSet.next()) {
                            val someValue = resultSet.getString("some_column_name")
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

        val mapViewBundle: Bundle? = savedInstanceState?.getBundle(MAP_VIEW_BUNDLE_KEY)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        btnRegresar.setOnClickListener {
            finish()
        }
    }

//funciones fuera
    suspend fun EjecutarProcesoAlmacenado(idUsuario: Int, idSucursal: String) {
        withContext(Dispatchers.IO){

        }
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