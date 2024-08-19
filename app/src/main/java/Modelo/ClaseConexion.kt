package Modelo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    suspend fun cadenaConexion(): Connection? {
        return withContext(Dispatchers.IO) {
            try {

                val url = "jdbc:oracle:thin:@192.168.1.5:1521:xe"
                val usuario = "DeltaMed" //DeltaMed
                val clave = "deltaTeam1" //deltaTeam1
                val conexion = DriverManager.getConnection(url, usuario, clave)
                conexion
            } catch (e: Exception) {
                println("Error: $e")
                null
            }
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


Alejandro
val url = "jdbc:oracle:thin:@192.168.1.23:1521:xe"

Jorge
"jdbc:oracle:thin:@192.168.1.5:1521:xe"

Raul colegio
val url = "jdbc:oracle:thin:@10.10.1.1:1521:xe"
y normal
//val url = "jdbc:oracle:thin:@192.168.31.105:1521:xe"

Huezo
val url = "jdbc:oracle:thin:@192.168.1.12:1521:xe"
*/