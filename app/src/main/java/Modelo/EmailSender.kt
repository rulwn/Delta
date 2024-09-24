package Modelo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailSender {

    suspend fun enviarCorreo(receptor: String, sujeto: String, mensaje: String) = withContext(Dispatchers.IO) {
        val props = Properties().apply {
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.socketFactory.port", "465")
            put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            put("mail.smtp.auth", "true")
            put("mail.smtp.port", "465")
        }

        val session = Session.getInstance(props, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("delta.medic.help@gmail.com", "fkui jrmx sozv mfuk")
            }
        })

        try {
            // Crear HTML dinámico según la longitud del código de recuperación
            val mensajeHtml = """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #FFFFFF; margin: 0; padding: 0;">
                    <div style="background-color: #FFFFFF; width: 100%; max-width: 600px; margin: 0 auto; padding: 20px; margin-top: 10px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.45); text-align: center;">
                        <img src="https://i.imgur.com/Ayfay3P.png" alt="DeltaMedic Logo" style="margin-top: 20px; width: 110px; max-width: 120px;">
                        <h1 style="font-size: 24px; color: #042A60; font-weight: bold; margin-bottom: 10px;">Recuperación de contraseña</h1>
                        <p style="font-size: 20px; color: #000000; margin-bottom: 20px;">Su código de recuperación es:</p>
                        <div style="white-space: nowrap; overflow: hidden;">
                            ${generarCamposDeCodigo(mensaje)}
                        </div>
                        <p style="font-size: 14px; color: #000000; margin-top: 20px;">Este código es válido por un tiempo limitado. Use este código para restablecer su contraseña.</p>
                    </div>
                </body>
                </html>
            """.trimIndent()

            val message = MimeMessage(session).apply {
                // Correo remitente
                setFrom(InternetAddress("delta.medic.help@gmail.com"))
                // Destinatario
                addRecipient(Message.RecipientType.TO, InternetAddress(receptor))
                // Asunto del correo
                subject = sujeto
                // Configurar el contenido HTML
                setContent(mensajeHtml, "text/html; charset=utf-8")
            }
            Transport.send(message)
            println("Correo enviado satisfactoriamente")
        } catch (e: MessagingException) {
            e.printStackTrace()
            println("CORREO NO ENVIADO")
        }
    }

    // Función que genera los campos de código HTML dinámicamente según la longitud del mensaje
    private fun generarCamposDeCodigo(mensaje: String): String {
        val sb = StringBuilder()

        // Crear un span por cada dígito del código con un tamaño más pequeño
        mensaje.forEach { digito ->
            sb.append("""
                <span style="font-size: 30px; color: #fff; background-color: #000; padding: 8px; display: inline-block; margin: 4px; border-radius: 5px; width: 40px; text-align: center;">$digito</span>
            """.trimIndent())
        }

        return sb.toString()
    }
}