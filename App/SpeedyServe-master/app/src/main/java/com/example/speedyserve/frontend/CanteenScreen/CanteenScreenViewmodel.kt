package com.example.speedyserve.frontend.CanteenScreen


import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedyserve.Backend.Authentication.Models.Canteen
import com.example.speedyserve.Backend.Authentication.Repo.CanteenRepo
import com.example.speedyserve.Backend.Authentication.Repo.CanteenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CanteenScreenViewModel @Inject constructor(private val repo: CanteenRepoImpl) : ViewModel() {

    private val _canteens = MutableStateFlow<List<Canteen>>(emptyList())
    val canteens = _canteens.asStateFlow() // Expose as immutable StateFlow

    init {
        onEvent(event = CanteenActions.fetchCanteens)
    }
    fun onEvent(event: CanteenActions) {
        when (event) {
            is CanteenActions.fetchCanteens -> {
                viewModelScope.launch {
                    val fetchedCanteens = repo.getCanteens() // Fetch data from repository
                    Log.d("listcheck ", fetchedCanteens.toList().toString())
                    _canteens.value=fetchedCanteens
                }
            }
        }
    }
}