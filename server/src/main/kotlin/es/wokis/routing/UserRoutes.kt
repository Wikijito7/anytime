package es.wokis.routing

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

            route("/avatar") {
                post {
                    val multipartData = call.receiveMultipart()
                    val callUser = call.user

                    callUser?.let { user ->
                        multipartData.forEachPart {
                            if (it is PartData.FileItem) {
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

                }

                // fin avatar
            }

            route("/{username}") {
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
                // fin username
            }
        }
    }
}