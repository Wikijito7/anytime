package es.wokis.routing

import es.wokis.data.dto.InvitacionDTO
import es.wokis.data.repository.InvitacionesRepository
import es.wokis.services.EmailService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.DI
import org.kodein.di.instance

fun Route.invitacionRouting(di: DI) {
    val emailService: EmailService by di.instance("emailService")
    val invitacionesRepository: InvitacionesRepository by di.instance("invitacionesRepo")

    post("/email") {
        val invitacionDTO = call.receive<InvitacionDTO>()
        val invitacion = emailService.sendEmail(invitacionDTO)
        invitacionesRepository.crearInvitacion(invitacion)

        call.respond(invitacion)
    }
}