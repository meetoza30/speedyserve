package com.example.speedyserve.Models.ApiResponse

import kotlinx.serialization.Serializable

@Serializable
data class getSlotReq(
    val canteenId : String,
    val orderedDishes : List<dishslotReq>
)

@Serializable
data class dishslotReq(
    val dishId : String,
    val quantity : Int
)

data class getSlotRes(
    val earliestPickupFormatted: String,
    val availableSlots : List<Slots>
)
data class Slots(
    val canteenId: String,
    val startTime : String,
    val endTime : String,
    val maxOrders : Int
)