package com.akurat

import io.ktor.application.*
import com.akurat.plugins.*

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.apiModule() {
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureErrorHandler()
}
