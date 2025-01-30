package com.example.speedyserve.Backend.Repo

import com.example.speedyserve.Backend.Models.User

interface AuthRepo{

    suspend fun signUp(user: User)
    suspend fun signIn(user: User)

}