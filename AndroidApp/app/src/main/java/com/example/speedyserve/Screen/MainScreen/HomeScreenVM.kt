package com.example.speedyserve.Screen.MainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedyserve.Models.Canteens.Canteen
import com.example.speedyserve.Repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(private val repo: Repo): ViewModel() {

    private val _canteenList : MutableStateFlow<List<Canteen>> = MutableStateFlow(emptyList())
    val canteenList : StateFlow<List<Canteen>> = _canteenList
    val isLoading : MutableStateFlow<Boolean> = MutableStateFlow(false)



    fun fetchCanteens(onError : (String)-> Unit){
        isLoading.value = true
        viewModelScope.launch {
            repo.getCanteenList()
                .onSuccess {
                    _canteenList.value = it.data
                    isLoading.value=false
                    Log.d("Canteens", it.toString())
                }
                .onFailure {
                    Log.d("Canteens",it.message.toString())
                    isLoading.value=false
                    onError(it.message.toString())
                }
        }
    }

}