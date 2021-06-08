package es.wokis.routing

import es.wokis.data.repository.EmpresaRepository
import es.wokis.services.ImageService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.DI
import org.kodein.di.instance

fun Route.empresaRouting(di: DI) {
    val empresaRepository: EmpresaRepository by di.instance("empresaRepo")
    val imageService: ImageService by di.instance("imageService")
    // TODO: 8/6/21 Acabar de implementar esto

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
}