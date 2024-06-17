package Modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun CadenaConexion(): Connection? {
        try {
            //val url = "jdbc:oracle:thin:@192.168.1.7:1521:xe"
            val url = "jdbc:oracle:thin:@10.10.1.1:1521:xe"
            val usuario = "c##_rodrigo"
            val clave = "momazos_salecianos"
            val conexion = DriverManager.getConnection(url,usuario,clave)
            return conexion
        } catch(e: Exception) {
            println("Error: $e")
            return null
        }
    }
}
