package delta.medic.mobile

import DiasAdapter
import Modelo.ClaseConexion
import Modelo.Dia
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.Year

class activity_agendar : AppCompatActivity() {

    var ID_User: Int = 0;
    var nombreDoctor : String = "";
    var apellidoDoctor : String = "";
    var imgDoctor : String = "";
    var nombreSucursal : String = "";
    var direccionSucur : String = "";
    var imgSucursal : String = "";
    var nombreEspecialidad : String = "";


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agendar)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val btnSelecSucursal = findViewById<ImageButton>(R.id.btnSelecSucursal)
        val txtFecha = findViewById<TextView>(R.id.txtFecha)
        val rcvPaciente = findViewById<RecyclerView>(R.id.rcvPaciente)
        val rcvDisponibilidad = findViewById<RecyclerView>(R.id.rcvDisponibilidad)
        var imgFotoSucursal = findViewById<ImageView>(R.id.imgSucursal)
        val imgFotoDoctor = findViewById<ImageView>(R.id.imgFotoDoctor)
        val txtNombreDoctor = findViewById<TextView>(R.id.txtNombreDoctor)
        val txtEspecialidad = findViewById<TextView>(R.id.txtEspecialidad)
        val txtDireccionSucur = findViewById<TextView>(R.id.txtDireccionSucur)
        val txtEsTar = findViewById<TextView>(R.id.txtEsTar)
        val txtEsMañ = findViewById<TextView>(R.id.txtEsMañ)
        val txtMot = findViewById<TextView>(R.id.txtMot)
        val txtPaciente = findViewById<TextView>(R.id.txtPaciente)
        val rcvEspaciosT = findViewById<RecyclerView>(R.id.rcvEspaciosT)
        val rcvEspaciosM = findViewById<RecyclerView>(R.id.rcvEspaciosM)


        /*
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.black))
                btnContinuar.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                btnSelecSucursal.setColorFilter(ContextCompat.getColor(this, R.color.black))
                txtFecha.setTextColor(ContextCompat.getColor(this, R.color.black))
                rcvPaciente.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                rcvDisponibilidad.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                imgFotoSucursal.setColorFilter(ContextCompat.getColor(this, R.color.black))
                imgFotoDoctor.setColorFilter(ContextCompat.getColor(this, R.color.black))
                txtNombreDoctor.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtEspecialidad.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtDireccionSucur.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtEsTar.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtEsMañ.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtMot.setTextColor(ContextCompat.getColor(this, R.color.black))
                txtPaciente.setTextColor(ContextCompat.getColor(this, R.color.black))
                rcvEspaciosT.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                rcvEspaciosM.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            } // Night mode is not active, we're using the light theme.
            Configuration.UI_MODE_NIGHT_YES -> {
                btnRegresar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                btnContinuar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                btnSelecSucursal.setColorFilter(ContextCompat.getColor(this, R.color.white))
                txtFecha.setTextColor(ContextCompat.getColor(this, R.color.white))
                rcvPaciente.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                rcvDisponibilidad.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                imgFotoSucursal.setColorFilter(ContextCompat.getColor(this, R.color.white))
                imgFotoDoctor.setColorFilter(ContextCompat.getColor(this, R.color.white))
                txtNombreDoctor.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtEspecialidad.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtDireccionSucur.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtEsTar.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtEsMañ.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtMot.setTextColor(ContextCompat.getColor(this, R.color.white))
                txtPaciente.setTextColor(ContextCompat.getColor(this, R.color.white))
                rcvEspaciosT.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                rcvEspaciosM.setBackgroundColor(ContextCompat.getColor(this, R.color.black))

            } // Night mode is active, we're using dark theme.
        }
         */

        val diasDelAno = obtenerDiasDelAno(Year.now().value)
        rcvDisponibilidad.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcvDisponibilidad.adapter = DiasAdapter(diasDelAno) { diaSeleccionado ->
            actualizarHorarios(diaSeleccionado)
            }


        rcvPaciente.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        CoroutineScope(Dispatchers.IO).launch {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement =
                objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE emailUsuario = ?")!!
            statement.setString(1, userEmail)
            val resultSet = statement?.executeQuery()
            withContext(Dispatchers.Main) {
                if (resultSet!!.next()) {
                    ID_User = resultSet.getInt("ID_Usuario")
                }
            }
        }

        val ID_Doctor = intent.getIntExtra("ID_Doctor", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.prepareStatement(
                """
SELECT
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
            """)
            statement?.setInt(1, ID_Doctor)
            val resultSet = statement?.executeQuery()
            withContext(Dispatchers.Main) {
                if (resultSet!!.next()) {
                    nombreDoctor = resultSet.getString("nombreUsuario")
                    apellidoDoctor = resultSet.getString("apellidoUsuario")
                    val nombreDoctor = "Dr. ${nombreDoctor ?: ""} ${apellidoDoctor ?: ""}".trim()
                    txtNombreDoctor.text = nombreDoctor
                    imgDoctor = resultSet.getString("imgUsuario")
                    Glide.with(imgFotoDoctor)
                        .load(imgDoctor)
                        .circleCrop()
                        .into(imgFotoDoctor)
                    imgSucursal = resultSet.getString("imgSucursal")
                    Glide.with(imgFotoSucursal)
                        .load(imgSucursal)
                        .into(imgFotoSucursal)
                    direccionSucur = resultSet.getString("direccionSucur")
                    txtDireccionSucur.text = direccionSucur
                    nombreEspecialidad = resultSet.getString("nombreEspecialidad")
                    txtEspecialidad.text = nombreEspecialidad
                }
                else{

                }

            }
        }

        btnSelecSucursal.setOnClickListener {
            val intent = Intent(this, activity_busqueda::class.java)
            startActivity(intent)
        }

        val lbAgendarCita = findViewById<TextView>(R.id.lbAgendarCita)
        lbAgendarCita.setText(Html.fromHtml(getResources().getString(R.string.subrayado)))
        val cardDoctor = findViewById<CardView>(R.id.linearCard)
        val linearNoDoctor = findViewById<LinearLayout>(R.id.linearNoDoctor)
        val doctorExists = checkIfDoctorExists()

        if (doctorExists) {
            linearNoDoctor.visibility = View.GONE
            cardDoctor.visibility = View.VISIBLE
        } else {
            linearNoDoctor.visibility = View.VISIBLE
            cardDoctor.visibility = View.GONE
        }

        btnRegresar.setOnClickListener {
            finish()
        }

        btnContinuar.setOnClickListener {
            showCustomDialog()
        }
    }

    private fun checkIfDoctorExists(): Boolean {
        val DoctorExist = intent.getBooleanExtra("DoctorExist", false)
        return DoctorExist
    }

    private fun infoDoctor(){

    }

    private fun showCustomDialog() {
        val dialog = Dialog(this, R.style.CustomDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_customizado)

        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(window.attributes)
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = layoutParams
        }

        val closeButton = dialog.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            finish()
        }

        val editButton = dialog.findViewById<TextView>(R.id.editButton)
        editButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun obtenerDiasDelAno(ano: Int): List<Dia> {
        val primerDia = LocalDate.of(ano, 1, 1)
        val ultimoDia = LocalDate.of(ano, 12, 31)
        return primerDia.datesUntil(ultimoDia.plusDays(1)).map { Dia(it) }.toList()
    }

    private fun actualizarHorarios(dia: Dia) {
        Log.e("Info","Actualizando horarios para el día: ${dia.fecha}")
    }
}