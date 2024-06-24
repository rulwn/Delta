package Modelo

import java.security.MessageDigest //Esta libreria incluye funciones para implementar
//algoritmos hash
class Encrypter {
    fun encrypt(pass: String): String {
        val bytes = pass.toByteArray() //convertimos la contraseña a un arreglo de bytes,
        //porque SHA-256 trabaja con datos binarios, no con strings directamente
        val md = MessageDigest.getInstance("SHA-256") //Especificamos en que algortimo
        //vamos a encriptar
        val digested = md.digest(bytes) //tomamos el arreglo de bytes y le aplicamos el algoritmo
        //luego devuelve un nuevo arreglo, que es el resultado del hash

        return digested.joinToString("") {"%02x".format(it)}
        /*

        joinToString(""): convierte el arreglo de bytes en una sola cadena de texto
        al darle "" como argumento indicamos que no queremos ningun separador

        "%02x".format(it): "%02x" es una especificación de formato y convierte cada
        byte en su representación hexadecimal de 2 digitos
                            it se refiere a cada byte del arreglo
         */
    }

    fun decrypt(attempt: String, actual_pass: String ): Boolean {
        return encrypt(attempt) == actual_pass
        /*
        Esta función toma 2 strings, el intento de ingreso y la contraseña real
        encriptada en SHA-256, retorna true si la encriptación del intento es identica
        a la contraseña almacenada
         */
    }
}