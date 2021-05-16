package es.wokis.data.repository

import es.wokis.data.dto.EmpresaDTO
import es.wokis.data.dto.UserDTO
import es.wokis.data.repository.interfaces.IEmpresaRepository

class EmpresaRepository private constructor() : IEmpresaRepository {
    override fun getEmpresa(name: String): EmpresaDTO {
        TODO("Not yet implemented")
    }

    override fun getEmpresaOfUser(username: String): EmpresaDTO {
        TODO("Not yet implemented")
    }

    override fun addEmpresa(empresa: EmpresaDTO): EmpresaDTO? {
        TODO("Not yet implemented")
    }

    override fun removeEmpresa(empresa: EmpresaDTO): Boolean {
        TODO("Not yet implemented")
    }

    override fun eliminarTrabajador(user: UserDTO): Boolean {
        TODO("Not yet implemented")
    }

    companion object {
        @Volatile
        var INSTANCE: EmpresaRepository? = null

        fun getInstance(): EmpresaRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = EmpresaRepository()
                INSTANCE = instance
                instance
            }
        }
    }
}