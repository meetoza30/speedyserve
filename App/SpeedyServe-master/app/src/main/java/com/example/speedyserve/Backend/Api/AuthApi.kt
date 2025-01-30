package com.example.speedyserve.Backend.Api

import com.example.speedyserve.Backend.Models.TokenResponse
import com.example.speedyserve.Backend.Models.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/")
    suspend fun signUp(@Body user : User)

    @POST("/api/signIn")
    suspend fun signIn(@Body user: User) : TokenResponse

}