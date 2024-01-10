package petopia.com.example

import com.example.repository.DatabaseFactory
import com.example.repository.Repo
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import petopia.com.example.authentication.JwtService
import petopia.com.example.authentication.hash
import petopia.com.example.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    DatabaseFactory.init()
    configureSecurity()
    configureSerialization()
    configureRouting()
}
