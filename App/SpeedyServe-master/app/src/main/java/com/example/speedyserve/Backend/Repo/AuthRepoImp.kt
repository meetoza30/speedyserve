package com.example.speedyserve.Backend.Repo

import android.content.SharedPreferences
import com.example.speedyserve.Backend.Api.AuthApi
import com.example.speedyserve.Backend.Models.User

class AuthRepoImp(
    private val authApi: AuthApi,
    private val preferences: SharedPreferences
) : AuthRepo{
    override suspend fun signUp(user: User){
        return authApi.signUp(
            user
        )

    }

    override suspend fun signIn(user: User) {
        TODO("Not yet implemented")
    }
}