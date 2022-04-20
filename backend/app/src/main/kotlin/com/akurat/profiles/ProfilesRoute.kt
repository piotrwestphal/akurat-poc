package com.akurat.profiles

import com.akurat.ProfilesService
import com.akurat.model.toResponse
import com.akurat.validation.validateUuid
import io.konform.validation.Invalid
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import java.util.*

fun Route.profilesRoute() {

    val logger = KotlinLogging.logger {}
    // TODO: add validation https://github.com/konform-kt/konform
    // TODO: add validation https://medium.com/nerd-for-tech/object-validation-in-kotlin-c7e02b5dabc
    val profilesService by inject<ProfilesService>()

    // TODO: add method for update
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
            logger.info { "Creating a profile with a payload [${createRequest}]" }
            val profile = profilesService.create(createRequest.name, createRequest.role)
            logger.info { "Created profile [${profile}]" }
            call.response.headers.append("Location", "${call.request.local.uri}/${profile.id}")
            call.respond(HttpStatusCode.Created, profile.toResponse())
        }
        delete("{id}") {
            val profileId = id
            logger.info { "Deleting profile with id [${profileId}]" }
            profilesService.delete(profileId)
                ?: throw NotFoundException("Profile with id '$profileId' not found")
            logger.info { "Deleted profile with id [${profileId}]" }
            call.respond(HttpStatusCode.OK)
        }
        patch("{id}") {
            val profileId = id
            val updateRequest = call.receive<UpdateProfileRequest>()
            logger.info { "Updating profile with id [${profileId}] by [${updateRequest}]" }
            val profile = profilesService.update(profileId, updateRequest.toProfileUpdate()) ?: throw NotFoundException("Profile with id '$id' not found")
            logger.info { "Updated profile [${profile}]" }
            call.respond(HttpStatusCode.OK, profile.toResponse())
        }
    }
}


private val PipelineContext<*, ApplicationCall>.id: UUID get()  =
    with(this.call.parameters["id"] ?: throw BadRequestException("Request parameter 'id' should be provided")) {
        val result = validateUuid(this)
        if (result is Invalid) {
            throw BadRequestException("Wrong request parameter 'id' format: ${result.errors.map { it.message }}")
        } else {
            UUID.fromString(this)
        }
    }