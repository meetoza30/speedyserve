package com.example.speedyserve.utils

import com.auth0.android.jwt.JWT

internal fun extractUserIdFromJWT(token : String)  : String?{
    val jwttoken = JWT(token)
    val userId = jwttoken.id
    return userId
}