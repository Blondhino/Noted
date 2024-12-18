package com.eniobiondic.noted.routes

import io.ktor.resources.Resource

@Resource("/v1")
class V1 {
    @Resource("/")
    class HomeRoute(val v1: V1 = V1())
}
