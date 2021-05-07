package es.wokis.data.models

import io.ktor.auth.*
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Users: IntIdTable() {
    val username = varchar("username", 20).uniqueIndex()
    val password = varchar("password", 60)
    val email = varchar("email", 60).uniqueIndex()
    val nombre = varchar("nombre", 30).nullable()
    val apellidos = varchar("apellidos", 60).nullable()
    val direccion = varchar("direccion", 100).nullable()
    val avatar = varchar("avatar", 255)
    val codigoEmpresa = reference("id_empresa", Empresas, ReferenceOption.CASCADE)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)
    val username by Users.username
    val password by Users.password
    val email by Users.email
    val nombre by Users.nombre
    val apellidos by Users.apellidos
    val direccion by Users.direccion
    val avatar by Users.avatar
    val codigoEmpresa by Empresa referencedOn Users.codigoEmpresa
}