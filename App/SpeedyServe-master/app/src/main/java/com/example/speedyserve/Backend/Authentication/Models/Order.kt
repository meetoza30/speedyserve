package com.example.speedyserve.Backend.Authentication.Models

data class Order(
    val _id: String,
    val userId: String,
    val canteenId: String,
    val dishes: List<OrderDish>,
    val totalPrice: Double,
    val status: String,
    val orderTime: String
)