package es.wokis.repository.interfaces

import es.wokis.dto.LoginUserDTO
import es.wokis.dto.RegisterUserDTO
import es.wokis.dto.UserDTO

interface IUserRespository {

    fun login(user: LoginUserDTO): String?
    fun register(user: RegisterUserDTO): String?
    fun getUser(username: String): UserDTO
    fun getAllUsersInCompany(companyName: String): Set<UserDTO>

}