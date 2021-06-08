package es.wokis.data.repository.interfaces

import es.wokis.data.dto.LoginUserDTO
import es.wokis.data.dto.RegisterUserDTO
import es.wokis.data.dto.UserDTO
import es.wokis.data.models.Role

interface IUserRespository {
    fun login(user: LoginUserDTO): String?
    fun register(user: RegisterUserDTO): String?
    fun getUser(username: String): UserDTO?
    fun changeAvatar(username: String, avatar: String)
    fun removeUser(username: String): String?
    fun changeRole(username: String, role: Role): UserDTO?
    fun updateUser(userDTO: UserDTO): UserDTO?
}