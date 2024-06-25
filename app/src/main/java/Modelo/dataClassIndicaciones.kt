package Modelo

import java.sql.Timestamp

data class dataClassIndicaciones(
    var id_indicacion: Int,
    var duracionMedi: Timestamp,
    var dosisMedi: Double,
    var medicina: String,
    var detalleIndi: String,
    var id_receta: Int,
    var id_tiempo: Int

)
