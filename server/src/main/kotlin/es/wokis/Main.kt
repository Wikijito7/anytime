package es.wokis

import io.ktor.server.netty.*
import es.wokis.plugins.*
import io.ktor.application.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.start(testing: Boolean = true) {
    initDB()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureRouting()
}
