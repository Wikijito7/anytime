package es.wokis.data.dto

import es.wokis.data.models.TipoHoraFichada
import java.time.LocalDateTime

data class HorasFichadasDTO(val user: UserDTO,
                            val empresa: EmpresaDTO,
                            val type: TipoHoraFichada,
                            val fecha: LocalDateTime)
