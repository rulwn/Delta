package delta.medic.mobile.ui.home

import Modelo.ClaseConexion
import Modelo.dataClassCitas
import Modelo.dataClassFavoritos
import RecycleViewHelper.AdaptadorCentrosRecientes
import RecycleViewHelper.AdaptadorCitas
import RecycleViewHelper.AdaptadorTratamientosChiquito
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R
import delta.medic.mobile.activity_busqueda
import delta.medic.mobile.activity_login.UserData.userEmail
import delta.medic.mobile.fragment_control_tratamientos
import delta.medic.mobile.fragment_usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val txtBienvenido = root.findViewById<TextView>(R.id.txtBienvenido)
        val txtaunnotienestratamientos =
            root.findViewById<TextView>(R.id.txtaunnotienesTratamientos)
        val rcvTratamientos = root.findViewById<RecyclerView>(R.id.scrollTratamientos)
        val fragmentControlTratamientos = fragment_control_tratamientos()
        val rcvRecordatoriosCitas = root.findViewById<RecyclerView>(R.id.rcvProximaCita)
        val txtaunotienescitas = root.findViewById<TextView>(R.id.txtaunnotienescitas)
        val txtAunotienescentros = root.findViewById<TextView>(R.id.txtaunnotienescentros)
        val rcvCentros = root.findViewById<RecyclerView>(R.id.rcvCentrosRecientes)
        val btnDolorCabeza = root.findViewById<TextView>(R.id.btnDolordeCabeza)
        val btnNauseas = root.findViewById<TextView>(R.id.btnNauseas)
        val btnTemperatura = root.findViewById<TextView>(R.id.btnTemperatura)
        rcvRecordatoriosCitas.overScrollMode = View.OVER_SCROLL_NEVER
        rcvTratamientos.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rcvRecordatoriosCitas.layoutManager = NoScrollLinearLayoutManager(requireContext())
        loadData(txtBienvenido)
        rcvCentros.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val tratamientosDB = fragmentControlTratamientos.obtenerDatosTratamientosPeriodo()
                withContext(Dispatchers.Main) {
                    if (tratamientosDB.isEmpty()) {
                        txtaunnotienestratamientos.visibility = View.VISIBLE
                    } else {
                        txtaunnotienestratamientos.visibility = View.GONE
                        val miAdaptador = AdaptadorTratamientosChiquito(tratamientosDB)
                        rcvTratamientos.adapter = miAdaptador
                    }
                }
            } catch (e: Exception) {
                println("Error al obtener los tratamientos: ${e.message}")
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val citasDB = obtenerCitas()
                withContext(Dispatchers.Main) {
                    if (citasDB.isEmpty()) {
                        txtaunotienescitas.visibility = View.VISIBLE
                    } else {
                        txtaunotienescitas.visibility = View.GONE
                        val miAdaptador = AdaptadorCitas(citasDB)
                        rcvRecordatoriosCitas.adapter = miAdaptador
                    }
                }
            } catch (e: Exception) {
                println("Error al obtener los datos de la base de datos: ${e.message}")
            }
        }
        val emailUsuario = userEmail
        CoroutineScope(Dispatchers.Main).launch {
            while (isActive) { // Este ciclo se repetirá mientras la coroutine esté activa
                val listaRecientes = obtenerRecientes(emailUsuario)
                withContext(Dispatchers.Main) {
                    if (listaRecientes.isEmpty()) {
                        txtAunotienescentros.visibility = View.VISIBLE
                    } else {
                        txtAunotienescentros.visibility = View.GONE
                        val adapter = AdaptadorCentrosRecientes(listaRecientes)
                        adapter.emailUsuario = emailUsuario
                        rcvCentros.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
                delay(5000)
            }
        }

        btnDolorCabeza.setOnClickListener {
            val intent = Intent(context, activity_busqueda::class.java).apply {
                putExtra("query", "Neurólogo")
            }
            startActivity(intent)
            parentFragmentManager.popBackStack()
        }
        btnNauseas.setOnClickListener {
            val intent = Intent(context, activity_busqueda::class.java).apply {
                putExtra("query", "Gastroenterólogo")
            }
            startActivity(intent)
            parentFragmentManager.popBackStack()
        }
        btnTemperatura.setOnClickListener {
            val intent = Intent(context, activity_busqueda::class.java).apply {
                putExtra("query", "Medicina general")
            }
            startActivity(intent)
        }
        updateTextViewColorsForTheme(root)


        return root
    }

    class NoScrollLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
        override fun canScrollVertically(): Boolean {
            return false
        }
    }

    private suspend fun obtenerCitas(): List<dataClassCitas> {
        return withContext(Dispatchers.IO) {
            val citas = mutableListOf<dataClassCitas>()
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement(
                        """
                    SELECT * FROM (
                        SELECT 
                            citas.ID_Cita,
                            citas.diacita,
                            citas.horacita,
                            citas.motivo,
                            citas.estadoCita,
                            citas.id_usuario,
                            usua.nombreUsuario,
                            usua.apellidoUsuario,
                            esp.nombreespecialidad
                        FROM 
                            tbcitasmedicas citas
                        INNER JOIN 
                            tbdoctores docs ON citas.id_doctor = docs.id_doctor
                        INNER JOIN 
                            tbEspecialidades esp ON docs.id_especialidad = esp.id_especialidad
                        INNER JOIN 
                            tbUsuarios usua ON docs.id_usuario = usua.id_usuario
                        INNER JOIN 
                            tbUsuarios us ON citas.id_usuario = us.id_usuario
                        WHERE 
                            us.emailUsuario = ?
                            AND citas.diacita >= CURRENT_DATE
                            AND citas.estadoCita = 'A'
                        ORDER BY 
                            citas.diacita ASC, 
                            citas.horacita ASC
                    )
                    WHERE 
                        ROWNUM = 1
                    """
                    )!!

                    statement.setString(1, userEmail)  // Se cambia el correo por el parámetro `userEmail`
                    val resultset = statement.executeQuery()
                    while (resultset.next()) {
                        val ID_Cita = resultset.getInt("ID_Cita")
                        val diaCita = resultset.getDate("diacita")
                        val horaCita = resultset.getTimestamp("horacita")
                        val motivo = resultset.getString("motivo")
                        val estadoCita = resultset.getString("estadoCita")
                        val ID_Usuario = resultset.getInt("id_usuario")
                        val nombreDoctor = resultset.getString("nombreUsuario")
                        val apellidoDoctor = resultset.getString("apellidoUsuario")
                        val especialidad = resultset.getString("nombreespecialidad")

                        val cita = dataClassCitas(
                            ID_Cita,
                            diaCita,
                            horaCita,
                            motivo,
                            estadoCita,
                            ID_Usuario,
                            nombreDoctor,
                            apellidoDoctor,
                            especialidad
                        )
                        citas.add(cita)
                    }
                } else {
                    println("No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: Exception) {
                println("Este es el error ${e.message}")
            }
            citas
        }
    }

    suspend fun obtenerRecientes(EmailUsuario: String): MutableList<dataClassFavoritos> {
        val listaFavoritos = mutableListOf<dataClassFavoritos>()

        withContext(Dispatchers.IO) {
            // Crear una conexión a la base de datos
            val conexion = ClaseConexion().cadenaConexion()

            // Preparar la consulta
            val statement = conexion?.prepareStatement(
                """
                     SELECT
                        u.ID_Usuario,
                        u.nombreUsuario,
                        u.imgUsuario,
                        u.emailUsuario,
                        d.ID_Doctor,
                        s.ID_Sucursal,
                        s.imgSucursal,
                        e.nombreEspecialidad
                        FROM
                        tbRecientes f
                        INNER JOIN tbDoctores d ON d.ID_Doctor = f.ID_Doctor
                        INNER JOIN tbSucursales s ON s.ID_Sucursal = f.ID_Sucursal
                        INNER JOIN tbUsuarios u ON u.ID_Usuario = d.ID_Usuario
                        INNER JOIN tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
                        WHERE
                        f.ID_Usuario = (SELECT ID_Usuario FROM tbUsuarios WHERE emailUsuario = ?)
                """.trimIndent()

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
                val nombreTipoSucursal = resultado.getString("nombreEspecialidad")
                val emailUsuario = resultado.getString("emailUsuario")

                // Crear un objeto dataClassFavoritos
                val favorito = dataClassFavoritos(
                    idUsuario,
                    idDoctor,
                    idSucursal,
                    nombreUsuario,
                    imgUsuario,
                    imgSucursal,
                    nombreTipoSucursal,
                    emailUsuario
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
    private fun updateTextViewColorsForTheme(root: View) {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val txtPodriasSentir = root.findViewById<TextView>(R.id.txtPodriasSentir)
        val txtTratamientosPrincipal = root.findViewById<TextView>(R.id.txtTratamientosPrincipal)
        val txtCitasPrincipal = root.findViewById<TextView>(R.id.txtCitasPrincipal)
        val txtCentrosRecientesPrincipal = root.findViewById<TextView>(R.id.txtCentrosRecientesPrincipal)
        val btnDolorCabeza = root.findViewById<TextView>(R.id.btnDolordeCabeza)
        val btnNauseas = root.findViewById<TextView>(R.id.btnNauseas)
        val btnTemperatura = root.findViewById<TextView>(R.id.btnTemperatura)
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                txtPodriasSentir?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorLight))
                txtTratamientosPrincipal?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorLight))
                txtCitasPrincipal?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorLight))
                txtCentrosRecientesPrincipal?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorLight))
                btnDolorCabeza?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                btnDolorCabeza?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorLight))
                btnNauseas?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                btnNauseas?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorLight))
                btnTemperatura?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                btnTemperatura?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorLight))
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                txtPodriasSentir?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
                txtTratamientosPrincipal?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
                txtCitasPrincipal?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
                txtCentrosRecientesPrincipal?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
                btnDolorCabeza?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
                btnDolorCabeza?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColornegro))
                btnNauseas?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
                btnNauseas?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColornegro))
                btnTemperatura?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
                btnTemperatura?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColornegro))
            }
        }
    }
    fun loadData(lbNombre: TextView) {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                val fragmentUsuario = fragment_usuario()
                val user = fragmentUsuario.GetUserParameters(userEmail)
                //Estos campos al estar con map pondrá  "[]" al inicio y al final
                val nombreUsuario = user.map { it.nombreUsuario }

                withContext(Dispatchers.Main) {
                    //Para solucionarlo se coloca replace
                    lbNombre.setText(
                        " Bienvenido ${
                            nombreUsuario.toString().replace("[", "").replace("]", "")
                        }"
                    )
                }
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}