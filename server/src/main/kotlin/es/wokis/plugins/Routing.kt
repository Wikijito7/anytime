package es.wokis.plugins

import es.wokis.data.repository.EmpresaRepository
import es.wokis.data.repository.HorasFichadasRepository
import es.wokis.data.repository.InvitacionesRepository
import es.wokis.data.repository.UserRepository
import es.wokis.routing.*
import es.wokis.services.EmailService
import es.wokis.services.ImageService
import es.wokis.utils.user
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.DI

fun Application.configureRouting(di: DI) {
    val userRepository = UserRepository()
    val empresaRepository = EmpresaRepository()
    val horasFichadasRepository = HorasFichadasRepository()
    val invitacionesRepository = InvitacionesRepository()
    val imageService = ImageService()
    val emailService = EmailService()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        authRouting(di)
        userRouting(di)
        empresaRouting(di)
        ficharRouting(di)
        invitacionRouting(di)

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
        }
    }
}
