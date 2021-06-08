package es.wokis.routing

import es.wokis.data.dto.HorasFichadasDTO
import es.wokis.data.repository.HorasFichadasRepository
import es.wokis.data.repository.UserRepository
import es.wokis.utils.user
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.DI
import org.kodein.di.instance

fun Route.ficharRouting(di: DI) {
    val userRepository: UserRepository by di.instance("userRepo")
    val horasFichadasRepository: HorasFichadasRepository by di.instance("horasFichadasRepo")
    authenticate {
        route("/fichaje") {
            put {
                val fichajeDTO = call.receive<HorasFichadasDTO>()

                if (horasFichadasRepository.modificarDato(fichajeDTO) != null) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete {
                val fichajeDTO = call.receive<HorasFichadasDTO>()

                if (horasFichadasRepository.eliminarDato(fichajeDTO)) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
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

        route("/fichar") {
            post {
                val callUser = call.user

                if (callUser != null) {
                    val horasFichadasDTO = horasFichadasRepository.fichar(callUser)

                    if (horasFichadasDTO != null) {
                        call.respond(horasFichadasDTO)
                    } else {
                        call.respond(HttpStatusCode.NotFound, callUser.username)
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }

        post("/desfichar") {
            val callUser = call.user

            val horasFichadas = call.receive<HorasFichadasDTO>()

            if (callUser != null) {
                val horasFichadasDTO = horasFichadasRepository.desfichar(horasFichadas)

                if (horasFichadasDTO != null) {
                    call.respond(horasFichadasDTO)
                } else {
                    call.respond(HttpStatusCode.NotFound, callUser.username)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}