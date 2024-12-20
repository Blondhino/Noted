package com.eniobiondic.noted.core

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import com.eniobiondic.noted.auth.TokenPrincipal
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.RoutingContext
import kotlinx.serialization.Serializable

fun ApplicationCall.tokenOrError(): Either<ValidationError.InvalidToken, TokenPrincipal> =
    either { ensureNotNull(principal()) { ValidationError.InvalidToken } }

sealed interface DomainError

sealed interface ValidationError : DomainError {
    data object InvalidToken : ValidationError
}

sealed interface UserError : DomainError {
    data class UserNotFoundById(val id: String) : UserError
    data class UserNotFoundByEmail(val email: String) : UserError
}

sealed interface DatabaseError : UserError, ValidationError {
    data class RecordAlreadyExists(val message: String?) : DatabaseError
    data class ForeignKeyViolation(val message: String?) : DatabaseError
}

suspend inline fun <reified A : Any> Either<DomainError, A>.respond(context: RoutingContext): Unit =
    when (this) {
        is Either.Left -> context.respondError(value)
        is Either.Right -> context.call.respond(value)
    }

suspend fun RoutingContext.respondError(error: DomainError) {
    val domainError = when (error) {
        is DatabaseError.RecordAlreadyExists -> call.unprocessable("Record already exists. ${error.message}")
        is DatabaseError.ForeignKeyViolation -> call.unprocessable("Invalid request. Schema constraints not satisfied.")
        is UserError.UserNotFoundByEmail -> call.unprocessable("User not found for email: ${error.email}")
        is UserError.UserNotFoundById -> call.unprocessable("User not found for id: ${error.id}")
        ValidationError.InvalidToken -> call.unprocessable("Invalid token")
    }
    call.respond(UnprocessableEntity, ErrorResponse(domainError))
}

private suspend inline fun RoutingCall.unprocessable(
    error: String,
) = ErrorMessage(listOf(error))

@Serializable
data class ErrorResponse(val error: ErrorMessage)

@Serializable
data class ErrorMessage(val body: List<String>)
