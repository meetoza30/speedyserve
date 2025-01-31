package com.example.speedyserve.Backend.Authentication.Models

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val _id : String?=null,
    val username : String,
    val email : String?=null,
    val mobile : String?=null,
    val password : String,

)
