package es.wokis.data.repository.interfaces

import es.wokis.data.dto.HorasFichadasDTO
import es.wokis.data.dto.UserDTO
import es.wokis.data.models.TipoHoraFichada
import es.wokis.data.models.User

interface IHorasFichadasRepository {
    fun fichar(user: User, tipo: TipoHoraFichada): HorasFichadasDTO?
    fun horasFichadas(user: UserDTO): List<HorasFichadasDTO>
    fun modificarDato(old: HorasFichadasDTO, new: HorasFichadasDTO): HorasFichadasDTO
}