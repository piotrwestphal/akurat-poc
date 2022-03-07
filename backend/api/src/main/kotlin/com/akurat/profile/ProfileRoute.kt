package com.akurat.profile

import com.akurat.ProfilesService
import com.akurat.model.Profile
import com.akurat.model.toResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.profileRoute() {

    // TODO: add validation https://github.com/konform-kt/konform
    // TODO: add validation https://medium.com/nerd-for-tech/object-validation-in-kotlin-c7e02b5dabc
    val service by inject<ProfilesService>()
    route("/profiles") {
        get {
            call.respond(service.getAll().sortedBy(Profile::createdAt).map { it.toResponse() })
        }
        get("{id}") {
            val id = call.parameters["id"]?.toLong()
                ?: throw BadRequestException("Request parameter 'name' should be provided")
            val profile = service.get(id) ?: throw NotFoundException("Profile with id '$id' not found")
            call.respond(profile.toResponse())
        }
        post {
            val createRequest = call.receive<CreateProfileRequest>()
            val profile = service.create(createRequest.name, createRequest.role)
            call.response.headers.append("Location", "${call.request.local.uri}/${profile.id}")
            call.respond(HttpStatusCode.Created, profile.toResponse())
        }
        delete("{id}") {
            val id = call.parameters["id"]?.toLong()
                ?: throw BadRequestException("Request parameter 'id' should be provided")
            service.delete(id) ?: throw NotFoundException("Profile with id '$id' not found")
            call.respond(HttpStatusCode.OK)
        }
    }

}