package com.example.speedyserve.Models.ApiResponse

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T
)


data class AuthData(
    val token: String
)