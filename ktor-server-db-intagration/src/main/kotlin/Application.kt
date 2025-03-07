package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
}
