package Modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun CadenaConexion(): Connection? {
        try {
            val url = "jdbc:oracle:thin:@192.168.1.7:1521:xe"
            val usuario = "c##_paraDelta"
            val clave = "delta"
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
val url = "jdbc:oracle:thin:@192.168.1.6:1521:xe"

Jorge
"jdbc:oracle:thin:@192.168.1.5:1521:xe"

Raul


Hurtado
val url = "jdbc:oracle:thin:@192.168.1.7:1521:xe"

Huezo
val url = "jdbc:oracle:thin:@192.168.1.12:1521:xe"
*/