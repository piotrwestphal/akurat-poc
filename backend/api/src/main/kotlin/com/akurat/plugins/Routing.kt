package com.akurat.plugins

import com.akurat.AppService
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

    val service by inject<AppService>()

    routing {
        get("/") {
            call.respond(service.getProfiles())
        }
        get("/{name}") {
            val name = call.parameters["name"].orEmpty()
            call.respond(service.getProfile(name))
        }
        post("/") {
            val data = call.receive<SomeData>()
            call.respond(HttpStatusCode.Created, service.createProfile(data.text))
        }
    }
}
