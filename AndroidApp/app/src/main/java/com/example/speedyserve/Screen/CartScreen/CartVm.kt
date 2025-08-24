package com.example.speedyserve.Screen.CartScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedyserve.Models.ApiResponse.PickupTime
import com.example.speedyserve.Models.ApiResponse.Slot
import com.example.speedyserve.Models.ApiResponse.dishslotReq
import com.example.speedyserve.Models.ApiResponse.getSlotReq
import com.example.speedyserve.Models.Order
import com.example.speedyserve.Repo.CartOrder
import com.example.speedyserve.Repo.Repo
import com.example.speedyserve.Screen.MenuScreen.dishWithQuantity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartVm @Inject constructor(private val repo: Repo) : ViewModel() {

    private val _order : MutableStateFlow<Order?> = MutableStateFlow(null)
    val order : StateFlow<Order?> = _order

    private val _cartDishes : MutableStateFlow<List<dishWithQuantity>> = MutableStateFlow(emptyList())
    val cartDishes : StateFlow<List<dishWithQuantity>> = _cartDishes


    private val _slots : MutableStateFlow<List<Slot>> = MutableStateFlow(emptyList())
    val slots : StateFlow<List<Slot>> = _slots

    private val _selectedSlot : MutableStateFlow<Slot> = MutableStateFlow(Slot("","","",""))
    val selectedSlot : StateFlow<Slot> = _selectedSlot


    private val _earliestPickupSlot : MutableStateFlow<PickupTime?> = MutableStateFlow(null)
    val earliestPickupSlot : StateFlow<PickupTime?> =_earliestPickupSlot


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
            //calling to get time slots
            getSlotDetails(onError)

            Log.d("getslot","canteenId $canteenId")
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

    fun updateRepo(){
        if(_canteenId.value!=null) {
            repo._cartOrderGlobal.value = CartOrder(
                canteenId = _canteenId.value!!,
                dishWithQuantity = _cartDishes
            )
        }
    }

    fun getSlotDetails(onError: (String) -> Unit){
        val slotDishes = cartDishes.value.map {
            dishslotReq(
                dishId = it.dish._id,
                quantity = it.quantity
            )
        }
        Log.d("getslotDishesreq",slotDishes.toString())
        viewModelScope.launch {
            val result = repo.getSlots(getSlotReq(
                canteenId = _canteenId.value?:"",
                slotDishes
            ))
            result.onSuccess {
                _slots.value=it.slots
                _earliestPickupSlot.value = it.earilestPickupSlot
                _selectedSlot.value = slots.value.get(0)
                Log.d("getslots",it.slots.toString())
            }
                .onFailure {
                    onError(it.message.toString())
                    Log.d("getSlots",it.message.toString())
                }

        }


    }

    fun updateSelectedTimeSlot(slot : Slot){
        _selectedSlot.value = slot
    }
}