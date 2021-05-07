package es.wokis.data.repository.interfaces

import es.wokis.data.dto.LoginUserDTO
import es.wokis.data.dto.RegisterUserDTO
import es.wokis.data.dto.UserDTO

interface IUserRespository {
    fun login(user: LoginUserDTO): String?
    fun register(user: RegisterUserDTO): String?
    fun getUser(username: String): UserDTO?
    fun getAllUsersInCompany(companyName: String): Set<UserDTO>
}