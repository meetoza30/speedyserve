package com.example.speedyserve.Screen.CartScreen

import android.util.Log
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.speedyserve.Repo.Repo
import com.example.speedyserve.Screen.MenuScreen.dishWithQuantity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class CartVm @Inject constructor(private val repo: Repo) : ViewModel() {

    private val _cartDishes : MutableStateFlow<List<dishWithQuantity>> = MutableStateFlow(emptyList())
    val cartDishes : StateFlow<List<dishWithQuantity>> = _cartDishes

    private val _canteenId : MutableState<String?> = mutableStateOf(null)

    fun fetchDishes(onError : (String)-> Unit){
        val repodata = repo.cartOrderGlobal
        val cartOrder = repodata.value?.dishWithQuantity
        val canteenId = repodata.value?.canteenId

        if (cartOrder==null){
            onError("error while fetching cartOders")
            Log.d("Cart","Repo cartorderglobal is null")
        }else{
            _cartDishes.value = cartOrder.value
        }

        if (canteenId==null){
            onError("error while fetching canteenId")
            Log.d("Cart","Repo cartorderglobal is null")
        }else{
            _canteenId.value = canteenId
        }

    }

    fun onClickAdd(clickeddishWithQuantity: dishWithQuantity){
        _cartDishes.value = _cartDishes.value.map {
                dishWithQuantity -> if(dishWithQuantity.dish._id==clickeddishWithQuantity.dish._id){
            dishWithQuantity.copy( quantity = dishWithQuantity.quantity+1 )
        }else{ dishWithQuantity } }
    }

    fun onClickMinus(clickeddishWithQuantity: dishWithQuantity , onError: (String) -> Unit){
        _cartDishes.value = _cartDishes.value.map {
                dishWithQuantity ->
            if(dishWithQuantity.dish._id==clickeddishWithQuantity.dish._id){
                if(dishWithQuantity.quantity<=0){
                    onError("First Add dish")
                    dishWithQuantity
                }else{
                    dishWithQuantity.copy(
                        quantity = dishWithQuantity.quantity-1
                    )
                }
            }else{
                dishWithQuantity
            }
        }
        if (cartDishes.value.first { it.dish._id == clickeddishWithQuantity.dish._id }.quantity==0){
            _cartDishes.value=_cartDishes.value.filter { it.dish._id!=clickeddishWithQuantity.dish._id }
        }
    }
}