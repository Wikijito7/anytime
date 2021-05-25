package es.wokis.data.models

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
    val avatar = varchar("avatar", 255).default("no-image")
    val codigoEmpresa = reference("id_empresa", Empresas, ReferenceOption.CASCADE)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)
    var username by Users.username
    var password by Users.password
    var email by Users.email
    var nombre by Users.nombre
    var apellidos by Users.apellidos
    var direccion by Users.direccion
    var avatar by Users.avatar
    var empresa by Empresa referencedOn Users.codigoEmpresa
}