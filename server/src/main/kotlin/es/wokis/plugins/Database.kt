package es.wokis.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import es.wokis.data.models.*
import io.ktor.application.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

fun Application.initDB() {
    val createDBConfig = HikariConfig().apply {
        jdbcUrl = "jdbc:mysql://${config.getString("db.ip")}:${config.getString("db.port")}"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = config.getString("db.username")
        password = config.getString("db.password")
    }

    val databaseConfig = HikariConfig().apply {
        jdbcUrl = "jdbc:mysql://${config.getString("db.ip")}:${config.getString("db.port")}" +
                "/${config.getString("db.databaseName")}"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = config.getString("db.username")
        password = config.getString("db.password")
        maximumPoolSize = 10
    }

    val dbConfig = Database.connect(HikariDataSource(createDBConfig))
    // para crear la base de datos. Gracias, Ktor. Te odio.
    transaction(dbConfig) {
        SchemaUtils.createDatabase(config.getString("db.databaseName"))
    }

    // Desregistramos y cerramos la conexión
    TransactionManager.closeAndUnregister(dbConfig)

    // La base de datos REAL. ¬¬
    val dataSource = HikariDataSource(databaseConfig)
    Database.connect(dataSource)

    transaction {
        SchemaUtils.create(Users, Empresas, HorasFichadas, Invitaciones)
        try {
            val empresa = Empresa.new {
                this.nombre = "EmpresaTest"
            }

            User.new {
                this.username = "woki"
                this.password = BCrypt.hashpw("pestillo", BCrypt.gensalt())
                this.email = "woki@woki.es"
                this.empresa = empresa
            }

            Users.insertIgnore {
                it[username] = "test"
                it[password] = BCrypt.hashpw("pestillo", BCrypt.gensalt())
                it[email] = "test@test.es"
                it[codigoEmpresa] = empresa.id.value
            }
        } catch (e: ExposedSQLException) {
            /* no-op */
        }


    }
}