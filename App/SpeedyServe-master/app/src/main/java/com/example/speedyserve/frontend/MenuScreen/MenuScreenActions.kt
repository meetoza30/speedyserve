package com.example.speedyserve.frontend.MenuScreen

import com.example.speedyserve.Backend.Authentication.Models.Dish
import com.example.speedyserve.Backend.Authentication.Models.OrderDish

sealed interface MenuScreenActions {
    data class fetchMenu(val _id : String) : MenuScreenActions
    data class addDish(val Dish: Dish) : MenuScreenActions
}