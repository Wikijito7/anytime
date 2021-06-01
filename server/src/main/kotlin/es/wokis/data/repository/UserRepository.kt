package es.wokis.data.repository

import es.wokis.data.dto.LoginUserDTO
import es.wokis.data.dto.RegisterUserDTO
import es.wokis.data.dto.UserDTO
import es.wokis.data.models.Empresa
import es.wokis.data.models.User
import es.wokis.data.models.Users
import es.wokis.data.repository.interfaces.IUserRespository
import es.wokis.plugins.makeToken
import es.wokis.utils.toUserDTO
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import java.sql.SQLException

class UserRepository : IUserRespository {
    override fun login(user: LoginUserDTO): String? {
        var userDB: User? = null
        transaction {
            userDB = User.find { Users.username eq user.username }.singleOrNull()
        }

        if (userDB == null) return null

        return when (BCrypt.checkpw(user.password, userDB?.password)) {
            true -> makeToken(user)
            else -> null
        }
    }

    override fun register(user: RegisterUserDTO): String? {
        var userDB: User? = null
        transaction {
            userDB = User.find { Users.username eq user.username }.singleOrNull()
            val empresa = Empresa.findById(user.empresaId) ?: throw SQLException("${user.empresaId} not found")
            if (userDB == null) {
                User.new {
                    this.username = user.username
                    this.password = BCrypt.hashpw(user.password, BCrypt.gensalt())
                    this.email = user.email
                    this.empresa = empresa
                }
            }
        }

        return if (userDB == null)
                login(LoginUserDTO(user.username, user.password))
            else
                null
    }

    override fun getUser(username: String): UserDTO? {
        var user: UserDTO? = null

        transaction {
            val userDB = User.find { Users.username eq username }.singleOrNull()

            user = userDB?.toUserDTO()
        }
        return user
    }

    override fun changeAvatar(username: String, avatar: String) {
        transaction {
            val user = User.find { Users.username eq username }.singleOrNull()
            println("------------ user is: $user")
            if (user == null) {
                println(username)
                return@transaction
            }

            user.avatar = avatar
            commit()
        }
    }
}