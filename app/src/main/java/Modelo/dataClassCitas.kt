package Modelo

import java.sql.Timestamp
import java.util.Date

data class dataClassCitas(
    var ID_Cita: Int,
    var diaCita: Date,
    var horaCita: Timestamp,
    var motivo:String,
    var EstadoCita: String,
    var ID_Usuario: Int,
    var nombreDoctor: String,
    var apellidoDoctor: String,
    var especialidad: String
)