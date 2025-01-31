package com.example.speedyserve.frontend.SignUpAndSignIn

data class AuthState(
    val isLoading: Boolean = false,
    val signUpUsername: String = "",
    val signUpPassword: String = "",
    val signInUsername: String = "",
    val signInPassword: String = "",
    val email  : String = "",
    val phoneNumber: String= "",
)
