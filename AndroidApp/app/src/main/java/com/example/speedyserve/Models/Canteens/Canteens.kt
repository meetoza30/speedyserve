package com.example.speedyserve.Models.Canteens

data class Canteen(
    val _id : String= "",
    val name : String = "",
    val mobile : String = "",
    val emailId : String = "",
    val slots : List<String> = emptyList(),
)

data class CanteenResponse (
    val success : Boolean,
    val data : List<Canteen>
)