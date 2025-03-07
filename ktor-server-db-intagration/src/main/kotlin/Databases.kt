package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*

fun Application.configureDatabases() {

    val database = Database.connect(
        url = "jdbc:postgresql://ep-super-paper-a8i6y8km-pooler.eastus2.azure.neon.tech/neondb?sslmode=require",
        driver = "org.postgresql.Driver",
        user = "neondb_owner",
        password = "npg_B4hpmN0tFibW"
    )
    val userService = UserService(database)

    routing {
        // Create user
        post("/users") {
            val user = call.receive<ExposedUser>()
            val id = userService.create(user)
            call.respond(HttpStatusCode.Created, id)
        }

        // Read user
        get("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                return@get
            }
            val user = userService.read(id)
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        }


        post("/users/update/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            val user = call.receive<ExposedUser>()
            if (id != null) {
                userService.update(id,user)
            }
            call.respond(HttpStatusCode.OK, user)
        }


        // Delete user
        delete("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                return@delete
            }

            val user = userService.read(id) // Check if user exists first
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "User not found")
                return@delete
            }

            val isDeleted = userService.delete(id) // Attempt to delete
            println("CHECK_TAG_Delete_id: $id")

            if (isDeleted) {
                call.respond(HttpStatusCode.OK, "User deleted successfully")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Failed to delete user")
            }
        }


    }
}
