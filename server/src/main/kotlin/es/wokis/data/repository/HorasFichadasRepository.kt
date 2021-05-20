package es.wokis.data.repository

import es.wokis.data.dto.HorasFichadasDTO
import es.wokis.data.dto.UserDTO
import es.wokis.data.models.HorasFichadasObj
import es.wokis.data.models.TipoHoraFichada
import es.wokis.data.models.User
import es.wokis.data.repository.interfaces.IHorasFichadasRepository
import es.wokis.utils.toHorasFichadasDTO
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class HorasFichadasRepository : IHorasFichadasRepository {
    override fun fichar(user: User, tipoHoraFichada: TipoHoraFichada): HorasFichadasDTO? {
        var fichaje: HorasFichadasDTO? = null

        transaction {
            val horaFichadaDB = HorasFichadasObj.new {
                this.user = user
                this.empresa = user.empresa
                this.tipo = tipoHoraFichada
                this.createdOn = LocalDateTime.now()
            }

            fichaje = horaFichadaDB.toHorasFichadasDTO()
        }

        return fichaje
    }

    override fun horasFichadas(user: UserDTO): List<HorasFichadasDTO> {
        TODO("Not yet implemented")
    }

    override fun modificarDato(old: HorasFichadasDTO, new: HorasFichadasDTO): HorasFichadasDTO {
        TODO("Not yet implemented")
    }
}