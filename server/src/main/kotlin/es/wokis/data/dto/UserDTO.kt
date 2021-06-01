package es.wokis.data.dto

data class UserDTO(
    val id: Int,
    val username: String,
    val nombre: String?,
    val apellidos: String?,
    val direccion: String?,
    val empresa: EmpresaDTO?
)

