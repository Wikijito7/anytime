package es.wokis.utils

import es.wokis.data.dto.*
import es.wokis.data.models.*
import org.jetbrains.exposed.dao.id.EntityID

fun Empresa.toEmpresaDTO(): EmpresaDTO {
    return EmpresaDTO(id.value, nombre, direccion, piso, logo, users.map { it.toUserDTOWE() })
}

fun User.toUserDTO(): UserDTO {
    return UserDTO(id.value, username, email, nombre, apellidos, direccion, avatar, empresa.toEmpresaDTO())
}

fun User.toLoginUserDTO(): LoginUserDTO {
    return LoginUserDTO(username, password)
}

// without empresa
fun User.toUserDTOWE(): UserDTO {
    return UserDTO(id.value, username, email, nombre, apellidos, direccion, avatar, null)
}

fun HorasFichadasObj.toHorasFichadasDTO(): HorasFichadasDTO {
    return HorasFichadasDTO(id.value, user.toUserDTOWE(), entrada, salida)
}

fun Invitacion.toInvitacionDTO(): InvitacionDTO {
    return InvitacionDTO(empresa.toEmpresaDTO(), email, hash, createdOn)
}

fun EmpresaDTO.toEmpresa(): Empresa {
    return Empresa(EntityID(id, Empresas))
}

fun UserDTO.toUser(): User {
    return User(EntityID(id, Users))
}