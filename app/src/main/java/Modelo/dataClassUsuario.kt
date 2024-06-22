package Modelo

import oracle.sql.BLOB
import oracle.sql.DATE
import java.sql.Blob
import java.sql.Date

data class dataClassUsuario(
<<<<<<< HEAD
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
=======
    var id_usuario: Int
>>>>>>> 2ef545d702929c36916867c697da790b3c812d3d
)
