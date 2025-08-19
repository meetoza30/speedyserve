package com.example.speedyserve.Screen.MenuScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedyserve.Models.Canteens.Canteen
import com.example.speedyserve.Models.Dishes.Dish
import com.example.speedyserve.Models.Dishes.DishesReq
import com.example.speedyserve.Repo.Repo
import com.example.speedyserve.Screen.AuthScreens.LoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class dishWithQuantity(
    val dish: Dish,
    val quantity : Int = 0
)



@HiltViewModel
class MenuScreenVM @Inject constructor(private val repo: Repo) : ViewModel() {
    private val _menu : MutableStateFlow<List<dishWithQuantity>> = MutableStateFlow(emptyList())
    val menu : StateFlow<List<dishWithQuantity>> = _menu

    private val _canteen : MutableStateFlow<Canteen> = MutableStateFlow(Canteen())
    val canteen : StateFlow<Canteen> = _canteen


    private val _cartOrder : MutableStateFlow<List<dishWithQuantity>> = MutableStateFlow(emptyList())
    val cartOrder : StateFlow<List<dishWithQuantity>> = _cartOrder

    val isLoading : MutableStateFlow<Boolean> = MutableStateFlow(true)


    fun fetchDishes(canteenId : String , onError : (String)-> Unit){
        isLoading.value = true
        Log.d("dish","canteenId : $canteenId")
        val menuReq = DishesReq(canteenId)
        viewModelScope.launch {
            repo.getCanteenDishes(menuReq)
                .onSuccess {
                    result ->
                    val dishes = result.dishes
                    _menu.value = dishes.map { dish ->
                        dishWithQuantity(dish)
                    }
                    _canteen.value = result.canteen


                    //checking if there is any global order already in cart and swappin it
                    val globalOrder = repo.cartOrderGlobal
                    if(globalOrder.value!=null){
                        _menu.value = _menu.value.map {dishWithQuantity ->
                            globalOrder.value!!.dishWithQuantity.value.find { dishWithQuantity.dish._id==it.dish._id }?:dishWithQuantity
                        }
                        _cartOrder.value = globalOrder.value!!.dishWithQuantity.value
                    }


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

//    fun onClickAdd(dish : Dish){
//        val current = _menu.value.toMutableList()
//        val existingIndex = current.indexOfFirst { it.dish._id == dish._id }
//
//        if (existingIndex >= 0) {
//            val existing = current[existingIndex]
//            current[existingIndex] = existing.copy(quantity = existing.quantity + 1)
//        } else {
//            current.add(dishWithQuantity(dish, 1))
//        }
//        _menu.value = current.toList()
//    }

    fun onClickAdd(clickeddishWithQuantity: dishWithQuantity){
        _menu.value = _menu.value.map {
        dishWithQuantity -> if(dishWithQuantity.dish._id==clickeddishWithQuantity.dish._id){
            dishWithQuantity.copy( quantity = dishWithQuantity.quantity+1 )
        }else{ dishWithQuantity } }

        val existIndex = _cartOrder.value.indexOfFirst { it.dish._id==clickeddishWithQuantity.dish._id }

        if(existIndex>=0){
            _cartOrder.value = _cartOrder.value.map {
                if (it.dish._id == clickeddishWithQuantity.dish._id) {
                    it.copy(quantity = it.quantity + 1)
                } else it
            }
        }else{
            val existIndexMenu =  menu.value.indexOfFirst { it.dish._id==clickeddishWithQuantity.dish._id }
            _cartOrder.value = _cartOrder.value + menu.value.get(existIndexMenu)
//            Log.d("cart order",clickeddishWithQuantity.toString())
        }
        Log.d("cart order",cartOrder.value.toString())
    }

    fun onClickMinus(clickeddishWithQuantity: dishWithQuantity , onError: (String) -> Unit){
        _menu.value = _menu.value.map {
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
        val existIndex = _cartOrder.value.indexOfFirst { it.dish._id==clickeddishWithQuantity.dish._id }

        if(existIndex>=0){
            _cartOrder.value = _cartOrder.value.map {
                if (it.dish._id == clickeddishWithQuantity.dish._id && it.quantity>0) {
                    it.copy(quantity = it.quantity - 1)
                } else it
            }
            if(cartOrder.value.get(existIndex).quantity==0){
                _cartOrder.value =_cartOrder.value.filter { it.dish._id!=cartOrder.value.get(existIndex).dish._id }
            }
        }else{
            onError("first add order")
        }
        Log.d("cart order",cartOrder.value.toString())

    }

    fun saveCartOrderToRepo(){
        repo.saveCartOrder(cartOrder = _cartOrder,
            canteenId = canteen.value._id)
    }

}