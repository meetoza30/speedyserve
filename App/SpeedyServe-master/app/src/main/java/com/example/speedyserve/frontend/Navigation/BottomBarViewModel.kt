package com.example.speedyserve.frontend.Navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BottomBarViewModel @Inject constructor(): ViewModel() {
    private var _currentScreen = mutableStateOf(Screens.CANTEENSCREEN.name)
    val currentScreen : State<String>
        get() =_currentScreen

    fun OnBottomBarActions(events: BottomNavigationEvents){
        when(events){
            is BottomNavigationEvents.Onclick -> {
                if(events.ScreenName!=currentScreen.toString()){
                    _currentScreen.value=events.ScreenName
                    events.navController.navigate(events.ScreenName)
                }
            }

            is BottomNavigationEvents.setState -> {
                events.route?.let{
                    _currentScreen.value=it
                }
            }
        }
    }

}