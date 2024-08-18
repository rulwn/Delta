package RecycleViewHelper

import Modelo.ClaseConexion
import Modelo.dataClassCitas
import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.graphics.Color
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import delta.medic.mobile.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class AdaptadorCitas(private var Datos: List<dataClassCitas>) :
    RecyclerView.Adapter<viewHolderCitas>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolderCitas {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_citas, parent, false)

        return viewHolderCitas(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: viewHolderCitas, position: Int) {
        val citas = Datos[position]
        val dayFormat = SimpleDateFormat("dd")
        val monthFormat = SimpleDateFormat("MM")
        val timeFormat = SimpleDateFormat("HH:mm")

        val diaCitaString = dayFormat.format(citas.diaCita)
        val mesCitaString = monthFormat.format(citas.diaCita)
        val horaCitaString = timeFormat.format(citas.horaCita)

        val diaYMes = "$diaCitaString/$mesCitaString"

        val TiempoYDia = "${diaYMes} ${horaCitaString}"
        val MotivoYPaciente = "${citas.motivo}, ${citas.nombrePaciente}"
        val DoctorEspecialidad =
            "${citas.nombreDoctor} ${citas.apellidoDoctor} - ${citas.especialidad}"
        holder.txtTiempoCitas.text = TiempoYDia
        holder.txtMotivo_paciente.text = MotivoYPaciente
        holder.txtDoctor_especialidad.text = DoctorEspecialidad
        holder.imgOpciones.setOnClickListener {
            val popupMenu = PopupMenu(holder.itemView.context, holder.imgOpciones)
            popupMenu.inflate(R.menu.menu_citas)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_cancelar -> {
                        val layout = LinearLayout(holder.itemView.context).apply {
                            orientation = LinearLayout.VERTICAL
                            setPadding(50, 40, 50, 40)
                        }
                        val icon = ImageView(holder.itemView.context).apply {
                            setImageResource(R.drawable.ic_cancelarcita)
                            layoutParams = LinearLayout.LayoutParams(
                                300, 300
                            ).apply {
                                gravity = android.view.Gravity.CENTER_HORIZONTAL
                            }
                            setPadding(0, 0, 0, 40)
                        }

                        val message = TextView(holder.itemView.context).apply {
                            text = "¿Estás seguro que quieres cancelar la cita?"
                            setTextColor(Color.parseColor("#E33E3E"))
                            textSize = 26f
                            gravity = android.view.Gravity.CENTER
                        }

                        layout.addView(icon)
                        layout.addView(message)

                        val builder = AlertDialog.Builder(holder.itemView.context)
                        builder.setView(layout)
                        builder.setPositiveButton("Sí", null)
                        builder.setNegativeButton("No", null)

                        val dialog = builder.create()
                        dialog.show()

                        val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        positiveButton.setTextColor(Color.parseColor("#E33E3E"))
                        positiveButton.setOnClickListener {
                                CoroutineScope(Dispatchers.Main).launch {
                                    val idCita = citas.ID_Cita
                                    cancelarCita(idCita)
                                }
                            dialog.dismiss()
                        }

                        val negativeButton: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        negativeButton.setTextColor(Color.parseColor("#E33E3E"))
                        negativeButton.setOnClickListener {
                            dialog.dismiss()
                        }
                        true
                    }

                    else -> false
                }
            }
            // Mostrar el menú
            popupMenu.show()
        }
    }
    suspend fun cancelarCita(idCita: Int) {
        withContext(Dispatchers.IO) {
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion != null) {
                    val statement = objConexion.prepareStatement(
                        """
                                UPDATE tbcitasmedicas
                                SET estadoCita = 'C'
                                WHERE ID_Cita = ?
                                """
                    )!!
                    statement.setInt(1, idCita)
                    val rowsAffected = statement.executeUpdate()
                    if (rowsAffected > 0) {
                        println("Cita con ID $idCita ha sido cancelada.")
                    } else {
                        println("No se encontró ninguna cita con ID $idCita.")
                    }
                } else {
                    println("No se pudo establecer una conexión con la base de datos.")
                }
            } catch (e: Exception) {
                println("Este es el error ${e.message}")
            }
        }
    }
}