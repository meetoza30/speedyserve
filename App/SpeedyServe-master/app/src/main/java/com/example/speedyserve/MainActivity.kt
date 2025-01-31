package com.example.speedyserve

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.speedyserve.frontend.BottomNavigationBAr.BottomNavigationBar
import com.example.speedyserve.frontend.Navigation.BottomBarViewModel
import com.example.speedyserve.frontend.Navigation.Navigation
import com.example.speedyserve.frontend.SignUpAndSignIn.SignUp.SignUpScreen
import com.example.speedyserve.ui.theme.SpeedyServeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpeedyServeTheme {
                val NavController =  rememberNavController()
                val bottomNavigationBarViewmodel : BottomBarViewModel = hiltViewModel()
                Scaffold(topBar = {},
                    bottomBar = {
                        BottomNavigationBar(bottomNavigationBarViewmodel,
                            navController = NavController)
                    }) {
                        padding->
                    Box(modifier = Modifier.fillMaxSize()
                        .padding(padding)) {
                        Navigation(bottomNavigationBarViewmodel,
                            navController = NavController)
                    }
                }
            }
        }
    }
}



