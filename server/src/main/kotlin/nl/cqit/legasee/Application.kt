package nl.cqit.legasee

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        anyHost()
    }
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
        get("/proxy") {
            val client = HttpClient()
            val url = call.request.queryParameters["url"]
            if (url != null) {
                val response = client.get(url)
                call.respondBytes(response.body(),response.contentType())
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing 'url' parameter")
            }
        }
    }
}