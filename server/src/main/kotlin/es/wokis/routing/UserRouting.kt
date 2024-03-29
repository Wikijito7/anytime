package es.wokis.routing

import es.wokis.data.dto.UserDTO
import es.wokis.data.models.Role
import es.wokis.data.repository.UserRepository
import es.wokis.services.ImageService
import es.wokis.utils.user
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.DI
import org.kodein.di.instance

fun Route.userRouting(di: DI) {
    val userRepository: UserRepository by di.instance("userRepo")
    val imageService: ImageService by di.instance("imageService")

    route("/user/{username}/avatar") {
        get {
            val username = call.parameters["username"]

            username?.let {
                val user = userRepository.getUser(username) ?: return@let
                call.respondFile(imageService.getAvatar(user.avatar))
            }
        }
    }

    authenticate {
        route("/user") {
            get {
                val callUser = call.user
                callUser?.let {
                    val userDTO = userRepository.getUser(callUser.username)

                    if (userDTO != null) {
                        call.respond(userDTO)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
            }

            put {
                val userDTO = call.receive<UserDTO>()

                if (userRepository.updateUser(userDTO) != null) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            route("/avatar") {
                post {
                    val multipartData = call.receiveMultipart()
                    val callUser = call.user

                    callUser?.let { user ->
                        multipartData.forEachPart {
                            if (it is PartData.FileItem) {
                                if (!it.contentType.toString().startsWith("image")) {
                                    call.respond(HttpStatusCode.BadRequest)
                                    return@forEachPart
                                }

                                val avatarPath: String = imageService.insertAvatar(user.username, it)
                                if (avatarPath.isNotBlank()) {
                                    userRepository.changeAvatar(user.username, avatarPath)
                                    call.respondFile(imageService.getAvatar(avatarPath))
                                }
                            }
                        }
                    }

                }

                delete {
                    val callUser = call.user

                    callUser?.let {
                        userRepository.changeAvatar(it.username, "no-image")
                        call.respondFile(imageService.getAvatar("no-image"))
                    }
                }
            }

            route("/{username}") {
                get {
                    val username = call.parameters["username"]
                    val callUser = userRepository.getUser(call.user!!.username)

                    if (callUser == null || callUser.rol != Role.ADMIN) {
                        call.respond(HttpStatusCode.Unauthorized)
                        return@get
                    }

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

                delete {
                    val callUser = userRepository.getUser(call.user!!.username)
                    val username = call.parameters["username"]

                    if (callUser == null || callUser.rol != Role.ADMIN) {
                        call.respond(HttpStatusCode.Unauthorized)
                        return@delete
                    }

                    if (username.isNullOrBlank()) {
                        call.respond(HttpStatusCode.BadRequest, "Username must be given.")

                    } else {
                        val user = userRepository.removeUser(username)

                        if (user != null) {
                            call.respond(user)

                        } else {
                            call.respond(HttpStatusCode.NotFound, username)
                        }
                    }
                }

                put("/role") {
                    val role = call.receive<Role>()
                    val callUser = call.user
                    val username = call.parameters["username"]

                    callUser?.let { user ->
                        if (user.role != Role.ADMIN) {
                            call.respond(HttpStatusCode.Unauthorized)
                            return@put
                        }

                        if (username.isNullOrBlank()) {
                            call.respond(HttpStatusCode.BadRequest)
                            return@put
                        }

                        val userDTO = userRepository.changeRole(username, role)

                        if (userDTO != null) {
                            call.respond(userDTO)
                        } else {
                            call.respond(HttpStatusCode.NotFound)
                        }
                    }
                }
                // fin username
            }
        }
    }
}