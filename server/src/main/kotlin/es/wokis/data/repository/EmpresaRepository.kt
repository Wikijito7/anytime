package es.wokis.data.repository

import es.wokis.data.dto.EmpresaDTO
import es.wokis.data.dto.UserDTO
import es.wokis.data.models.Empresa
import es.wokis.data.models.Empresas
import es.wokis.data.repository.interfaces.IEmpresaRepository
import es.wokis.utils.toEmpresaDTO
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.transactions.transaction

class EmpresaRepository : IEmpresaRepository {
    override fun getEmpresa(name: String): EmpresaDTO? {
        var empresaDTO: EmpresaDTO? = null
        transaction {
            val empresa = Empresa.find { Empresas.nombre eq name }.singleOrNull() ?: return@transaction
            empresaDTO = empresa.toEmpresaDTO()
        }

        return empresaDTO
    }

    override fun addEmpresa(empresa: EmpresaDTO): EmpresaDTO? {
        var empresaDTO: EmpresaDTO? = null
        transaction {
            val empresaDB = Empresa
                .find { Empresas.nombre.lowerCase() eq empresa.name.toLowerCase() }
                .singleOrNull()

            if (empresaDB == null) {
                empresaDTO = Empresa.new {
                    nombre = empresa.name
                    direccion = empresa.direccion
                    piso = empresa.piso
                    logo = empresa.logo
                    creador = empresa.creador
                }.toEmpresaDTO()
            }
        }

        return empresaDTO
    }

    override fun removeEmpresa(empresa: EmpresaDTO): Boolean {
        if (getEmpresa(empresa.name) != null) {
            Empresas.deleteWhere { Empresas.id eq empresa.id }
            return true
        }
        return false
    }

    override fun eliminarTrabajador(user: UserDTO): Boolean {
        transaction {

        }
        return false
    }
}