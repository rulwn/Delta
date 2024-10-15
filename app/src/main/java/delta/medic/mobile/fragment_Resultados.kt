package delta.medic.mobile

import Modelo.ClaseConexion
import Modelo.dataClassCentro
import RecycleViewHelper.AdaptadorCentro
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class fragment_Resultados : Fragment() {

    lateinit var txtSearch: EditText
    private lateinit var btnRegresar: ImageView
    private lateinit var rcvResultados: RecyclerView
    private lateinit var txtResultadoBusqueda: TextView
    private lateinit var view2: View
    private lateinit var txtResultados: TextView
    private lateinit var txtDoctorescercanos : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment__resultados, container, false)
        btnRegresar = root.findViewById(R.id.btnRegresar)
        rcvResultados = root.findViewById(R.id.rcvResultados)
        txtResultadoBusqueda = root.findViewById(R.id.txtResultadoBusqueda)
        view2 = root.findViewById(R.id.view2)
        txtResultados = root.findViewById(R.id.txtResultados)
        txtDoctorescercanos = root.findViewById(R.id.txtDoctorescercanos)

        val activity = activity as? activity_busqueda
        txtSearch = activity?.getSearchEditText() ?: EditText(context)
        txtResultadoBusqueda.text = txtSearch.text

        btnRegresar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Configuración del RecyclerView
        rcvResultados.layoutManager = LinearLayoutManager(context)

        // Ejecutar la búsqueda en una coroutine
        CoroutineScope(Dispatchers.IO).launch {
            val centrosDB = obtenerDatos(txtSearch.text.toString(), txtSearch.text.toString(), txtSearch.text.toString())
            withContext(Dispatchers.Main) {
                val miAdapter = AdaptadorCentro(centrosDB)
                rcvResultados.adapter = miAdapter
            }
        }

        applyThemeColors(root)

        return root
    }

    private fun applyThemeColors(root: View) {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                txtResultados.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                txtDoctorescercanos.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                txtResultadoBusqueda.setTextColor(ContextCompat.getColor(requireContext(), R.color.Azul1))
                btnRegresar.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                view2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                txtResultados.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                txtDoctorescercanos.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                txtResultadoBusqueda.setTextColor(ContextCompat.getColor(requireContext(), R.color.Azul1))
                btnRegresar.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
                view2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.fondobusqueda))
            }
        }
    }

    // Función para obtener los datos desde la base de datos
    private suspend fun obtenerDatos(nombreUsuario: String, apellidoUsuario: String, nombreEspecialidad: String): List<dataClassCentro> {
        val objConexion = ClaseConexion().cadenaConexion()!!
        val centroMedico = mutableListOf<dataClassCentro>()

        if (objConexion != null) {
            try {
                val busqueda = objConexion.prepareStatement("""
                    SELECT 
                    d.ID_Doctor,
                    u.ID_Usuario,
                    u.emailUsuario,
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
                    s.imgSucursal
                    FROM 
                    tbDoctores d
                    INNER JOIN 
                    tbUsuarios u ON d.ID_Usuario = u.ID_Usuario
                    INNER JOIN 
                    tbEspecialidades e ON d.ID_Especialidad = e.ID_Especialidad
                    INNER JOIN 
                    tbSucursales s ON d.ID_Sucursal = s.ID_Sucursal
                    WHERE
                    (LOWER(u.nombreUsuario) LIKE LOWER(?)
                    OR
                    LOWER(u.apellidoUsuario) LIKE LOWER(?)
                    OR
                    LOWER(e.nombreEspecialidad) LIKE LOWER(?))
                    AND
                    u.ID_TipoUsuario = 2
                """)
                busqueda.setString(1, "%${nombreUsuario}%")
                busqueda.setString(2, "%${apellidoUsuario}%")
                busqueda.setString(3, "%${nombreEspecialidad}%")

                val resultSet = busqueda.executeQuery()
                while (resultSet.next()) {
                    val ID_Doctor = resultSet.getInt("ID_Doctor")
                    val ID_Usuario = resultSet.getInt("ID_Usuario")
                    val emailUsuario = resultSet.getString("emailUsuario")
                    val nombreUsuario = resultSet.getString("nombreUsuario")
                    val apellidoUsuario = resultSet.getString("apellidoUsuario")
                    val imgUsuario = resultSet.getString("imgUsuario")
                    val nombreEspecialidad = resultSet.getString("nombreEspecialidad")
                    val nombreSucursal = resultSet.getString("nombreSucursal")
                    val telefonoSucur = resultSet.getString("telefonoSucur")
                    val direccionSucur = resultSet.getString("direccionSucur")
                    val longSucur = resultSet.getDouble("longSucur")
                    val latiSucur = resultSet.getDouble("latiSucur")
                    val imgSucursal = resultSet.getString("imgSucursal")
                    val ID_Sucursal = resultSet.getInt("ID_Sucursal")

                    val valoresJuntos = dataClassCentro(
                        ID_Doctor, ID_Usuario, emailUsuario, ID_Sucursal, nombreUsuario, apellidoUsuario, imgUsuario,
                        nombreEspecialidad, nombreSucursal, telefonoSucur, direccionSucur,
                        longSucur, latiSucur, imgSucursal
                    )

                    centroMedico.add(valoresJuntos)
                    Log.e("valoresJuntos", valoresJuntos.toString())
                }
            } catch (e: Exception) {
                Log.e("obtenerDatos", "Error al obtener datos: ${e.message}")
            }
        } else {
            Log.e("obtenerDatos", "No se pudo establecer una conexión con la base de datos.")
        }
        return centroMedico
    }
}