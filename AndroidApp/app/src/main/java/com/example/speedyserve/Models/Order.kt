package com.example.speedyserve.Models

import com.example.speedyserve.Models.ApiResponse.Slot
import com.example.speedyserve.Screen.MenuScreen.dishWithQuantity

data class Order(
    val canteenId : String,
    val dishes : dishWithQuantity,
    val selectedSlot : Slot,
    val TotalSum : String
)
