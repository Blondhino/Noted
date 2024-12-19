package com.eniobiondic.noted

import com.eniobiondic.noted.di.configureKoin
import com.eniobiondic.noted.plugins.configureRouting
import com.eniobiondic.noted.plugins.configureSecurity
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSecurity()
    configureRouting()
}
