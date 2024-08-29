package Modelo

import java.sql.Date
import java.sql.Timestamp

data class dataClassIndicaciones(
    val id_indicacion: Int,
    var inicioMedi: Date,
    var finalMedi: Date,
    var dosisMedi: String,
    var medicina: String,
    var detalleIndi: String,
    val lapsosTiempo: String,
    val frecuenciaMedi: Int,
    val hora: String
)
