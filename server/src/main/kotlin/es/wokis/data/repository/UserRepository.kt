package es.wokis.data.repository

import es.wokis.data.dto.LoginUserDTO
import es.wokis.data.dto.RegisterUserDTO
import es.wokis.data.dto.UserDTO
import es.wokis.data.models.Users
import es.wokis.plugins.makeToken
import es.wokis.data.repository.interfaces.IUserRespository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

class UserRepository: IUserRespository {
    override fun login(user: LoginUserDTO): String? {
        var userDB: ResultRow? = null
        transaction {
            userDB = Users.select { Users.username eq user.username }.singleOrNull()
        }

        return when (BCrypt.checkpw(user.password, userDB?.get(Users.password))) {
            true -> makeToken(user)
            else -> null
        }
    }

    override fun register(user: RegisterUserDTO): String? {
        TODO("Not yet implemented")
    }

    override fun getUser(username: String): UserDTO? {
        TODO("Not yet implemented")
    }

    override fun getAllUsersInCompany(companyName: String): Set<UserDTO> {
        TODO("Not yet implemented")
    }
}