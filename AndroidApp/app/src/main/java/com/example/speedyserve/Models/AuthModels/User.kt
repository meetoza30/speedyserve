package com.example.speedyserve.Models.AuthModels

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class UserSignUpReq(
    val username : String,
    val email : String,
    val password : String,
    val mobile : String
)

@Serializable
data class UserSignInReq(
    val username : String,
    val password: String
)