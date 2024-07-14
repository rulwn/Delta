package Modelo

import oracle.sql.BLOB
import oracle.sql.DATE
import java.sql.Blob
import java.sql.Date

data class dataClassUsuario(

    var idUsuario: Int,
    var nombreUsuario: String,
    var apellidoUsuario: String,
    var emailUsuario: String,
    var contraseña: String,
    var dirección: String,
    var teléfonoUsuario: String,
    var sexo: String,
    var fechaNacimiento: Date,
    var imgUsuario: String,
    var idTipoUsuario: Int

)
