package es.wokis.utils

import es.wokis.data.dto.LoginUserDTO
import es.wokis.data.models.TipoHoraFichada
import io.ktor.application.*
import io.ktor.auth.*

val ApplicationCall.user: LoginUserDTO? get() = authentication.principal()


fun tipoFichaje(tipo: String): TipoHoraFichada? = when (tipo) {
    "entrada" -> {
        TipoHoraFichada.ENTRADA
    }
    "salida" -> {
        TipoHoraFichada.SALIDA
    }
    else -> {
        null
    }
}
