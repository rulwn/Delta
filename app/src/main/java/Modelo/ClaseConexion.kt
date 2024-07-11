package Modelo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    suspend fun CadenaConexion(): Connection? {
        return withContext(Dispatchers.IO) {
            try {
                val url = "jdbc:oracle:thin:@192.168.1.7:1521:xe"
                val usuario = "c##_usuarios_delta"
                val clave = "1234"
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
val url = "jdbc:oracle:thin:@192.168.1.6:1521:xe"

Jorge
"jdbc:oracle:thin:@192.168.1.5:1521:xe"

Raul


Hurtado
val url = "jdbc:oracle:thin:@192.168.1.7:1521:xe"

Huezo
val url = "jdbc:oracle:thin:@192.168.1.12:1521:xe"
*/