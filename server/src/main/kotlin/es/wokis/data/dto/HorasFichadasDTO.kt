package es.wokis.data.dto

import java.time.LocalDateTime

data class HorasFichadasDTO(val id: Int,
                            val user: UserDTO,
                            val entrada: LocalDateTime,
                            val salida: LocalDateTime?)
