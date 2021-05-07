package es.wokis.data.dto

import java.time.LocalDateTime

data class HorasFichadasDTO(val idUser: Int, val idEmpresa: Int, val type: Int, val fecha: LocalDateTime)
