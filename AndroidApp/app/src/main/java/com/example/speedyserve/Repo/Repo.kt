package com.example.speedyserve.Repo

import com.example.speedyserve.API.Apis
import com.example.speedyserve.Models.ApiResponse.ApiResponse
import com.example.speedyserve.Models.AuthModels.UserSignInReq
import com.example.speedyserve.Models.AuthModels.UserSignUpReq
import com.example.speedyserve.Models.Canteens.Canteen
import com.example.speedyserve.Models.Canteens.CanteenResponse
import org.json.JSONObject
import javax.inject.Inject


class Repo @Inject constructor(private val apis: Apis)  {
    suspend fun signUp(user: UserSignUpReq): Result<ApiResponse> {
        return try {
            val response = apis.signUp(user)
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

    suspend fun signIn(user: UserSignInReq): Result<ApiResponse> {
        return try {
            val response = apis.signIn(user)
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

    suspend fun getCanteenList() : Result<CanteenResponse>{
        return try {
            val response = apis.getCanteens()
            if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                }?: Result.failure(Exception("Empty Response Body"))
            }else{
                val errorBody = response.errorBody()?.toString()
                val errorMessage = try{
                    val jsonObject = JSONObject(errorBody)
                    jsonObject.getString("message")
                }catch (e : Exception){
                    "Unknown Error"
                }
                Result.failure(Exception("Fetching Canteens failed : $errorMessage"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }

    }
}