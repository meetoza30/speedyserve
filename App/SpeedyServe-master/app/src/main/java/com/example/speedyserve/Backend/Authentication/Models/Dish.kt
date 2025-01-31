package com.example.speedyserve.Backend.Authentication.Models

import kotlinx.serialization.Serializable

@Serializable
data class Dish(
    val _id : String,
    val name : String,
    val description : String,
    val price : String,
    val image : String,
    val category : String
)
