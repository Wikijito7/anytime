package es.wokis.utils

import es.wokis.dto.LoginUserDTO
import io.ktor.application.*
import io.ktor.auth.*

val ApplicationCall.user: LoginUserDTO? get() = authentication.principal()