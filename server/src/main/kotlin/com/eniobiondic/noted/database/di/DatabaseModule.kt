package com.eniobiondic.noted.database.di

import com.eniobiondic.noted.database.provideDatabase
import com.eniobiondic.noted.server.NotedDatabase
import org.koin.dsl.module

fun databaseModule() = module {
    single(createdAtStart = true) { provideDatabase(get()) }
    factory { get<NotedDatabase>().userQueries }
}
