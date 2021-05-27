package es.wokis.data.repository

import es.wokis.data.dto.InvitacionDTO
import es.wokis.data.models.Invitacion
import es.wokis.data.models.Invitaciones
import es.wokis.data.repository.interfaces.IInvitacionesRepository
import es.wokis.utils.toEmpresa
import es.wokis.utils.toEmpresaDTO
import es.wokis.utils.toInvitacionDTO
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class InvitacionesRepository : IInvitacionesRepository {
    override fun crearInvitacion(invitacion: InvitacionDTO): InvitacionDTO? {
        var finalInvitacion: InvitacionDTO? = null
        val invitacionDTO = getInvitacion(invitacion.email)
        if (invitacionDTO == null) {
            transaction {
                Invitacion.new {
                    email = invitacion.email
                    empresa = invitacion.empresa.toEmpresa()
                    createdOn = LocalDate.now()
                }
            }
            finalInvitacion = invitacion
        }
        return finalInvitacion
    }

    override fun eliminarInvitacion(invitacion: InvitacionDTO): Boolean {
        val invitacionDTO = getInvitacion(invitacion.email)
        if (invitacionDTO != null) {
            transaction {
                Invitaciones.deleteWhere { Invitaciones.email eq invitacion.email }
            }
        } else {
            return false
        }
        return true
    }

    override fun getInvitacion(email: String): InvitacionDTO? {
        var invitacion: InvitacionDTO? = null
        transaction {
            val invitacionDB = Invitacion.find { Invitaciones.email eq email }.singleOrNull()

            if (invitacionDB != null) {
                invitacion = invitacionDB.toInvitacionDTO()
            }
        }
        return invitacion
    }

}