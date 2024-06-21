package Modelo

import android.content.Context
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class imgHandler {

    fun Uri_a_byteArray(context: Context, uri: Uri?): ByteArray? {
        return try {
            if(uri != null){
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)

                // Crea un ByteArrayOutputStream para almacenar los datos leídos
                val byteArrayOutputStream = ByteArrayOutputStream()

                // Crea un buffer para leer los datos
                val buffer = ByteArray(1024)
                var bytesRead: Int

                // Lee los datos del InputStream y escríbelos en el ByteArrayOutputStream
                while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead)
                }

                // Convierte el ByteArrayOutputStream a un byte[]
                byteArrayOutputStream.toByteArray()
            } else return null
            // Abre un InputStream desde el Uri

        } catch (e: Exception) {
            // Maneja cualquier excepción que ocurra durante la lectura
            e.printStackTrace()
            null
        }
    }
}