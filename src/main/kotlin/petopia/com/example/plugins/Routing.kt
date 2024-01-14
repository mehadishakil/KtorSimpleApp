package petopia.com.example.plugins

import com.example.data.model.User
import com.example.repository.Repo
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import petopia.com.example.authentication.JwtService
import petopia.com.example.authentication.hash
import petopia.com.example.routes.UserRoutes

fun Application.configureRouting() {

    val db = Repo()
    val jwtService = JwtService()
    val hashFunction = {s:String -> hash(s) }


    routing {



        UserRoutes(db, jwtService, hashFunction)



        get("/") {
            call.respondText("Hello World!")
        }

        get("/note/{id}") {
            val id = call.parameters["id"]
            call.respond("${id}")
        }

        get("/token") {
            val email = call.request.queryParameters["email"]!!
            val password = call.request.queryParameters["password"]!!
            val userName = call.request.queryParameters["username"]!!

            val user = User(email, hashFunction(password), userName)
            call.respond(jwtService.generateToken(user))
        }

        get("/note") {
            val id = call.request.queryParameters["id"]
            call.respond("${id}")

        }

        route("/notes") {

            route("/create") {
                // localhost:8081/notes
                post {
                    val body = call.receive<String>()
                    call.respond(body)
                }
            }


            delete {
                val body = call.receive<String>()
                call.respond(body)
            }
        }


    }
}
