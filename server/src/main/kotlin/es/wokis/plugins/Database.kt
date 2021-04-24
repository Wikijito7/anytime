package es.wokis.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import es.wokis.models.Users
import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

fun Application.initDB() {
    val config = HikariConfig().apply {
        jdbcUrl         = "jdbc:mysql://localhost/anytime"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username        = "root"
        password        = "pestillo"
        maximumPoolSize = 10

    }

    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

    transaction {

        SchemaUtils.create(Users)

        Users.insertIgnore {
            it[username] = "test"
            it[password] = BCrypt.hashpw("pestillo", BCrypt.gensalt())
        }
    }
}