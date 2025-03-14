package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.server.response.*

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, host = "0.0.0.0", port = port) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.application.log.error("Unhandled error: ${cause.localizedMessage}", cause)
            call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
        }
    }

    try {
        configureSerialization()
        configureDatabases()
        configureRouting()
    } catch (e: Exception) {
        log.error("Failed to configure application", e)
    }
}
