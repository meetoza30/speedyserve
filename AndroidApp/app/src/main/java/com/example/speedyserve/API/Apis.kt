package com.example.speedyserve.API

import com.example.speedyserve.Models.ApiResponse.ApiResponse
import com.example.speedyserve.Models.ApiResponse.CheckAuthResponse
import com.example.speedyserve.Models.ApiResponse.TokenReq
import com.example.speedyserve.Models.ApiResponse.getSlotReq
import com.example.speedyserve.Models.ApiResponse.getSlotRes
import com.example.speedyserve.Models.AuthModels.UserSignInReq
import com.example.speedyserve.Models.AuthModels.UserSignUpReq
import com.example.speedyserve.Models.Canteens.Canteen
import com.example.speedyserve.Models.Canteens.CanteenResponse
import com.example.speedyserve.Models.Dishes.DishesReq
import com.example.speedyserve.Models.Dishes.DishesResponse
import com.example.speedyserve.Models.Token.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Apis {

    //authApis
    @POST("api/signup")
    suspend fun signUp(@Body user: UserSignUpReq) : Response<ApiResponse>

    @POST("api/login")
    suspend fun signIn(@Body user: UserSignInReq) : Response<ApiResponse>

    @POST("api/check-auth")
    suspend fun checkAuth(@Body token: TokenReq) : Response<CheckAuthResponse>

    //canteenApis
    @GET("api/getCanteens")
    suspend fun getCanteens() : Response<CanteenResponse>

    @POST("api/getCanteenDishes")
    suspend fun getCanteenDishes(@Body canteenId : DishesReq) : Response<DishesResponse>

    @POST("api/getSlots")
    suspend fun getTimeSlots(@Body slotreq : getSlotReq) : Response<getSlotRes>
}