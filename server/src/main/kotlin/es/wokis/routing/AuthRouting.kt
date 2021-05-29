package es.wokis.routing

import es.wokis.data.dto.LoginUserDTO
import es.wokis.data.dto.RegisterUserDTO
import es.wokis.data.repository.UserRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.DI
import org.kodein.di.instance


fun Route.authRouting(di: DI) {
    val userRepository: UserRepository by di.instance("userRepo")

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

}