package com.example.speedyserve.Models.ApiResponse

import kotlinx.serialization.Serializable

data class CheckAuthResponse(
    val success : Boolean
)

@Serializable
data class TokenReq(
    val token : String
)