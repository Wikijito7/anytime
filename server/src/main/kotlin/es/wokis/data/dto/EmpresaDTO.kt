package es.wokis.data.dto

import com.google.gson.annotations.Expose

data class EmpresaDTO(
    val id: Int, val name: String,
    val direccion: String?, val piso: String?,
    val logo: String?,
    @Expose(serialize = false) val users: List<UserDTO>
)
