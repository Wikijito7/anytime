package es.wokis.data.repository

import es.wokis.data.dto.LoginUserDTO
import es.wokis.data.dto.RegisterUserDTO
import es.wokis.data.dto.UserDTO
import es.wokis.data.models.Empresa
import es.wokis.data.models.Role
import es.wokis.data.models.User
import es.wokis.data.models.Users
import es.wokis.data.repository.interfaces.IUserRespository
import es.wokis.plugins.makeToken
import es.wokis.utils.toLoginUserDTO
import es.wokis.utils.toUserDTO
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import java.sql.SQLException

class UserRepository : IUserRespository {
    override fun login(user: LoginUserDTO): String? {
        val userDB = transaction {
            return@transaction User.find { Users.username eq user.username }.singleOrNull()
        } ?: return null

        return when (BCrypt.checkpw(user.password, userDB.password)) {
            true -> makeToken(userDB.toLoginUserDTO())
            else -> null
        }
    }

    override fun register(user: RegisterUserDTO): String? {
        // TODO: Revisar
        val userDB = transaction {
            val userDB = User.find { Users.username eq user.username }.singleOrNull()
            val empresa = Empresa.findById(user.empresaId) ?: throw SQLException("${user.empresaId} not found")

            if (userDB == null) {
                return@transaction User.new {
                    this.username = user.username
                    this.password = BCrypt.hashpw(user.password, BCrypt.gensalt())
                    this.email = user.email
                    this.empresa = empresa
                }
            } else {
                return@transaction null
            }
        }

        return if (userDB != null)
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
            val user = User.find { Users.username eq username }.singleOrNull() ?: return@transaction

            user.avatar = avatar
            commit()
        }
    }

    override fun removeUser(username: String): String? {
        return transaction {
            val userDB = User.find { Users.username eq username }.firstOrNull() ?: return@transaction null

            userDB.delete()

            username
        }
    }

    override fun changeRole(username: String, role: Role): UserDTO? {
        return transaction {
            val userDB = User.find { Users.username eq username }.singleOrNull() ?: return@transaction null
            userDB.rol = role
            commit()

            userDB.toUserDTO()
        }
    }

    override fun updateUser(userDTO: UserDTO): UserDTO? {
        return transaction {
            val user = User.findById(userDTO.id) ?: return@transaction null

            user.let {
                it.email = userDTO.email
                it.nombre = userDTO.nombre
                it.apellidos = userDTO.apellidos
                it.direccion = userDTO.direccion
            }
            commit()

            user.toUserDTO()
        }
    }
}