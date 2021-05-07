package es.wokis.data.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.date

object Invitaciones : IntIdTable() {
    val email = varchar("email",100)
    val idEmpresa = integer("idEmpresa")
    val createdOn = date("created_on")

}

class Invitacion(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Invitacion>(Invitaciones)
    var email by Invitaciones.email
    var empresa by Empresa referencedOn Invitaciones.idEmpresa
    var createdOn by Invitaciones.createdOn
}