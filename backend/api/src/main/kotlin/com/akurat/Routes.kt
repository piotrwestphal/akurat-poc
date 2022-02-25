package com.akurat

import com.akurat.profile.profileRoute
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        route("/api") {
            profileRoute()
        }
    }
}
