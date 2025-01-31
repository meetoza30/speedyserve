package com.example.speedyserve.frontend.SignUpAndSignIn.SignIn

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.speedyserve.frontend.Navigation.Screens
import com.example.speedyserve.frontend.SignUpAndSignIn.AuthUiEvent
import com.example.speedyserve.frontend.SignUpAndSignIn.AuthViewModel
import com.example.speedyserve.frontend.SignUpAndSignIn.RoundedTextField


@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onLoggedin : NavHostController,  // Add NavController for navigation
    onSignUpClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    val onevent = viewModel::onEvent
    val state by remember { mutableStateOf(viewModel.state) }
    val isLoggedIn by viewModel._isLoggedIn.collectAsState()  // Observe login state
    val loggedIn by remember { mutableStateOf(false) }
    // Navigate when login is successful
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            Log.d("loggedin","true")
            onLoggedin.navigate(route = "${Screens.CANTEENSCREEN}")
        }
    }

    Column(
        modifier = modifier.fillMaxSize().background(color = Color(0xFFECEFF1)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onSkipClick, modifier = Modifier.align(Alignment.Start).padding(10.dp)) {
            Text(
                "Skip",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(0.dp)
            )
        }

        Spacer(modifier = Modifier.height(90.dp))
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome to", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text(
                text = "SpeedyServe",
                fontSize = 32.sp,
                color = Color(0xFFFF9800),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
        Text(
            "SignIn",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(start = 0.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))

        RoundedTextField(
            value = state.value.signInUsername,
            onValueChange = { onevent(AuthUiEvent.SignInUsernameChanged(it)) },
            placeHolderText = "Username",
            singleLine = true,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp),
            isPasswordTextField = false
        )

        Spacer(modifier = Modifier.height(25.dp))

        RoundedTextField(
            value = state.value.signInPassword,
            onValueChange = { onevent(AuthUiEvent.SignInPasswordChanged(it)) },
            placeHolderText = "Password",
            singleLine = true,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp),
            isPasswordTextField = true
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = { onevent(AuthUiEvent.SignIn) },
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 40.dp, end = 40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFF9800))
        ) {
            Text(text = "SignIn", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row {
            Text(
                "Not registered? Sign up first",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFFF9800),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            IconButton(onClick = onSignUpClick, modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowRightAlt, "Sign Up")
            }
        }
    }
}