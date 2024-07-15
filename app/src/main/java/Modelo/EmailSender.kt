package Modelo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Transport
import javax.mail.Session
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
        //Iniciar sesión

        val session = Session.getInstance(props, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("delta.medic.help@gmail.com", "fkui jrmx sozv mfuk")
            }

        })
        try {
            val message = MimeMessage(session).apply {
                //Con que correo enviaré el mensaje
                setFrom(InternetAddress("delta.medic.help@gmail.com"))
                addRecipient(Message.RecipientType.TO, InternetAddress(receptor))
                subject = sujeto
                setText(mensaje)
            }
            Transport.send(message)
            println("Correo enviado satisfactoriamente")
        } catch (e: MessagingException) {
            e.printStackTrace()
            println("CORREO NO ENVIADO")
        }
    }
}