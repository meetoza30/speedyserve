package com.example.speedyserve.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.speedyserve.Screen.AuthScreens.LoginScreen
import com.example.speedyserve.Screen.AuthScreens.SignUpScreen
import com.example.speedyserve.Screen.MainScreen.HomeScreen
import com.example.speedyserve.ViewModels.AuthVM.AuthVM

@Composable
fun NavigationApp(navController : NavHostController,
                  modifier: Modifier = Modifier) {
    val AuthVM : AuthVM = hiltViewModel()
    NavHost(navController = navController , startDestination = Screen.HOMESCREEN.name ) {

        //authScreens
         composable(route = Screen.SIGNIN.name) {

                 LoginScreen(AuthVM,navController)

         }

        composable(route = Screen.SIGNUP.name) {
                SignUpScreen(AuthVM)
        }

        //canteenScreens

        composable (route = Screen.HOMESCREEN.name){
            HomeScreen(hiltViewModel())
        }


    }
}