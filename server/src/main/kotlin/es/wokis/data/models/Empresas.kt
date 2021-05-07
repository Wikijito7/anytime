package es.wokis.data.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Empresas : IntIdTable() {
    val nombre = varchar("nombre", 30)
    val direccion = varchar("direccion", 60).nullable()
    val piso = integer("piso").nullable()

    val logo = varchar("logo", 60).nullable()
    val creador = reference("creador", Users, ReferenceOption.CASCADE)
}

class Empresa(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Empresa>(Empresas)

    val nombre by Empresas.nombre
    val direccion by Empresas.direccion
    val piso by Empresas.piso
    val logo by Empresas.logo
    val creador by User referencedOn Empresas.creador
}