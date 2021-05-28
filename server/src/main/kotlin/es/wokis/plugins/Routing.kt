package es.wokis.plugins

import es.wokis.data.dto.*
import es.wokis.data.repository.EmpresaRepository
import es.wokis.data.repository.HorasFichadasRepository
import es.wokis.data.repository.UserRepository
import es.wokis.services.EmailService
import es.wokis.services.ImageService
import es.wokis.utils.tipoFichaje
import es.wokis.utils.toUser
import es.wokis.utils.user
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    val userRepository = UserRepository()
    val empresaRepository = EmpresaRepository()
    val horasFichadasRepository = HorasFichadasRepository()
    val imageService = ImageService()
    val emailService = EmailService()

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
            route("/user") {
                route("{username}") {
                    get {
                        val username = call.parameters["username"]
                        val callUser = call.user

//                if (callUser != null && (username == callUser.username || callUser.role == Roles.ADMIN))

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

                    route("avatar") {
                        get {
                            val callUser = call.user
                            val username = call.parameters["username"]
                            print(username)


                            username?.let {
                                val user = userRepository.getUser(username) ?: return@let

                                call.respondFile(imageService.getAvatar(user.avatar))
                            }
                        }

                        post {
                            val multipartData = call.receiveMultipart()
                            val callUser = call.user

                            callUser?.let { user ->
                                multipartData.forEachPart {
                                    if (it is PartData.FileItem) {
                                        val avatarPath: String = imageService.insertAvatar(user.username, it)
                                        if (!avatarPath.isBlank()) {
                                            userRepository.changeAvatar(user.username, avatarPath)
                                            call.respond(imageService.getAvatar(avatarPath))
                                        }


                                    }
                                }
                            }

                        }

                        delete {

                        }

                        // fin avatar
                    }
                    // fin username
                }
            }

            route("/empresa") {
                get("{nombre}") {
                    val nombreEmpresa = call.parameters["nombre"]

                    if (!nombreEmpresa.isNullOrBlank()) {
                        val empresaDTO = empresaRepository.getEmpresa(nombreEmpresa)

                        if (empresaDTO != null) {
                            call.respond(empresaDTO)

                        } else {
                            call.respond(HttpStatusCode.NotFound, "$nombreEmpresa")
                        }

                    } else {
                        call.respond(HttpStatusCode.BadRequest, "Name is required")
                    }
                }

                post {

                }

                put {

                }

                delete {

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

            post("email") {
                val empresaDTO = EmpresaDTO(1, "Test", null, null, null, listOf<UserDTO>())
                val invitacion = emailService.sendEmail(InvitacionDTO(empresaDTO, "antoniojoselojoojeda@gmail.com"))

                call.respond(invitacion)
            }
        }
    }
}
