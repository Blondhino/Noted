package com.eniobiondic.noted.user.usecase

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import com.eniobiondic.noted.core.DatabaseError
import com.eniobiondic.noted.core.UserError
import com.eniobiondic.noted.core.UserError.UserNotFoundByEmail
import com.eniobiondic.noted.core.UserError.UserNotFoundById
import com.eniobiondic.noted.server.UserEntity

class GetExistingOrCreateUser(
    private val getUser: GetUser,
    private val createUser: CreateUser,
) {

    suspend operator fun invoke(
        email: String,
        profileImageUrl: String,
    ): Either<DatabaseError, UserEntity> = either {
        when (val existingUserOrError = getUser(UserIdentifier.Email(email))) {
            is Either.Right -> existingUserOrError
            is Either.Left -> handleGetUserError(email, profileImageUrl, existingUserOrError.value)
        }.bind()
    }

    private suspend fun Raise<DatabaseError>.handleGetUserError(
        email: String,
        profileImageUrl: String,
        userError: UserError,
    ) = when (userError) {
        is DatabaseError -> raise(userError)
        is UserNotFoundByEmail, is UserNotFoundById -> createUser(email, profileImageUrl)
    }
}
