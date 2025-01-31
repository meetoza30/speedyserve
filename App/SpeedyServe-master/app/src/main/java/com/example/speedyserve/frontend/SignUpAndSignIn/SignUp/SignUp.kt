package com.example.speedyserve.frontend.SignUpAndSignIn.SignUp


import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.speedyserve.frontend.SignUpAndSignIn.AuthUiEvent
import com.example.speedyserve.frontend.SignUpAndSignIn.AuthViewModel
import com.example.speedyserve.frontend.SignUpAndSignIn.RoundedTextField

@Composable
fun SignUpScreen(modifier: Modifier = Modifier,
                 viewmodel: AuthViewModel = hiltViewModel(),
                 onSignInClick : ()-> Unit,
//                 navController: NavController
){
val context = LocalContext.current
    val onevent =viewmodel::onEvent
    val state=viewmodel.state
    val isSignup = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if(isSignup.value){
            Log.d("check1",isSignup.value.toString())
            Toast.makeText(context,"You are Registered", Toast.LENGTH_SHORT).show()

        }
    }
    Column(
        modifier = modifier.fillMaxSize().background(color = Color(0xFFECEFF1)),
        // verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.height(50.dp))
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome to" , fontSize = 14.sp  , fontWeight = FontWeight.SemiBold)
            Text(text = "SpeedyServe" , fontSize = 32.sp , color = Color(0xFFFF9800), fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text("SignUp" , fontSize = 18.sp , fontWeight = FontWeight.Bold ,
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(0.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
        RoundedTextField(
            value = state.value.signUpUsername,
            onValueChange = { onevent(AuthUiEvent.SignUpUsernameChanged(it)) },
            placeHolderText = "Username",
            singleLine = true,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp),
            isPasswordTextField = false)
        Spacer(modifier = Modifier.height(25.dp))
        RoundedTextField(
            value = state.value.email,
            onValueChange = { onevent(AuthUiEvent.SignUpEmail(it)) },
            placeHolderText = "Email",
            singleLine = true,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp),
            isPasswordTextField = false)
        Spacer(modifier = Modifier.height(25.dp))
        RoundedTextField(
            value = state.value.phoneNumber,
            onValueChange = { onevent(AuthUiEvent.SignUpMobileNum(it)) },
            placeHolderText = "Phone no.",
            singleLine = true,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp),
            isPasswordTextField = false)
        Spacer(modifier = Modifier.height(25.dp))
        RoundedTextField(
            value = state.value.signUpPassword,
            onValueChange = { onevent(AuthUiEvent.SignUpPasswordChanged(it)) },
            placeHolderText = "Password",
            singleLine = true,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp),
            isPasswordTextField = true)
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {
            onevent(AuthUiEvent.SignUp)
            isSignup.value =true
        },
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 40.dp , end = 40.dp).background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFFF9800), Color(0xFF995B00)),
                    center = Offset(0f, 0f), // Gradient center
                    radius = 400f // Radius of the gradient
                ),
                shape = RoundedCornerShape(12.dp) // Rounded corners
            ),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
        ) {
            Text(
                text = "SignUp",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF),
                ) )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row {  Text("Already have an account? Login" ,
            modifier = Modifier.align(Alignment.CenterVertically) ,
            fontSize = 14.sp  ,
            fontWeight = FontWeight.Medium ,
            color = Color(0xFFFF9800))

            IconButton(onClick = onSignInClick) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowRightAlt,
                    "icon right",
                    modifier= Modifier.align(Alignment.CenterVertically))
            }
        }

    }
}

//@Preview
//@Composable
//fun SignUpPreview() {
//    SignUpScreen(navController = rememberNavController())
//}