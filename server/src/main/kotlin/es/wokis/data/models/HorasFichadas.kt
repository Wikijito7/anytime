package es.wokis.data.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object HorasFichadas : IntIdTable() {
    val idUser = reference("id_user", Users)
    val idEmpresa = reference("id_empresa", Empresas)
//    val tipo = enumeration("type", TipoHoraFichada::class)
    val entrada = datetime("entrada")
    val salida = datetime("salida").nullable()

}

class HorasFichadasObj(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HorasFichadasObj>(HorasFichadas)
    var user by User referencedOn HorasFichadas.idUser
    var empresa by Empresa referencedOn HorasFichadas.idEmpresa
//    var tipo by HorasFichadas.tipo
    var entrada by HorasFichadas.entrada
    var salida by HorasFichadas.salida
}

enum class TipoHoraFichada(val type: Int) {
    ENTRADA(0),
    SALIDA(1)
}