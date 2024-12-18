package com.eniobiondic.noted.plugins

import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        authenticate {
            get("/") {
                call.respondText("Hello, world!")
            }
        }
    }
}
