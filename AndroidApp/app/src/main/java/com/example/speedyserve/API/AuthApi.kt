package com.example.speedyserve.API

import com.example.speedyserve.Models.ApiResponse.ApiResponse
import com.example.speedyserve.Models.ApiResponse.AuthData
import com.example.speedyserve.Models.AuthModels.UserSignInReq
import com.example.speedyserve.Models.AuthModels.UserSignUpReq
import com.example.speedyserve.Models.Token.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/signup")
    suspend fun signUp(@Body user: UserSignUpReq) : Response<ApiResponse<AuthData>>

    @POST("api/login")
    suspend fun signIn(@Body user: UserSignInReq) : Response<ApiResponse<AuthData>>

    @POST("api/reauth")
    suspend fun ReAuth(@Body token: Token) : Response<Token>

}