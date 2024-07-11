package Modelo

import java.sql.Timestamp

data class dataClassIndicaciones(
    val id_indicacion: Int,
    var duracionMedi: Timestamp,
    var dosisMedi: String,
    var medicina: String,
    var detalleIndi: String,
    val id_receta: Int,
    val id_tiempo: Int
)
