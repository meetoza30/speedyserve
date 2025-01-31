package com.example.speedyserve.Backend.Authentication.Repo

import com.example.speedyserve.Backend.Authentication.Models.TokenResponse
import com.example.speedyserve.Backend.Authentication.Models.User
import retrofit2.Response

interface AuthRepo{

    suspend fun signUp(user: User) : Result<Unit>
    suspend fun signIn(user: User) : Result<String>

}