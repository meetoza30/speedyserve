package com.example.speedyserve.frontend.cartScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedyserve.Backend.Authentication.Api.orderSchema
import com.example.speedyserve.Backend.Authentication.Models.Order
import com.example.speedyserve.Backend.Authentication.Models.OrderDish
import com.example.speedyserve.Backend.Authentication.Repo.CanteenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CartViewmodel @javax.inject.Inject constructor(private val repoImpl: CanteenRepoImpl) : ViewModel() {
    var orderdishlist = mutableStateListOf<OrderDish>()
    var timeslot = mutableStateOf("4.00 PM - 5.00 PM")

    init {
        orderdishlist.addAll(repoImpl.orderDishList)
    }

    fun onEvent(event: CartScreenActions) {
        when (event) {
            is CartScreenActions.DecreaseQuantity -> {
                orderdishlist.find { it.dishname == event.Dish.dishname }?.let { dish ->
                    if (dish.quantity > 1) {
                        dish.quantity--
                    }
                }
            }

            is CartScreenActions.IncreaseQuantity -> {
                orderdishlist.find { it.dishname == event.Dish.dishname }?.let { dish ->
                    dish.quantity++
                }
            }

            is CartScreenActions.TimeSlot -> {
                timeslot.value = event.timemslot
            }

            is CartScreenActions.placeOrder -> {
                val order = orderSchema(
                    dishes = orderdishlist,
                    timeSlot = timeslot.value,
                    totalPrice = calculate().toString()
                )
                viewModelScope.launch {
                    repoImpl.placeOrder(order)
                    // Clear the list by creating a new instance
                    orderdishlist = mutableStateListOf()
                    repoImpl.deleteList() // Delete the list in the repository
                }
            }
        }
    }
    internal fun calculate(): Double {
        return orderdishlist.sumOf { dish ->
            val price = dish.price.toDoubleOrNull() ?: 0.0 // Convert price to Double, default to 0.0 if invalid
            price * dish.quantity
        }
    }
}
