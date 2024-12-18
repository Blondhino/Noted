package com.eniobiondic.noted.plugins

import com.eniobiondic.noted.auth.firebase
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication

fun Application.configureSecurity() {
    install(Authentication) {
        firebase()
    }
}
