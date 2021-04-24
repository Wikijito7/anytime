package es.wokis.plugins

import es.wokis.dto.LoginUserDTO

import es.wokis.repository.UserRepository
import es.wokis.utils.user
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/login") {
            val user = call.receive<LoginUserDTO>()
            print("user is $user")
            val token: String? = UserRepository().login(user)

            if (token != null) {
                call.respond(HttpStatusCode.OK, token)
            } else {
                call.respond(HttpStatusCode.Forbidden, "Wrong username or password")
            }
        }

        authenticate {
            route("/asd") {
                get("auth") {
                    call.respondText("Auth!? name = ${call.user?.username}" +
                            ", pass= ${call.user?.password}")
                }
            }
        }
    }
}
