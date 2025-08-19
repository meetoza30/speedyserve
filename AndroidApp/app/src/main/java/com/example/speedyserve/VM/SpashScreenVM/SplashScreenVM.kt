package com.example.speedyserve.VM.SpashScreenVM

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedyserve.Repo.Repo
import com.example.speedyserve.utils.TokenManager
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenVM @Inject constructor(private val repo: Repo): ViewModel() {

    fun getTokenAndVerify(context: Context,onSuccess : ()-> Unit,onFailure : (String)-> Unit){
        val tokenManager = TokenManager(context)
        val token = tokenManager.getToken()
        Log.d("token", token.toString())
        viewModelScope.launch {
            if (token != null) {
                repo.checkAuthentication(token)
                    .onSuccess {
                        Log.d("token success", it.success.toString())
                        if (it.success) {
                            onSuccess()
                        } else {
                            onFailure("Invalid Token")
                        }
                    }
                    .onFailure {
                        Log.d("token success", it.message.toString())
                        onFailure(it.message.toString())
                    }
            }else{
                onFailure("No token")
            }
        }
    }
}