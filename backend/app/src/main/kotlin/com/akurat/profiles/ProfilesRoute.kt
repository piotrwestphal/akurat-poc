package com.akurat.profiles

import com.akurat.ProfilesService
import com.akurat.model.toResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.profilesRoute() {

    // TODO: add validation https://github.com/konform-kt/konform
    // TODO: add validation https://medium.com/nerd-for-tech/object-validation-in-kotlin-c7e02b5dabc
    val profilesService by inject<ProfilesService>()

    // TODO: add method for update
    // TODO: test bad uuid -> Bas Request Exception
    route("/profiles") {
        get {
            call.respond(profilesService.getAll().map { it.toResponse() })
        }
        get("{id}") {
            val profile = profilesService.get(id) ?: throw NotFoundException("Profile with id '$id' not found")
            call.respond(profile.toResponse())
        }
        post {
            val createRequest = call.receive<CreateProfileRequest>()
            val profile = profilesService.create(createRequest.name, createRequest.role)
            call.response.headers.append("Location", "${call.request.local.uri}/${profile.id}")
            call.respond(HttpStatusCode.Created, profile.toResponse())
        }
        delete("{id}") {
            profilesService.delete(id)
                ?: throw NotFoundException("Profile with id '$id' not found")
            call.respond(HttpStatusCode.OK)
        }
        put("{id}") {
            // TODO!
            profilesService.update(id, null!!)
        }
    }
}

private val PipelineContext<*, ApplicationCall>.id: UUID get()  =
    with(this.call.parameters["id"] ?: throw BadRequestException("Request parameter 'id' should be provided")) {
        try {
            UUID.fromString(this)
        } catch (e: IllegalArgumentException) {
            throw BadRequestException(e.message ?: "Error during converting string to uuid", e.cause)
        }
    }