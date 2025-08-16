package com.example.speedyserve.Repo

import android.util.Log
import coil.network.HttpException
import com.example.speedyserve.API.AuthApi
import com.example.speedyserve.Models.ApiResponse.ApiResponse
import com.example.speedyserve.Models.ApiResponse.AuthData
import com.example.speedyserve.Models.AuthModels.UserSignInReq
import com.example.speedyserve.Models.AuthModels.UserSignUpReq
import com.example.speedyserve.Models.Token.Token
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Response
import org.json.JSONObject
import javax.inject.Inject


class Repo @Inject constructor(private val authApi: AuthApi)  {
    suspend fun signUp(user: UserSignUpReq): Result<ApiResponse<AuthData>> {
        return try {
            val response = authApi.signUp(user)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val jsonObject = JSONObject(errorBody)
                    jsonObject.getString("message")
                } catch (e: Exception) {
                    "Unknown error"
                }
                Result.failure(Exception("Sign-up failed: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signIn(user: UserSignInReq): Result<ApiResponse<AuthData>> {
        return try {
            val response = authApi.signIn(user)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val jsonObject = JSONObject(errorBody)
                    jsonObject.getString("message")
                } catch (e: Exception) {
                    "Unknown error"}
                Result.failure(Exception("Login failed: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}