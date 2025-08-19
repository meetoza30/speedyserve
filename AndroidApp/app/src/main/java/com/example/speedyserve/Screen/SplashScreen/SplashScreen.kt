package com.example.clubsconnect.FrontEnd.commonscreen.SplashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.speedyserve.Navigation.Screen
import com.example.speedyserve.R
import com.example.speedyserve.VM.SpashScreenVM.SplashScreenVM
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(viewModel : SplashScreenVM,
                 navController: NavController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(true) {
         // 2-second splash delay

        viewModel.getTokenAndVerify(
            context,
            onSuccess = {
                navController.navigate(Screen.HOMESCREEN.name)
            },
            onFailure = {
                navController.navigate(Screen.SIGNIN.name)
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3F51B5)), // Indigo background
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Logo",
                tint = Color.White,
                modifier = Modifier.size(200.dp)
            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text("My App", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}
