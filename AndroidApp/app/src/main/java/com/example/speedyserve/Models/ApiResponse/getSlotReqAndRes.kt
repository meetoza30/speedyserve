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
    val earliestPickup : PickupTime,
    val earilestPickupSlot : PickupTime,
    val slots : List<Slot>
)
data class Slot(
    val startISO : String,
    val endISO : String,
    val startHHMM : String,
    val endHHMM : String
)

data class PickupTime(
    val iso : String,
    val hhmm : String
)