package es.wokis.routing

import es.wokis.data.repository.HorasFichadasRepository
import es.wokis.data.repository.UserRepository
import es.wokis.utils.tipoFichaje
import es.wokis.utils.toUser
import es.wokis.utils.user
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.DI
import org.kodein.di.instance

fun Route.ficharRouting(di: DI) {
    val userRepository: UserRepository by di.instance("userRepo")
    val horasFichadasRepository: HorasFichadasRepository by di.instance("horasFichadasRepo")

    get("/fichaje/{username}") {
        val username = call.parameters["username"]

        if (username != null) {
            val userDTO = userRepository.getUser(username)

            if (userDTO != null) {
                val fichaje = horasFichadasRepository.horasFichadas(userDTO)

                call.respond(fichaje)

            } else {
                call.respond(HttpStatusCode.NotFound, username)
            }

        } else {
            call.respond(HttpStatusCode.BadRequest, "Username must be given")
        }
    }

    post("/fichar/{type}") {
        val callUser = call.user
        val type = call.parameters["type"]

        if (callUser != null && !type.isNullOrBlank() && tipoFichaje(type) != null) {
            val user = userRepository.getUser(callUser.username)?.toUser()
            val tipoFichaje = tipoFichaje(type)

            if (user != null && tipoFichaje != null) {
                val horasFichadasDTO = horasFichadasRepository.fichar(user, tipoFichaje)
                call.respond(horasFichadasDTO)
            } else {
                call.respond(HttpStatusCode.BadRequest, "$type is not valid")
            }

        } else {
            call.respond(HttpStatusCode.BadRequest, "$type is not valid")
        }
    }
}