package com.eniobiondic.noted.user.service

import arrow.core.Either
import com.eniobiondic.noted.core.DomainError
import com.eniobiondic.noted.user.AppUser

interface UserService {
    suspend fun getUser(
        email: String,
        profilePictureUrl: String,
    ): Either<DomainError, AppUser>
    suspend fun deleteUser(email: String): Either<DomainError, Unit>
}
