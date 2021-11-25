package com.akurat.plugins

import com.akurat.ProfilesService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

@Serializable
data class SomeData(val text: String)

fun Application.configureRouting() {

    val service by inject<ProfilesService>()

    routing {
        get("/") {
            call.respond(service.getAll())
        }
        get("/{name}") {
            val name = call.parameters["name"].orEmpty()
            call.respond(service.get(name))
        }
        post("/") {
            val data = call.receive<SomeData>()
            call.respond(HttpStatusCode.Created, service.create(data.text))
        }
    }
}
