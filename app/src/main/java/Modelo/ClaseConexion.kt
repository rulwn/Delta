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
val url = "jdbc:oracle:thin:@192.168.1.7:1521:xe"
            val usuario = "c##_practica2"
            val clave = "practica"
            val conexion = DriverManager.getConnection(url,usuario,clave)
*/

/*
Luz


Hector


Jorge
"jdbc:oracle:thin:@192.168.1.5:1521:xe"

Raul


Hurtado
val url = "jdbc:oracle:thin:@192.168.1.7:1521:xe"

Huezo
val url = "jdbc:oracle:thin:@192.168.1.12:1521:xe"
*/