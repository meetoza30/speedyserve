package com.example.speedyserve.Models.ApiResponse

import com.example.speedyserve.Models.Order
import com.example.speedyserve.Models.orderDishes

data class placeOrderResponse(
    val message : String,
    val order : responseOrder
)


data class responseOrder(
    val _id : String,
    val userId : String,
    val canteenId : String,
    val dishes : List<responseDish>,
    val totalPrice : Int,
    val timeSlot : String
)

data class responseDish(
    val dish : String ,//actually id
    val quantity : Int,
    val price : Int
)