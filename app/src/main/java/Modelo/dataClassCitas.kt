package Modelo

import java.sql.Timestamp
import java.util.Date

data class dataClassCitas(
    var ID_Cita: Int,
    var diaCita: Date,
    var horaCita: Timestamp,
    var motivo:String,
    var ID_Centro: Int,
    var ID_Paciente: Int,
    var nombrePaciente: String,
    var parentesco: String,
    var ID_Usuario: Int,
    var nombreDoctor: String,
    var apellidoDoctor: String,
    var especialidad: String
)