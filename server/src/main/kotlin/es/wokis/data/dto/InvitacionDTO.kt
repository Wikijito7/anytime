package es.wokis.data.dto

import es.wokis.data.models.Empresa
import java.time.LocalDate

data class InvitacionDTO(val empresa: Empresa, val email: String, val fechaCreacion: LocalDate? = null)
