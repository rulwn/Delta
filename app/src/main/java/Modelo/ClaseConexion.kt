package Modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun CadenaConexion(): Connection? {
        try {
            val url = "jdbc:oracle:thin:@192.168.1.12:1521:xe"
            val usuario = "DeltaMed"
            val clave = "deltaTeam1"
            val conexion = DriverManager.getConnection(url,usuario,clave)
            return conexion
        } catch(e: Exception) {
            println("Error: $e")
            return null
        }
    }
}
/*
Luz


Hector


Jorge


Raul


Hurtado
val url = "jdbc:oracle:thin:@192.168.1.7:1521:xe"

Huezo
val url = "jdbc:oracle:thin:@192.168.1.1:1521:xe"
*/