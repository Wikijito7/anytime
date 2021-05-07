package es.wokis.data.dto

import es.wokis.data.models.Empresa

data class UserDTO(val username: String, val password: String, val nombre: String?, val apellidos: String?,
                   val direccion: String?, val avatar: String?, val empresa: Empresa)

