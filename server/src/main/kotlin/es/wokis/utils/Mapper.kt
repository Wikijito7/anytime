package es.wokis.utils

import es.wokis.data.dto.EmpresaDTO
import es.wokis.data.dto.HorasFichadasDTO
import es.wokis.data.dto.UserDTO
import es.wokis.data.models.Empresa
import es.wokis.data.models.HorasFichadasObj
import es.wokis.data.models.User

fun Empresa.toEmpresaDTO(): EmpresaDTO {
    return EmpresaDTO(id.value, nombre, direccion, piso, logo, creador, users.map { it.toUserDTO() })
}

fun User.toUserDTO(): UserDTO {
    return UserDTO(username, password, nombre, apellidos, direccion, avatar, empresa.toEmpresaDTO())
}

fun HorasFichadasObj.toHorasFichadasDTO(): HorasFichadasDTO {
    return HorasFichadasDTO(user.toUserDTO(), empresa.toEmpresaDTO(), tipo, createdOn)
}
