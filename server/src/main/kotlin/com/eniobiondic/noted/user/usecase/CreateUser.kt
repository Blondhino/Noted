package com.eniobiondic.noted.user.usecase

import arrow.core.Either
import arrow.core.raise.either
import com.eniobiondic.noted.core.DatabaseError
import com.eniobiondic.noted.database.databaseOperation
import com.eniobiondic.noted.server.NotedDatabase
import com.eniobiondic.noted.server.UserEntity
import com.eniobiondic.noted.server.UserQueries
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

class CreateUser(
    private val userQueries: UserQueries,
    private val notedDatabase: NotedDatabase,
) {
    suspend operator fun invoke(
        email: String,
        profileImage: String,
        id: Uuid = Uuid.random(),
    ): Either<DatabaseError, UserEntity> = either {
        databaseOperation {
            notedDatabase.transactionWithResult {
                userQueries.insert(
                    id = id.toJavaUuid(),
                    email = email,
                    profile_image_url = profileImage,
                )
                userQueries.getByEmail(email).executeAsOne()
            }
        }.bind()
    }
}
