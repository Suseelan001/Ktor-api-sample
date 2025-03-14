package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.logging.Logger

private val logger = Logger.getLogger("ktor")

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    try {
        embeddedServer(Netty, port) {
            module()
        }.start(wait = true)
    } catch (e: Exception) {
        logger.severe("Failed to start server: $e")
    }
}

fun Application.module() {
    try {
        configureSerialization()
        configureDatabases()
        configureRouting()
    } catch (e: Exception) {
        logger.severe("Failed to configure application: $e")
    }
}