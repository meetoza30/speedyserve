package com.example.speedyserve.utils

import android.content.Context
import androidx.core.content.edit
import com.example.speedyserve.Models.ApiResponse.UserInfo

class SaveUserInfo (context : Context) {

   private val sharedPreferences = context.getSharedPreferences("speedyserve_user_prefs",Context.MODE_PRIVATE)

    fun getUserInfo() : UserInfo{
        val userName = sharedPreferences.getString("userName",null)?:""
        val email = sharedPreferences.getString("userEmail",null)?:""
        val mobile = sharedPreferences.getString("userMobile",null)?:""
        return UserInfo(userName,email,mobile)
    }
    fun saveUserInfo(userInfo: UserInfo){
        sharedPreferences.edit{putString("userName",userInfo.username)}
        sharedPreferences.edit { putString("userEmail",userInfo.email) }
        sharedPreferences.edit{putString("userMobile",userInfo.mobile)}
    }

    fun removeUser(){
        sharedPreferences.edit{remove("speedyserve_user_prefs")}
    }
}