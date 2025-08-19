package com.example.speedyserve.Models.ApiResponse

data class ApiResponse(
    val success: Boolean,
    val message: String,
    val data: String // token string
)
