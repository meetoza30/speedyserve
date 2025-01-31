package com.example.speedyserve.Backend.Authentication.Models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDish(
    @SerializedName("foodName")
    val dishname : String="",
    var quantity: Int =0,
    val price: String="0",
    val foodId: String="",
    val isInCart : Boolean =false
)

