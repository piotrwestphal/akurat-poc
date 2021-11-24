package com.akurat

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.*
import kotlinx.serialization.SerializationException

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
        exception<Throwable> { cause ->
            log.error(cause)
        }
    }
}