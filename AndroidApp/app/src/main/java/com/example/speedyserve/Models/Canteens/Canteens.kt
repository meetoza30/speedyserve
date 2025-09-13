package com.example.speedyserve.Models.Canteens

import kotlinx.serialization.SerialName

data class Canteen(
    val _id : String= "",
    val name : String = "",
    val mobile : String = "",
    val emailId : String = "",
//    val slots : List<String> = emptyList(),
    val openingTime : String = "",
    val closingTime : String = "",
    val short_descripiton : String = "",
    val rating : Double = 5.0,
    val image : String = ""
    )

data class CanteenResponse (
    val success : Boolean,
    val data : List<Canteen>
)