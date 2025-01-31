package com.example.speedyserve.frontend.MenuScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedyserve.Backend.Authentication.Models.Dish
import com.example.speedyserve.Backend.Authentication.Models.OrderDish
import com.example.speedyserve.Backend.Authentication.Repo.CanteenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuScreenViewModel @Inject constructor(
    private val repoImpl: CanteenRepoImpl,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _orderedDishList = mutableListOf<OrderDish>()
    val ordereddishList = _orderedDishList

    private val _menu = MutableStateFlow<List<Dish>>(emptyList())
    val menu = _menu

    val isLoading = mutableStateOf(true)
    val isDishListEmpty = mutableStateOf(true)

    init {
        savedStateHandle.get<String>("id")?.let { canteenId ->
            Log.d("check", canteenId)
            onEvent(MenuScreenActions.fetchMenu(canteenId))
        }
    }

    fun onEvent(event: MenuScreenActions) {
        when (event) {
            is MenuScreenActions.fetchMenu -> {

                viewModelScope.launch {
                    isLoading.value = true
                    val fetchedMenu = repoImpl.getDishes(event._id).dishes
                    _menu.value = fetchedMenu
                    isLoading.value = false

                }
            }

            is MenuScreenActions.addDish -> {
                val existingDish = _orderedDishList.find { it.foodId == event.Dish._id }

                if (existingDish != null) {
                    // Increase quantity of existing dish
                    val index = _orderedDishList.indexOf(existingDish)
                    _orderedDishList[index] = existingDish.copy(quantity = existingDish.quantity + 1)
                } else {
                    // Add new dish to the list
                    _orderedDishList.add(
                        OrderDish(
                            dishname = event.Dish.name,
                            quantity = 1,
                            foodId = event.Dish._id,
                            price = event.Dish.price,
                            isInCart = true
                        )
                    )

                }
                isDishListEmpty.value = _orderedDishList.isEmpty()

                Log.d("clicked", "addclick")
                Log.d("cheklost", ordereddishList.toString())

                viewModelScope.launch{
                    repoImpl.updateList(_orderedDishList)
                }
            }
        }
    }
}
