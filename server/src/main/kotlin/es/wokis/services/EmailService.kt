package es.wokis.services

import es.wokis.data.dto.InvitacionDTO
import es.wokis.plugins.config
import es.wokis.utils.HashGenerator
import java.time.LocalDate
import java.util.*
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.math.log

class EmailService {
    private val remitente = config.getString("mail.user")
    private val passw = config.getString("mail.pass")

    private val asunto = "Invitacion"
    private val cuerpo = "Cuerpo"

    fun sendEmail(invitacion: InvitacionDTO): InvitacionDTO {
        val properties: Properties = System.getProperties()
        val hash = HashGenerator.generateHash(20)

        with(properties) {
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.user", remitente)
            put("mail.smtp.clave", passw)
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.port", "587")
        }

        val session = Session.getDefaultInstance(properties)
        val message = MimeMessage(session)

        try {
            with(message) {
                setFrom(InternetAddress(remitente))
                addRecipients(Message.RecipientType.TO, invitacion.email)
                subject = asunto
                setText(cuerpo)
            }

            val transport = session.getTransport("smtp")
            with(transport) {
                connect("smtp.gmail.com", remitente, passw)
                sendMessage(message, message.allRecipients)
                close()
            }
        } catch (e: MessagingException) {
            print(e.message)
        }

        return InvitacionDTO(invitacion.empresa, invitacion.email, hash, LocalDate.now())
    }
}