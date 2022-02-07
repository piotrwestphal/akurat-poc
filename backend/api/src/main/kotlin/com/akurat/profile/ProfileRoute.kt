package com.akurat.profile

import com.akurat.ProfilesService
import com.akurat.model.toResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.profileRoute() {

    val service by inject<ProfilesService>()
    route("profiles") {
        get {
            call.respond(service.getAll().map { it.toResponse() })

        }
        get("{name}") {
            val name = call.parameters["name"].orEmpty()
            call.respond(service.get(name).toResponse())
        }
        post {
            val data = call.receive<CreateProfileRequest>()
            call.respond(HttpStatusCode.Created, service.create(data.text).toResponse())
        }
    }
}