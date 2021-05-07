package es.wokis.data.repository.interfaces

import es.wokis.data.dto.EmpresaDTO
import es.wokis.data.dto.UserDTO

interface IEmpresaRepository {
    fun getEmpresa(name: String): EmpresaDTO
    fun getEmpresaOfUser(username: String): EmpresaDTO
    fun addEmpresa(empresa: EmpresaDTO): EmpresaDTO?
    fun removeEmpresa(empresa: EmpresaDTO): Boolean
    fun eliminarTrabajador(user: UserDTO): Boolean
}