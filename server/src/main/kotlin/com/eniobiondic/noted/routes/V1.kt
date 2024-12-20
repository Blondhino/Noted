package com.eniobiondic.noted.routes

import io.ktor.resources.Resource

@Resource("/v1")
class V1 {
    @Resource("/get-user")
    class GetUserRoute(val v1: V1 = V1())

    @Resource("/delete-user")
    class DeleteUserRoute(val v1: V1 = V1())
}
