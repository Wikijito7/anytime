package es.wokis.data.dto

import java.time.LocalDate

data class InvitacionDTO(val empresa: EmpresaDTO, val email: String, val fechaCreacion: LocalDate? = null)
