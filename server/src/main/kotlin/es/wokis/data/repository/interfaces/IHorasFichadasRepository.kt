package es.wokis.data.repository.interfaces

import es.wokis.data.dto.HorasFichadasDTO

interface IHorasFichadasRepository {
    fun fichar(idUser: Int): HorasFichadasDTO
    fun desfichar(idUser: Int): HorasFichadasDTO
    fun horasFichadas(idUser: Int): List<HorasFichadasDTO>
    fun modificarDato(old: HorasFichadasDTO, new: HorasFichadasDTO)
}