package com.akurat.plugins

import io.ktor.auth.*
import io.ktor.util.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun Application.configureSecurity() {
    
    authentication {
    		basic(name = "basicAuth") {
    			realm = "Ktor Server"
    			validate { credentials ->
    				if (credentials.name == credentials.password) {
    					UserIdPrincipal(credentials.name)
    				} else {
    					null
    				}
    			}
    		}
    	}

    routing {
        authenticate("basicAuth") {
            get("/protected/route/basic") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}!")
            }
        }
    }
}
