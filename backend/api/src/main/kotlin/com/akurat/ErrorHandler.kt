package com.akurat

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

@Serializable
data class ErrorMessage(val error: String, val message: String?)

fun Application.configureErrorHandler() {
    install(StatusPages) {
        exception<NotFoundException> { cause ->
            val statusCode = HttpStatusCode.NotFound
            log.error(cause)
            call.respond(statusCode, ErrorMessage(statusCode.description, cause.message))
        }
        exception<SerializationException> { cause ->
            val statusCode = HttpStatusCode.BadRequest
            log.error(cause)
            call.respond(statusCode, ErrorMessage(statusCode.description, cause.message))
        }
    }
}