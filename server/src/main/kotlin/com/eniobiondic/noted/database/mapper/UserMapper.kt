package com.eniobiondic.noted.database.mapper

import com.eniobiondic.noted.server.UserEntity
import com.eniobiondic.noted.user.AppUser

fun UserEntity.toAppUser() = AppUser(
    uid = id.toString(),
    email = email,
    profileImageUrl = profile_image_url,
)
