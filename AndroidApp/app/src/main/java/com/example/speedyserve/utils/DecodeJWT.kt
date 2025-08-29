package com.example.speedyserve.utils

import com.auth0.android.jwt.JWT

internal fun extractUserIdFromJWT(token : String)  : String?{
    val decodedJWT = JWT(token)
    return decodedJWT.getClaim("_id").asString()
}