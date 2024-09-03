package delta.medic.mobile

import RecycleViewHelper.DiasAdapter
import Modelo.ClaseConexion
import Modelo.Dia
import RecycleViewHelper.AdaptadorHorarios
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import delta.medic.mobile.activity_login.UserData.userEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Year
import java.time.format.DateTimeFormatter

class activity_agendar : AppCompatActivity() {

    var ID_Centro: Int = 0
    var ID_User: Int = 0
    var nombreDoctor : String = ""
    var apellidoDoctor : String = ""
    var imgDoctor : String = ""
    var direccionSucur : String = ""
    var imgSucursal : String = ""
    var nombreEspecialidad : String = ""
    val horarioMatutino = arrayOf(
        "08:00 AM", "08:30 AM", "09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM",
        "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "01:00 PM", "01:30 PM", "02:00 PM")

    val horarioVespertino = arrayOf(
        "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM", "04:30 PM",
        "05:00 PM", "05:30 PM", "06:00 PM", "06:30 PM", "07:00 PM", "07:30 PM", "08:00 PM")

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
        val rcvDisponibilidad = findViewById<RecyclerView>(R.id.rcvDisponibilidad)
        var imgFotoSucursal = findViewById<ImageView>(R.id.imgSucursal)
        val imgFotoDoctor = findViewById<ImageView>(R.id.imgFotoDoctor)
        val txtNombreDoctor = findViewById<TextView>(R.id.txtNombreDoctor)
        val txtEspecialidad = findViewById<TextView>(R.id.txtEspecialidad)
        val txtDireccionSucur = findViewById<TextView>(R.id.txtDireccionSucur)
        val rcvEspacios = findViewById<RecyclerView>(R.id.rcvEspacios)
        val ID_Doctor = intent.getIntExtra("ID_Doctor", 0)

        CoroutineScope(Dispatchers.IO).launch {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE emailUsuario = ?")!!
            statement.setString(1, userEmail)
            val resultSet = statement.executeQuery()
            withContext(Dispatchers.Main) {
                if (resultSet.next()) {
                    ID_User = resultSet.getInt("ID_Usuario")
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.prepareStatement(
                """
                SELECT
                    cm.ID_Centro,
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
                """)!!
            statement?.setInt(1, ID_Doctor)
            val resultSet = statement.executeQuery()
            withContext(Dispatchers.Main) {
                if (resultSet.next()) {
                    ID_Centro = resultSet.getInt("ID_Centro")
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
            }
        }

        val diasDelAno = obtenerDiasDelAno(Year.now().value)
        rcvDisponibilidad.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcvDisponibilidad.adapter = DiasAdapter(diasDelAno, { diaSeleccionado ->
            actualizarHorarios(diaSeleccionado)
        }, txtFecha)

        val diaSeleccionado = LocalDate.now()
        val horariosDisponibles = mutableListOf<Timestamp>()

        for (hora in horarioMatutino) {
            val timestampStringM = generarTimestamp(diaSeleccionado, hora)
            horariosDisponibles.add(Timestamp.valueOf(timestampStringM))
        }

        for (hora in horarioVespertino) {
            val timestampStringV = generarTimestamp(diaSeleccionado, hora)
            horariosDisponibles.add(Timestamp.valueOf(timestampStringV))
        }

        CoroutineScope(Dispatchers.IO).launch {
            val horasOcupadas = obtenerHorasOcupadas(diaSeleccionado.toString(), ID_Centro)

            withContext(Dispatchers.Main) {
                rcvEspacios.layoutManager = GridLayoutManager(this@activity_agendar, 3)
                rcvEspacios.adapter = AdaptadorHorarios(horariosDisponibles, horasOcupadas) { horaSeleccionada ->
                    Log.d("Seleccionar Hora", "Hora seleccionada: $horaSeleccionada")
                    // Manejar la selección de hora
                }
            }
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

        btnSelecSucursal.setOnClickListener {
            val intent = Intent(this, activity_busqueda::class.java)
            startActivity(intent)
        }
    }

    private fun checkIfDoctorExists(): Boolean {
        val DoctorExist = intent.getBooleanExtra("DoctorExist", false)
        return DoctorExist
    }

    private fun actualizarHorarios(dia: Dia) {
        Log.e("Info","Actualizando horarios para el día: ${dia.fecha}")
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
        val hoy = LocalDate.now()
        val ultimoDia = LocalDate.of(ano, 12, 31)
        return hoy.datesUntil(ultimoDia.plusDays(1)).map { Dia(it) }.toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generarTimestamp(fechaSeleccionada: LocalDate, hora: String): String {
        val formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatoHora = DateTimeFormatter.ofPattern("hh:mm a")

        // Parsear la fecha y hora y luego formatearla en el formato adecuado
        val fechaFormateada = fechaSeleccionada.format(formatoFecha)
        val horaFormateada = LocalTime.parse(hora, formatoHora).format(DateTimeFormatter.ofPattern("HH:mm:ss"))

        return "$fechaFormateada $horaFormateada"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun obtenerHorasOcupadas(diaSeleccionadoString: String, idCentro: Int): List<String> {
        val horasOcupadas = mutableListOf<String>()
        var objConexion: Connection? = null
        var statement: PreparedStatement? = null
        var resultSet: ResultSet? = null

        try {
            // Convertir String a LocalDate
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val diaSeleccionado = LocalDate.parse(diaSeleccionadoString, formatter)

            objConexion = ClaseConexion().cadenaConexion()
            statement = objConexion?.prepareStatement("""
                SELECT 
                    horaCita
                FROM 
                    tbCitasMedicas
                WHERE 
                    diaCita = ? 
                    AND ID_Centro = ?
            """)!!

            statement.setDate(1, Date.valueOf(diaSeleccionado.toString()))
            statement.setInt(2, idCentro)
            resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val horaCita = resultSet.getTime("horaCita")
                val horaLocalTime = horaCita.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalTime()

                horasOcupadas.add(horaLocalTime.toString())
            }
        } catch (e: SQLException) {
            Log.e("SQL Error", "Error al ejecutar la consulta", e)
        } finally {
            resultSet?.close()
            statement?.close()
            objConexion?.close()
        }

        return horasOcupadas
    }
}