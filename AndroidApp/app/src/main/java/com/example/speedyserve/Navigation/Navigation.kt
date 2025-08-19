package com.example.speedyserve.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavArgs
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.clubsconnect.FrontEnd.commonscreen.SplashScreen.SplashScreen
import com.example.speedyserve.Screen.AuthScreens.LoginScreen
import com.example.speedyserve.Screen.AuthScreens.SignUpScreen
import com.example.speedyserve.Screen.MainScreen.HomeScreen
import com.example.speedyserve.ViewModels.AuthVM.AuthVM
import com.speedyserve.ui.screens.MenuScreen

@Composable
fun NavigationApp(navController : NavHostController,
                  modifier: Modifier = Modifier) {
    val AuthVM : AuthVM = hiltViewModel()
    NavHost(navController = navController , startDestination = Screen.SPLASHSCREEN.name ) {

        composable (route = Screen.SPLASHSCREEN.name){
            SplashScreen(hiltViewModel(),navController)
        }

        //authScreens
         composable(route = Screen.SIGNIN.name) {

                 LoginScreen(AuthVM,navController)

         }

        composable(route = Screen.SIGNUP.name) {
                SignUpScreen(AuthVM, navController = navController)
        }

        //canteenScreens

        composable (route = Screen.HOMESCREEN.name){
            HomeScreen(hiltViewModel(), onCanteenClick = {
                navController.navigate("${Screen.MENUSCREEN.name}/$it")
            })
        }

        composable(route = "${Screen.MENUSCREEN.name}/{canteenId}") {
            backstackEntry->
            val canteenId = backstackEntry.arguments?.getString("canteenId")?:""
            MenuScreen(canteenId = canteenId, viewmodel = hiltViewModel())
        }


    }
}