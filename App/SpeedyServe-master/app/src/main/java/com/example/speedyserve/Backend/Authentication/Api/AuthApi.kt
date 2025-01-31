package com.example.speedyserve.Backend.Authentication.Api

import com.example.speedyserve.Backend.Authentication.Models.TokenResponse
import com.example.speedyserve.Backend.Authentication.Models.User
import com.example.speedyserve.Backend.Authentication.Models.Canteen
import com.example.speedyserve.Backend.Authentication.Models.Dish
import com.example.speedyserve.Backend.Authentication.Models.OrderDish
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthApi {

    @POST("/api/signUp")
    suspend fun signUp(@Body user : User)

    @POST("/api/signIn")
    suspend fun signIn(@Body user: User) : Response<TokenResponse>

    @GET("/canteens/getCanteens")
    suspend fun getCanteenList() : List<Canteen>

    @POST("/canteens/getCanteenDishes")
    suspend fun getMenuList( @Body body: Map<String, String>) : DishResponse

    @POST("/orders/placeOrder")
    suspend fun placeOrder(@Body orderSchema: orderSchema)

}

data class orderSchema(
    val userId : String? =null,
    val canteenId : String ="679ba66c5a75c4d106e38cb7",
    val dishes : List<OrderDish>,
    val timeSlot : String,
    val totalPrice : String
)