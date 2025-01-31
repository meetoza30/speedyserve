package com.example.speedyserve.Backend.Authentication.Repo

import com.example.speedyserve.Backend.Authentication.Api.DishResponse
import com.example.speedyserve.Backend.Authentication.Api.orderSchema
import com.example.speedyserve.Backend.Authentication.Models.Canteen
import com.example.speedyserve.Backend.Authentication.Models.Dish

interface CanteenRepo {
    suspend fun getCanteens() : List<Canteen>
    suspend fun getDishes(_id : String) : DishResponse
    suspend fun placeOrder(orderSchema: orderSchema)
    suspend fun deleteList()
}