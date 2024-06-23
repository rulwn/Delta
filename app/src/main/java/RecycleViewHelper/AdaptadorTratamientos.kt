package RecycleViewHelper

import Modelo.ClaseConexion
import Modelo.dataClassIndicaciones
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdaptadorTratamientos(private var Datos: List<dataClassIndicaciones>): RecyclerView.Adapter<ViewHolderTratamientos>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTratamientos {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolderTratamientos, position: Int) {
        TODO("Not yet implemented")
    }
    fun Actuzalizarlista(nuevalista: List<dataclassProductos>){
        Datos = nuevalista
        notifyDataSetChanged()
    }

    fun Actualizarlistadespuesdecargardatos(id: String, nuevoNombre: String){
        val index = Datos.indexOfFirst { it.uuid == uuid }
        Datos[index].nombreProductos = nuevoNombre
        notifyItemChanged(index)
    }

    fun Eliminarlista(nombreProducto: String, posicion: Int){
        //1. crear clase conexion
        //2. Quitar elemento de la lista
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)
        //Quitar de la base
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()
            val delProductos = objConexion?.prepareStatement("Delete tbProductos where nombreProducto = ?")!!

            delProductos.setString(1, nombreProducto)
            delProductos.executeUpdate()
            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        //notificamos que se eliminaron los datos
        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    fun actualizarProductos(nombreProducto: String, uuid: String){
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()
            val updateProductos = objConexion?.prepareStatement("Update tbProductos set nombreProducto = ? where uuid = ?")!!
            updateProductos.setString(1, nombreProducto)
            updateProductos.setString(2, uuid)
            updateProductos.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                Actualizarlistadespuesdecargardatos(uuid, nombreProducto)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)

        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = Datos[position]
        holder.textView.text = producto.nombreProductos

        val Item = Datos[position]
        holder.imgborrar.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Seguro?")
            builder.setMessage("Deseas eliminar el registro?")
            builder.setPositiveButton("Si"){dialog, which ->
                Eliminarlista(Item.nombreProductos, position)
            }
            builder.setNegativeButton("No"){ dialog, which ->

            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

}