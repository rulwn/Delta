package RecycleViewHelper

import Modelo.ClaseConexion
import Modelo.dataClassIndicaciones
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import delta.medic.mobile.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdaptadorTratamientos(private var Datos: List<dataClassIndicaciones>): RecyclerView.Adapter<ViewHolderTratamientos>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTratamientos {
            val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_tratamiento, parent, false)

            return ViewHolderTratamientos(vista)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderTratamientos, position: Int) {
        val tratamiento = Datos[position]
        holder.txtNombreMedicina.text = tratamiento.medicina
        holder.txtDosis.text = tratamiento.dosisMedi
        holder.txtDetallesIndicaciones.text = tratamiento.detalleIndi
        holder.txtTiempo.text = tratamiento.id_tiempo.toString()
        holder.imgOpciones.setOnClickListener {

        }


        /*val item = Datos[position]
        holder.imgborrar.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Seguro?")
            builder.setMessage("Deseas eliminar el registro?")
            builder.setPositiveButton("Si") { dialog, which ->
                Eliminarlista(item.nombreProductos, position)
            }
            builder.setNegativeButton("No") { dialog, which ->

            }
            val alertDialog = builder.create()
            alert*Dialog.show()*/
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