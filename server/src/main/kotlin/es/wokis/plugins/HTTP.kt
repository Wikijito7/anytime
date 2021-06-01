package es.wokis.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import java.time.Duration

fun Application.configureHTTP() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.Authorization)

        allowNonSimpleContentTypes = true
        allowCredentials = true
        allowSameOrigin = true
        maxAgeInSeconds = Duration.ofDays(1).toMinutes() * 60L

        anyHost() // TODO: 30/05/2021 No dejarlo como anyhost, limitarlo al host final
    }

}
