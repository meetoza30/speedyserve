package com.example.speedyserve.Models.ApiResponse

data class UserInfo(
    val username : String,
    val email : String,
    val mobile  : String
)

data class UserInfoRes(
    val success : Boolean,
    val message : String,
    val user : UserInfo
)