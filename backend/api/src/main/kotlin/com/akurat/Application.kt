package com.akurat

import io.ktor.application.*
import com.akurat.plugins.*
import kotlinx.serialization.Serializable

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Serializable
data class ErrorMessage(val error: String, val message: String?)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureKoin()
    configureErrorHandler()
}
