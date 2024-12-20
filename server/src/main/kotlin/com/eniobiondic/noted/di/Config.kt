package com.eniobiondic.noted.di

import com.eniobiondic.noted.auth.di.authModule
import com.eniobiondic.noted.database.di.databaseModule
import com.eniobiondic.noted.user.di.userModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            module { single { this@configureKoin } },
            authModule(),
            databaseModule(),
            userModule(),
        )
    }
}
