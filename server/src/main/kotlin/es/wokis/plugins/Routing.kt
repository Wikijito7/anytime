package es.wokis.plugins

import es.wokis.data.dto.LoginUserDTO
import es.wokis.data.dto.RegisterUserDTO
import es.wokis.data.repository.EmpresaRepository
import es.wokis.data.repository.HorasFichadasRepository
import es.wokis.data.repository.UserRepository
import es.wokis.utils.user
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    val userRepository = UserRepository()
    val empresaRepository = EmpresaRepository()
    val horasFichadasRepository = HorasFichadasRepository()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        // Login / Register

        post("/login") {
            val user = call.receive<LoginUserDTO>()
            val token: String? = userRepository.login(user)

            if (token != null) {
                call.respond(HttpStatusCode.OK, token)
            } else {
                call.respond(HttpStatusCode.Forbidden, "Wrong username or password")
            }
        }

        post("/register") {
            val user = call.receive<RegisterUserDTO>()

            val token: String? = userRepository.register(user)

            if (token != null) {
                call.respond(HttpStatusCode.OK, token)
            } else {
                call.respond(HttpStatusCode.Conflict, "That user already exists")
            }

        }

        // si no está autorizado, 401.
        authenticate {
            route("/asd") {
                get("auth") {
                    call.respondText(
                        "Auth!? name = ${call.user?.username}" +
                                ", pass= ${call.user?.password}"
                    )
                }
            }

            // perfil usuario x, TODO: Añadir roles.
            get("/user/{username}") {
                val username = call.parameters["username"]

                if (username.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Username must be given.")

                } else {
                    val user = userRepository.getUser(username)

                    if (user != null) {
                        call.respond(user)

                    } else {
                        call.respond(HttpStatusCode.NotFound, username)
                    }
                }
            }

            post("/fichar") {
                val user = call.user

                if (user != null) {

//                    horasFichadasRepository.fichar()
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
        }
    }
}
