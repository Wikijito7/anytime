package es.wokis

import es.wokis.plugins.*
import io.ktor.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.start(testing: Boolean = true) {
    initConfig()
    initDB()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureRouting()
}
