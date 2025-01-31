package com.example.speedyserve.Backend.Authentication.Api

import com.example.speedyserve.Backend.Authentication.Models.Dish
import kotlinx.serialization.Serializable

@Serializable
data class DishResponse(
    val success: Boolean,
    val canteen: String,
    val dishes: List<Dish>
)
