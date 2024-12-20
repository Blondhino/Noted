package com.eniobiondic.noted.user.service

import arrow.core.Either
import arrow.core.raise.either
import com.eniobiondic.noted.core.DomainError
import com.eniobiondic.noted.database.mapper.toAppUser
import com.eniobiondic.noted.server.UserEntity
import com.eniobiondic.noted.user.AppUser
import com.eniobiondic.noted.user.usecase.DeleteUser
import com.eniobiondic.noted.user.usecase.GetExistingOrCreateUser

class UserServiceImpl(
    private val getExistingOrCreateUser: GetExistingOrCreateUser,
    private val deleteUserFromDb: DeleteUser,
) : UserService {
    override suspend fun getUser(
        email: String,
        profilePictureUrl: String,
    ): Either<DomainError, AppUser> = either {
        getExistingOrCreateUser(email, profilePictureUrl)
            .map(UserEntity::toAppUser)
            .bind()
    }

    override suspend fun deleteUser(email: String): Either<DomainError, Unit> =
        deleteUserFromDb(email)
}
