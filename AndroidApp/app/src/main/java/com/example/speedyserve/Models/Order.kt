package com.example.speedyserve.Models

import com.example.speedyserve.Models.ApiResponse.Slot
import com.example.speedyserve.Models.Dishes.Dish
import com.example.speedyserve.Screen.MenuScreen.dishWithQuantity
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val userId : String,
    val canteenId : String,
    val dishes : List<orderDishes>,
    val timeSlot : String,
    val totalPrice : Int,
    val preptime : Int = 0
)

data class orderDishes(
    val dish : Dish,
    val quantity : Int,
    val price : Int
)
