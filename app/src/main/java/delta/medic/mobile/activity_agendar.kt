package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.Dia
import RecycleViewHelper.AdaptadorHorarios
import RecycleViewHelper.DiasAdapter
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
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class activity_agendar : AppCompatActivity() {

    var ID_User: Int = 0;
    var nombreDoctor : String = "";
    var apellidoDoctor : String = "";
    var imgDoctor : String = "";
    var direccionSucur : String = "";
    var imgSucursal : String = "";
    var nombreEspecialidad : String = "";
    var ID_Doctor : Int = 0;
    var horaSeleccionada : Timestamp? = null
    var diaSeleccionado : LocalDate? = null
    private lateinit var txtMotivo: TextView

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
        val txtFecha = findViewById<TextView>(R.id.txtFecha)
        val rcvDisponibilidad = findViewById<RecyclerView>(R.id.rcvDisponibilidad)
        var imgFotoSucursal = findViewById<ImageView>(R.id.imgSucursal)
        val imgFotoDoctor = findViewById<ImageView>(R.id.imgFotoDoctor)
        val txtNombreDoctor = findViewById<TextView>(R.id.txtNombreDoctor)
        val txtEspecialidad = findViewById<TextView>(R.id.txtEspecialidad)
        val txtDireccionSucur = findViewById<TextView>(R.id.txtDireccionSucur)
        txtMotivo = findViewById(R.id.txtMotivo)
        val txtPressHere = findViewById<TextView>(R.id.txtPressHere)

        val diasDelAno = obtenerDiasDelAno(Year.now().value)
        rcvDisponibilidad.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcvDisponibilidad.adapter = DiasAdapter(diasDelAno, { dia ->
            diaSeleccionado = dia.fecha // Asignar el día seleccionado
            actualizarHorarios(diaSeleccionado)

        }, txtFecha)

        fun generarTimestamp(fechaSeleccionada: LocalDate, hora: String): String {
            val formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formatoHora = DateTimeFormatter.ofPattern("hh:mm a")
            val fechaFormateada = fechaSeleccionada.format(formatoFecha)
            val horaFormateada = LocalDateTime.parse(
                "${fechaFormateada} ${hora}",
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")
            )
            return horaFormateada.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        }

        val diaSeleccionado = LocalDate.now()
        val horarioMatutino = arrayOf(
            "08:00 AM", "08:30 AM", "09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM",
            "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "01:00 PM", "01:30 PM", "02:00 PM"
        )

        val horarioVespertino = arrayOf(
            "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM", "04:30 PM",
            "05:00 PM", "05:30 PM", "06:00 PM", "06:30 PM", "07:00 PM", "07:30 PM", "08:00 PM"
        )

        for (hora in horarioMatutino) {
            val timestampM = generarTimestamp(diaSeleccionado, hora)
            println(timestampM)
        }

        for (hora in horarioVespertino) {
            val timestampV = generarTimestamp(diaSeleccionado, hora)
            println(timestampV)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement =
                objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE emailUsuario = ?")!!
            statement.setString(1, userEmail)
            val resultSet = statement.executeQuery()
            withContext(Dispatchers.Main) {
                if (resultSet!!.next()) {
                    ID_User = resultSet.getInt("ID_Usuario")
                }
            }
        }

        ID_Doctor = intent.getIntExtra("ID_Doctor", 0)

        CoroutineScope(Dispatchers.IO).launch {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.prepareStatement(
                """
        SELECT h.horarioTurno
            FROM tbHorarios h
            INNER JOIN tbDoctores d ON h.ID_Doctor = d.ID_Doctor
            WHERE d.ID_Doctor = ?
        """
            )
            statement?.setInt(1, ID_Doctor)
            val resultSet = statement?.executeQuery()
            var horaTurno: String? = null
            if (resultSet?.next() == true) {
                horaTurno = resultSet.getString("horarioTurno")
                Log.e("Info", "Hora de turno: $horaTurno")
            }

            val diaSeleccionado = LocalDate.now()
            val horariosDisponibles = mutableListOf<Timestamp>()

            if (horaTurno == "M") {
                for (hora in horarioMatutino) {
                    val timestampString = generarTimestamp(diaSeleccionado, hora)
                    horariosDisponibles.add(Timestamp.valueOf(timestampString))
                }

                // Cambiamos al hilo principal para interactuar con el RecyclerView
                withContext(Dispatchers.Main) {
                    val rcvEspacios = findViewById<RecyclerView>(R.id.rcvEspacios)
                    rcvEspacios.layoutManager = GridLayoutManager(this@activity_agendar, 3)
                    rcvEspacios.adapter = AdaptadorHorarios(horariosDisponibles, horarioMatutino) { hora ->
                        Log.d("Seleccionar Hora", "Hora seleccionada: $hora")
                        horaSeleccionada = hora
                    }
                }
            } else if (horaTurno == "V") {
                for (hora in horarioVespertino) {
                    val timestampString = generarTimestamp(diaSeleccionado, hora)
                    horariosDisponibles.add(Timestamp.valueOf(timestampString))
                }

                // Cambiamos al hilo principal para interactuar con el RecyclerView
                withContext(Dispatchers.Main) {
                    val rcvEspacios = findViewById<RecyclerView>(R.id.rcvEspacios)
                    rcvEspacios.layoutManager = GridLayoutManager(this@activity_agendar, 3)
                    rcvEspacios.adapter = AdaptadorHorarios(horariosDisponibles, horarioMatutino) { hora ->
                        Log.d("Seleccionar Hora", "Hora seleccionada: $hora")
                        horaSeleccionada = hora
                    }
                }
            }
        }

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
INNER JOIN 
    tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
INNER JOIN 
    tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
INNER JOIN 
    tbSucursales s ON d.ID_Sucursal = s.ID_Sucursal
INNER JOIN 
    tbServicios se ON d.ID_Doctor = se.ID_Doctor
WHERE 
    d.ID_Doctor = ?
        """
            )
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
                } else {
                    Log.e("Info", "No se encontraron resultados para el ID_Doctor: $ID_Doctor")                }
            }
        }
        val txtMotivo = findViewById<TextView>(R.id.txtMotivo) // Asegúrate de que el ID es correcto
        val lbAgendarCita = findViewById<TextView>(R.id.lbAgendarCita)
        lbAgendarCita.setText(Html.fromHtml(getResources().getString(R.string.subrayado)))
        val cardDoctor = findViewById<CardView>(R.id.linearCard)
        val linearNoDoctor = findViewById<LinearLayout>(R.id.linearNoDoctor)
        val txtSucu = findViewById<TextView>(R.id.txtSucu)
        val txtdispo = findViewById<TextView>(R.id.txtdispo)
        val txtespacios = findViewById<TextView>(R.id.txtespacios)
        val grid = findViewById<GridLayout>(R.id.grid)
        val textMoti = findViewById<TextView>(R.id.textMoti)
        txtPressHere.setText(Html.fromHtml(getResources().getString(R.string.txtPressHere)))
        val doctorExists = checkIfDoctorExists()

        if (doctorExists) {
            linearNoDoctor.visibility = View.GONE
            cardDoctor.visibility = View.VISIBLE
        } else {
            linearNoDoctor.visibility = View.VISIBLE
            cardDoctor.visibility = View.GONE
            rcvDisponibilidad.visibility = View.GONE
            txtSucu.visibility = View.GONE
            cardDoctor.visibility = View.GONE
            txtdispo.visibility = View.GONE
            rcvDisponibilidad.visibility = View.GONE
            txtFecha.visibility = View.GONE
            txtespacios.visibility = View.GONE
            grid.visibility = View.GONE
            textMoti.visibility = View.GONE
            txtMotivo.visibility = View.GONE
            btnContinuar.visibility = View.GONE
        }

        btnRegresar.setOnClickListener {
            finish()
        }

        btnContinuar.setOnClickListener {
            if (txtMotivo.text.toString().isEmpty()){
                txtMotivo.error = "Por favor, escribe un motivo."
            }
            else {
                if (horaSeleccionada != null && diaSeleccionado != null) {
                    showCustomDialog()
                }
                else {
                    Toast.makeText(this, "Por favor, selecciona una hora y un día.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        txtPressHere.setOnClickListener {
            val intent = Intent(this, activity_busqueda::class.java)
            startActivity(intent)
        }
    }

    private fun checkIfDoctorExists(): Boolean {
        val DoctorExist = intent.getBooleanExtra("DoctorExist", false)
        return DoctorExist
    }

    private fun actualizarHorarios(dia: LocalDate?) {
        Log.e("Info", "Actualizando horarios para el día: ${dia}")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertarCitaEnBaseDeDatos(horaSeleccionada: Timestamp, diaSeleccionado: LocalDate, motivo: String, ID_Doctor: Int, ID_User: Int) {
        try {
            // Convertir el Timestamp a LocalTime
            val localTime = horaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
            Log.d("Debug", "Hora convertida a LocalTime: $localTime")

            // Combinar la fecha (LocalDate) y la hora (LocalTime) en LocalDateTime
            val localDateTime = LocalDateTime.of(diaSeleccionado, localTime)
            Log.d("Debug", "Fecha y hora combinadas en LocalDateTime: $localDateTime")

            // Convertir LocalDateTime a Timestamp con el formato correcto
            val timestampFinal = Timestamp.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            Log.d("Debug", "Timestamp final: $timestampFinal")

            // Generar el estado de la cita ('A' para agendada por defecto)
            val estadoCita = 'A'

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Establecer la conexión a la base de datos
                    val objConexion = ClaseConexion().cadenaConexion()
                    val insertStatement = objConexion?.prepareStatement(
                        """
                    INSERT INTO tbCitasMedicas 
                    (diaCita, estadoCita, horaCita, motivo, ID_Doctor, ID_Usuario)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """
                    )
                    insertStatement?.setDate(1, java.sql.Date.valueOf(diaSeleccionado.toString())) // diaCita
                    insertStatement?.setString(2, estadoCita.toString()) // estadoCita (Por defecto 'A')
                    insertStatement?.setTimestamp(3, timestampFinal) // horaCita en el formato correcto
                    insertStatement?.setString(4, motivo) // motivo de la cita
                    insertStatement?.setInt(5, ID_Doctor) // ID_Doctor
                    insertStatement?.setInt(6, ID_User) // ID_Usuario

                    // Ejecutar el INSERT
                    insertStatement?.executeUpdate()

                    // Notificación de éxito en el hilo principal
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@activity_agendar, "Cita registrada exitosamente.", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    // Manejo de excepciones y notificación de error
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@activity_agendar, "Error al registrar la cita.", Toast.LENGTH_SHORT).show()
                    }
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            // Capturar errores si la conversión falla
            e.printStackTrace()
            Toast.makeText(this, "Error al convertir la fecha y hora.", Toast.LENGTH_SHORT).show()
        }
    }
    ///////Custom Dialog/////////
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showCustomDialog() {
        if (isFinishing || isDestroyed) {
            return
        }

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
            // Actualizar el valor del motivo cada vez que se hace clic en el botón
            val motivo = txtMotivo.text.toString()
            Log.e("Info", "$motivo , $horaSeleccionada, $diaSeleccionado, $ID_Doctor")
            if (motivo.isNotEmpty() && horaSeleccionada != null && diaSeleccionado != null) {
                insertarCitaEnBaseDeDatos(horaSeleccionada!!, diaSeleccionado!!, motivo, ID_Doctor, ID_User)
                Toast.makeText(this, "Cita registrada exitosamente.", Toast.LENGTH_SHORT).show()
                val Intent = Intent(this, MainActivity::class.java)
                startActivity(Intent)
            } else {
                Toast.makeText(this, "Por favor, selecciona una hora, un día y escribe un motivo.", Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss() // Cerrar el diálogo
        }

        val editButton = dialog.findViewById<TextView>(R.id.editButton)
        editButton.setOnClickListener {
            dialog.dismiss() // Cerrar el diálogo
        }

        dialog.show()
    }
/////////////////

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun obtenerDiasDelAno(ano: Int): List<Dia> {
        val hoy = LocalDate.now()
        val ultimoDia = LocalDate.of(ano, 12, 31)
        return hoy.datesUntil(ultimoDia.plusDays(1)).map { Dia(it) }.toList()
    }
}