package com.eniobiondic.noted.user.usecase

import arrow.core.Either
import arrow.core.raise.either
import com.eniobiondic.noted.core.UserError
import com.eniobiondic.noted.database.databaseOperation
import com.eniobiondic.noted.server.UserEntity
import com.eniobiondic.noted.server.UserQueries
import com.eniobiondic.noted.user.usecase.UserIdentifier.Email
import com.eniobiondic.noted.user.usecase.UserIdentifier.Id
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

class GetUser(
    private val userQueries: UserQueries,
) {
    suspend operator fun invoke(identifier: UserIdentifier): Either<UserError, UserEntity> =
        either {
            val userOrNull = databaseOperation {
                when (identifier) {
                    is Email -> userQueries.getByEmail(identifier.email).executeAsOneOrNull()
                    is Id -> userQueries.getById(identifier.id.toJavaUuid()).executeAsOneOrNull()
                }
            }.bind()

            userOrNull ?: when (identifier) {
                is Email -> raise(UserError.UserNotFoundByEmail(identifier.email))
                is Id -> raise(UserError.UserNotFoundById(identifier.id.toString()))
            }
        }
}

sealed interface UserIdentifier {
    data class Email(val email: String) : UserIdentifier
    data class Id(val id: Uuid) : UserIdentifier
}
