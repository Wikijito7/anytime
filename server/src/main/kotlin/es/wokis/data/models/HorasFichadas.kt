package es.wokis.data.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.`java-time`.datetime

object HorasFichadas : IntIdTable() {
    val idUser = reference("id_user", Users)
    val idEmpresa = reference("id_empresa", Empresas)
    val tipo = enumeration("type", TipoHoraFichada::class)
    val createdOn = datetime("created_on")
}

class HorasFichadasObj(id: EntityID<Int>) : IntEntity(id) {
    val idUser by User referencedOn HorasFichadas.idUser
    val idEmpresa by Empresa referencedOn HorasFichadas.idEmpresa
    val tipo by HorasFichadas.tipo
    val createdOn by HorasFichadas.createdOn
}

enum class TipoHoraFichada(val type: Int) {
    ENTRADA(0),
    SALIDA(1)
}