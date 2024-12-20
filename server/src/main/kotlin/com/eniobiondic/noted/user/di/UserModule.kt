package com.eniobiondic.noted.user.di

import com.eniobiondic.noted.user.service.UserService
import com.eniobiondic.noted.user.service.UserServiceImpl
import com.eniobiondic.noted.user.usecase.CreateUser
import com.eniobiondic.noted.user.usecase.DeleteUser
import com.eniobiondic.noted.user.usecase.GetExistingOrCreateUser
import com.eniobiondic.noted.user.usecase.GetUser
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun userModule() = module {
    factoryOf(::UserServiceImpl) bind UserService::class
    factoryOf(::GetExistingOrCreateUser)
    factoryOf(::CreateUser)
    factoryOf(::GetUser)
    factoryOf(::DeleteUser)
}
