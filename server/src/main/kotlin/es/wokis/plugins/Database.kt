package es.wokis.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import es.wokis.data.models.Empresas
import es.wokis.data.models.HorasFichadas
import es.wokis.data.models.Invitaciones
import es.wokis.data.models.Users
import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

fun Application.initDB() {
    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:mysql://${config.getString("db.ip")}:${config.getString("db.port")}" +
                "/${config.getString("db.databaseName")}"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = config.getString("db.username")
        password = config.getString("db.password")
        maximumPoolSize = 10

    }

    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

    transaction {

        SchemaUtils.create(Users, Empresas, HorasFichadas, Invitaciones)

        Users.insertIgnore {
            it[username] = "test"
            it[password] = BCrypt.hashpw("pestillo", BCrypt.gensalt())
            it[email] = "test@test.es"
        }
    }
}