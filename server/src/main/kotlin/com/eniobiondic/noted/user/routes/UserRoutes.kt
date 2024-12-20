package com.eniobiondic.noted.user.routes

import arrow.core.raise.either
import com.eniobiondic.noted.core.respond
import com.eniobiondic.noted.core.tokenOrError
import com.eniobiondic.noted.routes.V1
import com.eniobiondic.noted.user.service.UserService
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import org.koin.ktor.plugin.scope
import io.ktor.server.resources.delete as deleteRoute
import io.ktor.server.resources.get as getRoute

fun Routing.userRoutes() {
    authenticate {
        getRoute<V1.GetUserRoute> {
            val userService: UserService = call.scope.get()
            either {
                val token = call.tokenOrError().bind().token
                userService.getUser(email = token.email, profilePictureUrl = token.picture).bind()
            }.respond(this)
        }

        deleteRoute<V1.DeleteUserRoute> {
            either {
                val userService: UserService = call.scope.get()
                val token = call.tokenOrError().bind().token
                userService.deleteUser(token.email).bind()
            }.respond(this)
        }
    }
}
