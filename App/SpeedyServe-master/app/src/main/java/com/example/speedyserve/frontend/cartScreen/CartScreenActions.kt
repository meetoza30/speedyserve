package com.example.speedyserve.frontend.cartScreen

import com.example.speedyserve.Backend.Authentication.Models.OrderDish
import com.example.speedyserve.frontend.MenuScreen.MenuScreenActions

sealed interface CartScreenActions {
    data class IncreaseQuantity(val Dish : OrderDish) : CartScreenActions
    data class DecreaseQuantity(val Dish: OrderDish) : CartScreenActions
    data class TimeSlot(val timemslot : String) : CartScreenActions
    object placeOrder : CartScreenActions
}