package es.wokis.dto

import io.ktor.auth.*


data class LoginUserDTO(val username: String, val password: String): Principal