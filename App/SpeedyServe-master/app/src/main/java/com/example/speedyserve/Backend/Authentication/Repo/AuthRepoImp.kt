package com.example.speedyserve.Backend.Authentication.Repo

import android.content.SharedPreferences
import android.util.Log
import com.example.speedyserve.Backend.Authentication.Api.AuthApi
import com.example.speedyserve.Backend.Authentication.Models.User

class AuthRepoImp(
    private val authApi: AuthApi,
    private val preferences: SharedPreferences
) : AuthRepo {

    override suspend fun signUp(user: User): Result<Unit> {
        return try {
            authApi.signUp(user) // No response body, assumes success if no exception
            Result.success(Unit)
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("API Error", "SignUp failed: ${e.code()}, Body: $errorBody")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("API Error", "Unexpected SignUp error: ${e.localizedMessage}")
            Result.failure(e)
        }
    }

    override suspend fun signIn(user: User): Result<String> {
        return try {
            val response = authApi.signIn(user)
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!._id
                preferences.edit().putString("jwt", token).apply()
                Result.success(token)
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("API Error", "SignIn failed: ${response.code()}, Body: $errorBody")
                Result.failure(Exception("SignIn failed: ${response.code()}"))
            }
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("API Error", "SignIn failed: ${e.code()}, Body: $errorBody")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("API Error", "Unexpected SignIn error: ${e.localizedMessage}")
            Result.failure(e)
        }
    }
}
