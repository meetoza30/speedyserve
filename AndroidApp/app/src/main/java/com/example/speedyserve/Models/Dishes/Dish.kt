package com.example.speedyserve.Models.Dishes

import com.example.speedyserve.Models.Canteens.Canteen
import kotlinx.serialization.Serializable

data class Dish(
    val canteenId : String,
    val name : String,
    val price : String,
    val image : String,
    val description : String,
    val serveTime : String,
    val category : String
)

data class DishesResponse(
    val success : Boolean ,
    val dishes : List<Dish>,
    val canteen : Canteen
)

@Serializable
data class DishesReq(
    val canteenId: String
)