package com.example.speedyserve.frontend.Navigation

import androidx.navigation.NavController

sealed interface BottomNavigationEvents {
    data class Onclick(val ScreenName : String,
                       val navController: NavController): BottomNavigationEvents
    data class setState(val route: String?) : BottomNavigationEvents
}