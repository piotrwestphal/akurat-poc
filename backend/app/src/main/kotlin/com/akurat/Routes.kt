package com.akurat

import com.akurat.profiles.profilesRoute
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        route("/api") {
            route("/v1") {
                profilesRoute()
            }
        }
    }
}
