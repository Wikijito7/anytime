package es.wokis.plugins

import es.wokis.data.repository.EmpresaRepository
import es.wokis.data.repository.HorasFichadasRepository
import es.wokis.data.repository.InvitacionesRepository
import es.wokis.data.repository.UserRepository
import es.wokis.services.EmailService
import es.wokis.services.ImageService
import io.ktor.application.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun Application.initKodeIn(): DI {
    return DI {
        bind<UserRepository>(tag = "userRepo") with singleton { UserRepository() }
        bind<EmpresaRepository>(tag = "empresaRepo") with singleton { EmpresaRepository() }
        bind<HorasFichadasRepository>(tag = "horasFichadasRepo") with singleton { HorasFichadasRepository() }
        bind<InvitacionesRepository>(tag = "invitacionesRepo") with singleton { InvitacionesRepository() }
        bind<ImageService>(tag = "imageService") with singleton { ImageService() }
        bind<EmailService>(tag = "emailService") with singleton { EmailService() }
    }

}