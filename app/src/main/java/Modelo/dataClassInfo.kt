package Modelo

data class dataClassDoctorInfo(
    val ID_Doctor: Int,
    val ID_Usuario: Int,
    val nombreUsuario: String,
    val apellidoUsuario: String,
    val imgUsuario: String,
    val nombreEspecialidad: String,
    val ID_Sucursal: Int,
    val nombreSucursal: String,
    val telefonoSucur: String,
    val direccionSucur: String,
    val longSucur: Double,
    val latiSucur: Double,
    val imgSucursal: String,
    val nombreServicio: String,
    val costo: Float
)