package es.wokis.data.dto

data class RegisterUserDTO(val email: String,
                           val username: String,
                           val password: String,
                           val empresaId: Int)