package com.eniobiondic.noted.user

import kotlinx.serialization.Serializable

@Serializable
data class AppUser(
    val uid: String,
    val email: String,
    val profileImageUrl: String,
)
