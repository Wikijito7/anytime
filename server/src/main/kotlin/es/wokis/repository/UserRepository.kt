package es.wokis.repository

import es.wokis.dto.LoginUserDTO
import es.wokis.dto.RegisterUserDTO
import es.wokis.dto.UserDTO
import es.wokis.models.Users
import es.wokis.plugins.makeToken
import es.wokis.repository.interfaces.IUserRespository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

class UserRepository: IUserRespository {
    override fun login(user: LoginUserDTO): String? {
        var userDB: ResultRow? = null
        transaction {
            userDB = Users.select { Users.username eq user.username }.single()
        }

        return when (BCrypt.checkpw(user.password, userDB?.get(Users.password))) {
            true -> makeToken(user)
            else -> null
        }
    }

    override fun register(user: RegisterUserDTO): String? {
        TODO("Not yet implemented")
    }

    override fun getUser(username: String): UserDTO {
        TODO("Not yet implemented")
    }

    override fun getAllUsersInCompany(companyName: String): Set<UserDTO> {
        TODO("Not yet implemented")
    }
}