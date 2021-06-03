package es.wokis.data.repository.interfaces

import es.wokis.data.dto.HorasFichadasDTO
import es.wokis.data.dto.LoginUserDTO
import es.wokis.data.dto.UserDTO

interface IHorasFichadasRepository {
    fun fichar(user: LoginUserDTO): HorasFichadasDTO?
    fun desfichar(horasFichadasDTO: HorasFichadasDTO): HorasFichadasDTO?
    fun horasFichadas(user: UserDTO): List<HorasFichadasDTO>
    fun modificarDato(old: HorasFichadasDTO, new: HorasFichadasDTO): HorasFichadasDTO
}