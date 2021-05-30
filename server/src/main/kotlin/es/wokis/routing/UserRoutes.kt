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
    authenticate {
        route("/user") {
            get {
                // TODO: 30/05/2021 Añadir el soporte
            }
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
    }
}