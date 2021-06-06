package es.wokis.data.repository

import es.wokis.data.dto.InvitacionDTO
import es.wokis.data.models.Empresa
import es.wokis.data.models.Invitacion
import es.wokis.data.models.Invitaciones
import es.wokis.data.repository.interfaces.IInvitacionesRepository
import es.wokis.utils.toInvitacionDTO
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class InvitacionesRepository : IInvitacionesRepository {
    override fun crearInvitacion(invitacion: InvitacionDTO): InvitacionDTO? {
        var finalInvitacion: InvitacionDTO? = null

            transaction {
                val empresa = Empresa.findById(invitacion.empresa.id) ?: return@transaction

                finalInvitacion = Invitacion.new {
                    email = invitacion.email
                    this.empresa = empresa
                    hash = invitacion.hash!!
                    createdOn = LocalDate.now()
                }.toInvitacionDTO()
            }

        return finalInvitacion
    }

    override fun eliminarInvitacion(invitacion: InvitacionDTO): Boolean {
        val invitacionDTO = getInvitacion(invitacion.hash!!)

        if (invitacionDTO != null) {
            return transaction {
                val invitacionBD = Invitacion.find { Invitaciones.hash eq invitacion.hash }
                    .singleOrNull() ?: return@transaction false

                invitacionBD.delete()
                true
            }
        }

        return false

    }

    override fun getInvitacion(hash: String): InvitacionDTO? {
        var invitacion: InvitacionDTO? = null
        transaction {
            val invitacionDB = Invitacion.find { Invitaciones.hash eq hash }.singleOrNull()

            if (invitacionDB != null) {
                invitacion = invitacionDB.toInvitacionDTO()
            }
        }

        return invitacion
    }

}