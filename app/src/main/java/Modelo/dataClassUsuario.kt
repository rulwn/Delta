package Modelo

import oracle.sql.BLOB
import oracle.sql.DATE
import java.sql.Blob
import java.sql.Date

data class dataClassUsuario(
    var idUsuario: Number,
    var nombreUsuario: String,
    var apellidoUsuario: String,
    var emailUsuario: String,
    var contraseña: String,
    var dirección: String,
    var sexo: Char,
    var fechaNacimiento: Date,
    var imgUsuario: Blob,
    var idTipoUsuario: Number,
    var idSeguro: Number
)
