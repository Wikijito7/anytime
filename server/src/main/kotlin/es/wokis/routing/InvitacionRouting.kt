package es.wokis.routing

import es.wokis.data.dto.InvitacionDTO
import es.wokis.data.repository.InvitacionesRepository
import es.wokis.services.EmailService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.DI
import org.kodein.di.instance

fun Route.invitacionRouting(di: DI) {
    val emailService: EmailService by di.instance("emailService")
    val invitacionesRepository: InvitacionesRepository by di.instance("invitacionesRepo")

    route("/invite") {
        get("{hash}") {
            val hash = call.parameters["hash"]

            if (hash.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val invitacion = invitacionesRepository.getInvitacion(hash)

            if (invitacion != null) {
                call.respond(invitacion)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        delete {
            val invitacionDTO = call.receive<InvitacionDTO>()

            val response = invitacionesRepository.eliminarInvitacion(invitacionDTO)

            if (response) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

    post("/email") {
        val invitacionDTO = call.receive<InvitacionDTO>()
        val invitacion = emailService.sendEmail(invitacionDTO)
        invitacionesRepository.crearInvitacion(invitacion)

        call.respond(invitacion)
    }
}