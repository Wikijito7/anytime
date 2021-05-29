package es.wokis

import es.wokis.plugins.*
import io.ktor.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.start(testing: Boolean = true) {
    val di = initKodeIn()
    initConfig()
    initImages()
    initDB()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureRouting(di)
}
