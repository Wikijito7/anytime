package es.wokis.data.dto

import es.wokis.data.models.Role
import io.ktor.auth.*


data class LoginUserDTO(val username: String,
                        val password: String,
                        val role: Role = Role.USER): Principal