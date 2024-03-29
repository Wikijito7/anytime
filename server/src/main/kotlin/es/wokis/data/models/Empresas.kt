package es.wokis.data.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Empresas : IntIdTable() {
    val nombre = varchar("nombre", 30).uniqueIndex()
    val direccion = varchar("direccion", 60).nullable()
    val piso = varchar("piso", 10).nullable()

    val logo = varchar("logo", 60).nullable()
}

class Empresa(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Empresa>(Empresas)

    var nombre by Empresas.nombre
    var direccion by Empresas.direccion
    var piso by Empresas.piso
    var logo by Empresas.logo

    //    var creador by User referencedOn Empresas.creador
    val users by User referrersOn Users.codigoEmpresa
}