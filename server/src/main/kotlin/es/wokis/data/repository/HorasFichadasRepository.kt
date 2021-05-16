package es.wokis.data.repository

import es.wokis.data.dto.HorasFichadasDTO
import es.wokis.data.repository.interfaces.IHorasFichadasRepository

class HorasFichadasRepository private constructor() : IHorasFichadasRepository {
    override fun fichar(idUser: Int): HorasFichadasDTO {
        TODO("Not yet implemented")
    }

    override fun desfichar(idUser: Int): HorasFichadasDTO {
        TODO("Not yet implemented")
    }

    override fun horasFichadas(idUser: Int): List<HorasFichadasDTO> {
        TODO("Not yet implemented")
    }

    override fun modificarDato(old: HorasFichadasDTO, new: HorasFichadasDTO) {
        TODO("Not yet implemented")
    }

    companion object {
        @Volatile
        var INSTANCE: HorasFichadasRepository? = null

        fun getInstance(): HorasFichadasRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = HorasFichadasRepository()
                INSTANCE = instance
                instance
            }
        }
    }
}