package com.example.speedyserve.Backend.Authentication.Models

import kotlinx.serialization.Serializable

@Serializable
data class Canteen(
    val _id : String,
    val name : String,
//    val password : String,
    val listDish : List<Dish>,
//    val mobile : String,
    val openingTime : String,
    val closingTime : String,
//    val emailId : String

)

