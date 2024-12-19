package com.eniobiondic.noted.database

import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.eniobiondic.noted.server.NotedDatabase
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopped

fun provideDatabase(
    app: Application,
): NotedDatabase {
    val dbConfig = app.environment.config.config(DATABASE_CONFIG)

    val hikariConfig = HikariConfig().apply {
        dbConfig.propertyOrNull(PATH_CONNECTION)?.getString()?.let(this::setJdbcUrl)
        dbConfig.propertyOrNull(PATH_USERNAME)?.getString()?.let(this::setUsername)
        dbConfig.propertyOrNull(PATH_PASSWORD)?.getString()?.let(this::setPassword)
        dbConfig.propertyOrNull(PATH_POOL_SIZE)?.getString()?.toInt()?.let(this::setMaximumPoolSize)
    }

    val driver = HikariDataSource(hikariConfig).asJdbcDriver()

    NotedDatabase.Schema.create(driver)

    return NotedDatabase(driver).also { app.monitor.subscribe(ApplicationStopped) { driver.close() } }
}

private const val DATABASE_CONFIG = "database"
private const val PATH_CONNECTION = "connection"
private const val PATH_USERNAME = "username"
private const val PATH_PASSWORD = "password"
private const val PATH_POOL_SIZE = "poolSize"
