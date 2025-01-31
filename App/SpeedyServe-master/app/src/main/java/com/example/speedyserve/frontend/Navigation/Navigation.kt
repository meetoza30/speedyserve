package com.example.speedyserve.frontend.Navigation

import CartScreen
import MenuScreen
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavArgs
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.speedyserve.frontend.CanteenScreen.HomeScreen
import com.example.speedyserve.frontend.MenuScreen.MenuScreenViewModel
import com.example.speedyserve.frontend.ProfileScreen.ProfileScreen
import com.example.speedyserve.frontend.SignUpAndSignIn.SignIn.SignInScreen
import com.example.speedyserve.frontend.SignUpAndSignIn.SignUp.SignUpScreen

@Composable
fun Navigation(bottomNavigationBarViewmodel: BottomBarViewModel,
               navController: NavHostController,
               modifier: Modifier = Modifier) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    val context= LocalContext.current
    var backpressedOnce by remember{ mutableStateOf(false) }
    val exithandler = rememberUpdatedState { backpressedOnce = false }
    if(backpressedOnce==true){
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(2000)
            exithandler.value()
        }
    }

    // Update the BottomNavigationBarViewmodel whenever the route changes
    LaunchedEffect(currentRoute) {
        currentRoute?.let {
            bottomNavigationBarViewmodel.OnBottomBarActions(BottomNavigationEvents.setState(it))
        }
    }


    NavHost(navController = navController, startDestination = Screens.SIGNIN.name,
        enterTransition ={fadeIn()},
        exitTransition ={fadeOut()},
        popEnterTransition ={fadeIn()},
        popExitTransition = {fadeOut()},
    ){
        composable(route="${Screens.SIGNUP}"){
            BackHandler {
                if(backpressedOnce){
                    (context as? Activity)?.finish()
                }else{
                    backpressedOnce=true
                    Toast.makeText(context,"Press again to exit the app", Toast.LENGTH_SHORT).show()
                }
            }

            SignUpScreen(onSignInClick = {navController.navigate(route="${Screens.SIGNIN}")})
        }
        composable(route= "${Screens.SIGNIN}"){
            BackHandler {
                if(backpressedOnce){
                    (context as? Activity)?.finish()
                }else{
                    backpressedOnce=true
                    Toast.makeText(context,"Press again to exit the app", Toast.LENGTH_SHORT).show()
                }
            }
            SignInScreen(onLoggedin = navController,
                onSignUpClick = {navController.navigate(route ="${Screens.SIGNUP}")},
                onSkipClick = {navController.navigate(route="${Screens.CANTEENSCREEN}")})
        }
        composable(route = "${Screens.CANTEENSCREEN}",
        ){
            BackHandler {
                if(backpressedOnce){
                    (context as? Activity)?.finish()
                }else{
                    backpressedOnce=true
                    Toast.makeText(context,"Press again to exit the app", Toast.LENGTH_SHORT).show()
                }
            }
            HomeScreen(onCanteenClick = {navController.navigate(route = "${Screens.MenuScreen}/$it")})
        }
        composable(route = "${Screens.CARTSCREEN}"){
            CartScreen()
        }
        composable(route = "${Screens.PROFILESCREEN}",){
            ProfileScreen()
        }
        composable(route = "${Screens.MenuScreen}/{id}",
            arguments = listOf(
                navArgument("id"){
                    type= NavType.StringType
                }
            )){
            MenuScreen(navController)
        }

    }

}