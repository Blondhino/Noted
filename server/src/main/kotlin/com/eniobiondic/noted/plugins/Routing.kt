package com.eniobiondic.noted.plugins

import com.eniobiondic.noted.routes.V1
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import io.ktor.server.resources.get as getRoute

fun Application.configureRouting() {
    install(Resources)
    routing {
        authenticate {
            getRoute<V1.HomeRoute> {
                call.respondText("Hello, world!")
            }
        }
    }
}
