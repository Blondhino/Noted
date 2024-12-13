package com.eniobiondic.noted

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform