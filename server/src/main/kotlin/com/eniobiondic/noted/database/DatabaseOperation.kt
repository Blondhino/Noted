package com.eniobiondic.noted.database

import arrow.core.Either
import com.eniobiondic.noted.core.DatabaseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.postgresql.util.PSQLException
import org.postgresql.util.PSQLState

suspend inline fun <T> databaseOperation(
    crossinline action: () -> T,
): Either<DatabaseError, T> = withContext(Dispatchers.IO) {
    Either.catchOrThrow<PSQLException, T> {
        action()
    }.mapLeft { psqlException ->
        when (psqlException.sqlState) {
            PSQLState.UNIQUE_VIOLATION.state -> DatabaseError.RecordAlreadyExists(
                message = psqlException.message?.parseViolationMessage(),
            )
            PSQLState.FOREIGN_KEY_VIOLATION.state -> DatabaseError.ForeignKeyViolation(
                message = psqlException.message?.parseViolationMessage(),
            )
            else -> throw psqlException
        }
    }
}

/*
This is sample error message that we want to parse:

ERROR: duplicate key value violates unique constraint "userentity_uid_key"
  Detail: Key (uid)=(05535752-d70e-4cb9-91c1-a2fe92253426) already exists.

or

ERROR: insert or update on table "notesetntiy" violates foreign key
constraint "notesentity_user_id_fkey"
  Detail: Key (user_id)=(05535752-d70e-4cb9-91c1-a2fe92253426) is not present in table "userentity".

Result of this function should be:
Detail: Key (uid)=(05535752-d70e-4cb9-91c1-a2fe92253426) already exists.

or

Detail: Key (user_id)=(05535752-d70e-4cb9-91c1-a2fe92253426) is not present in table "userentity".

Parse this message to only get the unique constraint name and the value that caused the violation.
*/
fun String?.parseViolationMessage(): String? = this?.lines()?.lastOrNull()?.trim()
