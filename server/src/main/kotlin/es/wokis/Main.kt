package es.wokis

import com.typesafe.config.ConfigFactory
import io.ktor.server.netty.*
import es.wokis.plugins.*
import io.ktor.application.*
import java.io.File

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.start(testing: Boolean = true) {
    initConfig()
    initDB()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureRouting()
}
