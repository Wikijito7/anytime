package es.wokis.data.repository.interfaces

import es.wokis.data.dto.InvitacionDTO

interface IInvitacionesRepository {
    fun crearInvitacion(invitacion: InvitacionDTO): InvitacionDTO?
    fun eliminarInvitacion(invitacion: InvitacionDTO): Boolean
    fun getInvitacion(hash: String) : InvitacionDTO?

}