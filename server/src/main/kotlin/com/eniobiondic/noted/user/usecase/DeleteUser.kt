package com.eniobiondic.noted.user.usecase

import arrow.core.Either
import arrow.core.raise.either
import com.eniobiondic.noted.core.UserError
import com.eniobiondic.noted.server.UserQueries

class DeleteUser(
    private val getUser: GetUser,
    private val userQueries: UserQueries,
) {
    suspend operator fun invoke(email: String): Either<UserError, Unit> = either {
        val user = getUser(UserIdentifier.Email(email)).bind()
        userQueries.deleteByEmail(user.email)
    }
}
