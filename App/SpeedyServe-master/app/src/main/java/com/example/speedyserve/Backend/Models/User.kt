package com.example.speedyserve.Backend.Models

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val userId : String? =null,
    val userName : String,
    val email : String,
    val password : String,
    val mobileNo : String,

)
