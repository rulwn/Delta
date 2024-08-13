package RecycleViewHelper

import Modelo.ClaseConexion
import Modelo.dataClassIndicaciones
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdaptadorTratamientosChiquito(private var Datos: List<dataClassIndicaciones>): RecyclerView.Adapter<ViewHolderTratamientosChiquito>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTratamientosChiquito {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_tratamientosmini, parent, false)

        return ViewHolderTratamientosChiquito(vista)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderTratamientosChiquito, position: Int) {
        val tratamiento = Datos[position]
        //holder.txtNombreMedicina.text = "${tratamiento.medicina} - ${tratamiento.dosisMedi}"
        //holder.txtDetallesIndicaciones.text = tratamiento.detalleIndi
        holder.txtTiempo.text = tratamiento.id_tiempo.toString()
        val itemColor = getItemColor(position)
        holder.cardview.setCardBackgroundColor(itemColor)
        holder.imgOpciones.setOnClickListener {
            val popupMenu = PopupMenu(holder.itemView.context, holder.imgOpciones)
            popupMenu.inflate(R.menu.menu_opciones)

            // Manejar la selección de opciones del menú
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_enviar_control -> {
                        // Navegar al fragmento de control
                        val navController = Navigation.findNavController(holder.itemView)
                        navController.navigate(R.id.fragment_control)
                        true
                    }
                    else -> false
                }
            }

            // Mostrar el menú
            popupMenu.show()
        }
    }

    private val colorStart = Color.parseColor("#042A60")
    private val colorMiddle = Color.parseColor("#1D4A7C")
    private val colorEnd = Color.parseColor("#3C659E")

    // Function to interpolate colors
    private fun interpolateColor(startColor: Int, endColor: Int, factor: Float): Int {
        val startA = Color.alpha(startColor)
        val startR = Color.red(startColor)
        val startG = Color.green(startColor)
        val startB = Color.blue(startColor)

        val endA = Color.alpha(endColor)
        val endR = Color.red(endColor)
        val endG = Color.green(endColor)
        val endB = Color.blue(endColor)

        val a = (startA + ((endA - startA) * factor)).toInt()
        val r = (startR + ((endR - startR) * factor)).toInt()
        val g = (startG + ((endG - startG) * factor)).toInt()
        val b = (startB + ((endB - startB) * factor)).toInt()

        return Color.argb(a, r, g, b)
    }

    private fun getItemColor(position: Int): Int {
        val fraction = position.toFloat() / (itemCount - 1)
        return when {
            fraction <= 0.5 -> interpolateColor(colorStart, colorMiddle, fraction * 2)
            else -> interpolateColor(colorMiddle, colorEnd, (fraction - 0.5f) * 2)
        }
    }


    private fun Actualizarlista(nuevalista: List<dataClassIndicaciones>){
        Datos = nuevalista
        notifyDataSetChanged()
    }

    private fun Actualizarlistadespuesdecargardatos(id: Int, nuevoNombre: String){
        val index = Datos.indexOfFirst { it.id_indicacion == id }
        Datos[index].medicina = nuevoNombre
        notifyItemChanged(index)
    }

    private fun Eliminarlista(nombreProducto: String, posicion: Int){
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)
        //Quitar de la base
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()
            val delIndicaciones = objConexion?.prepareStatement("Delete tbIndicaciones where medicina = ?")!!

            delIndicaciones.setString(1, nombreProducto)
            delIndicaciones.executeUpdate()
            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        //notificamos que se eliminaron los datos
        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    fun actualizarIndicaciones(nombreIndicacion: String, id: Int){
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()
            val updateIndicaciones = objConexion?.prepareStatement("Update tbIndicaciones set medicina = ? where uuid = ?")!!
            updateIndicaciones.setString(1, nombreIndicacion)
            updateIndicaciones.setString(2, id.toString())
            updateIndicaciones.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                Actualizarlistadespuesdecargardatos(id, nombreIndicacion)
            }
        }

    }
}
