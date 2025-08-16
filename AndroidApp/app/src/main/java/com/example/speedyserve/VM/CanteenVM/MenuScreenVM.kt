package com.example.speedyserve.VM.CanteenVM

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedyserve.Models.Dishes.Dish
import com.example.speedyserve.Models.Dishes.DishesReq
import com.example.speedyserve.Repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuScreenVM @Inject constructor(private val repo: Repo) : ViewModel() {
    private val _menu : MutableStateFlow<List<Dish>> = MutableStateFlow(emptyList())
    val menu : StateFlow<List<Dish>> = _menu

    val isLoading : MutableStateFlow<Boolean> = MutableStateFlow(true)


    fun fetchDishes(canteenId : String , onError : (String)-> Unit){
        isLoading.value = true
        Log.d("dish","canteenId : $canteenId")
        val menuReq = DishesReq(canteenId)
        viewModelScope.launch {
            repo.getCanteenDishes(menuReq)
                .onSuccess {
                    result ->
                    _menu.value = result.dishes
                    Log.d("dishes",result.dishes.toString())
                    isLoading.value=false
                }
                .onFailure {
                    onError(it.message.toString())
                    isLoading.value = false
                    Log.d("dishes","error ${it.message}")
                }
        }

    }
}