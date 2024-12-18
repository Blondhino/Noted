package com.eniobiondic.noted

import com.eniobiondic.noted.di.configureKoin
import com.eniobiondic.noted.plugins.configureRouting
import com.eniobiondic.noted.plugins.configureSecurity
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureKoin()
    configureSecurity()
    configureRouting()
}
