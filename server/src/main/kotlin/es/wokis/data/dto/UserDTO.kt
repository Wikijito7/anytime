package es.wokis.data.dto

import es.wokis.data.models.Role

data class UserDTO(
    val id: Int,
    val username: String,
    val email: String,
    val nombre: String?,
    val apellidos: String?,
    val direccion: String?,
    val avatar: String,
    val rol: Role,
    val empresa: EmpresaDTO?
)

