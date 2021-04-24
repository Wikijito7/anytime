package es.wokis.dto

import io.ktor.auth.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Table
import org.mindrot.jbcrypt.BCrypt

data class UserDTO(val username: String,
                   val password: String,
                   val name: String,
                   val surname: String,
                   val id_empresa: Int)

