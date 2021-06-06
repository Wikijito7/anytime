package es.wokis.data.repository.interfaces

import es.wokis.data.dto.EmpresaDTO

interface IEmpresaRepository {
    fun getEmpresa(name: String): EmpresaDTO?
    fun getEmpresa(id: Int): EmpresaDTO?
    fun addEmpresa(empresa: EmpresaDTO): EmpresaDTO?
    fun removeEmpresa(empresa: EmpresaDTO): Boolean
}