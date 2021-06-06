package es.wokis.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import es.wokis.data.dto.LoginUserDTO
import es.wokis.data.models.User
import es.wokis.data.models.Users
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.jetbrains.exposed.sql.transactions.transaction

private lateinit var jwtIssuer: String
private lateinit var jwtAudience: String
private lateinit var algorithm: Algorithm

fun Application.configureSecurity() {
    jwtIssuer = environment.config.property("jwt.domain").getString()
    jwtAudience = environment.config.property("jwt.audience").getString()
    val privateKey = config.getString("secretkey")

    algorithm = Algorithm.HMAC256(privateKey)

    val jwtRealm = environment.config.property("jwt.realm").getString()
    authentication {
        jwt {
            realm = jwtRealm
            verifier(makeJwtVerifier(jwtIssuer, jwtAudience))
            validate { credential ->
                val name = credential.payload.getClaim("username").asString()
                val password = credential.payload.getClaim("password").asString()

                val user = transaction {
                    return@transaction User.find { Users.username eq name }.singleOrNull()
                }

                if (user != null && password == user.password) {
                    LoginUserDTO(user.username, user.password, user.rol)
                } else {
                    null
                }
            }
        }
    }

}

private fun makeJwtVerifier(issuer: String, audience: String): JWTVerifier = JWT
    .require(algorithm)
    .withAudience(audience)
    .withIssuer(issuer)
    .build()

/**
 * A function that creates a token with given user data.
 *
 * @param user the user whose token is being made
 */

fun makeToken(user: LoginUserDTO): String = JWT.create()
    .withSubject("Authentication")
    .withIssuer(jwtIssuer)
    .withAudience(jwtAudience)
    .withClaim("username", user.username)
    .withClaim("password", user.password)
    .sign(algorithm)