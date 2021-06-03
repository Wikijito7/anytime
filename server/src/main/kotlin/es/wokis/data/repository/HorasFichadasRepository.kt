package es.wokis.data.repository

import es.wokis.data.dto.HorasFichadasDTO
import es.wokis.data.dto.LoginUserDTO
import es.wokis.data.dto.UserDTO
import es.wokis.data.models.HorasFichadas
import es.wokis.data.models.HorasFichadasObj
import es.wokis.data.models.User
import es.wokis.data.models.Users
import es.wokis.data.repository.interfaces.IHorasFichadasRepository
import es.wokis.utils.toHorasFichadasDTO
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class HorasFichadasRepository : IHorasFichadasRepository {
    override fun fichar(user: LoginUserDTO): HorasFichadasDTO? {
        // devolucion de la funcion
        return transaction {
            val userDB = User.find { Users.username eq user.username }.singleOrNull() ?: return@transaction null
            // devolución en la transacción
            return@transaction HorasFichadasObj.new {
                 this.user = userDB
                 empresa = userDB.empresa
                 this.entrada = LocalDateTime.now()
                 this.salida = null
             }.toHorasFichadasDTO()

        }
    }

    override fun desfichar(horasFichadasDTO: HorasFichadasDTO): HorasFichadasDTO? {
        return transaction {
            val horaFichada = HorasFichadasObj.findById(horasFichadasDTO.id) ?: return@transaction null

            horaFichada.salida = LocalDateTime.now()
            commit()

            return@transaction horaFichada.toHorasFichadasDTO()

        }
    }

    override fun horasFichadas(user: UserDTO): List<HorasFichadasDTO> {
        val fichajesList: MutableList<HorasFichadasDTO> = mutableListOf()

        transaction {
            val horasFichadas = HorasFichadasObj.find { HorasFichadas.idUser eq user.id }

            fichajesList.addAll(horasFichadas.map { it.toHorasFichadasDTO() })
        }

        return fichajesList
    }

    override fun modificarDato(old: HorasFichadasDTO, new: HorasFichadasDTO): HorasFichadasDTO {
        TODO("Not yet implemented")
    }
}