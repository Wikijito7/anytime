package es.wokis.data.dto

import com.google.gson.annotations.Expose
import es.wokis.data.models.User

data class EmpresaDTO(val id: Int, val name: String,
                      val direccion: String?, val piso: String?,
                      val logo: String?, val creador: User,
                      @Expose(serialize = false) val users: List<UserDTO>)
