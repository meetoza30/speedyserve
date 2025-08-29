package com.example.speedyserve.Models.ApiResponse

import com.example.speedyserve.Models.Order

data class placeOrderResponse(
    val message : String,
    val order : Order
)
