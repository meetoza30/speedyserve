package com.example.speedyserve.ViewModels.AuthVM

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedyserve.Models.AuthModels.UserSignInReq
import com.example.speedyserve.Models.AuthModels.UserSignUpReq
import com.example.speedyserve.Repo.Repo
import com.example.speedyserve.utils.TokenManager
import com.example.speedyserve.utils.extractUserIdFromJWT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor(private val repo: Repo) : ViewModel() {

    fun signUp(context: Context,name: String, email: String, password: String,mobile : String,onResult : (String)-> Unit) {
        val user = UserSignUpReq(
            username = name,
            email = email,
            password = password,
            mobile = mobile//hardcoded for now just to test
        )
        viewModelScope.launch {
            repo.signUp(user)
                .onSuccess {
                    result->
                    Log.d("loging",result.data.toString())
                    val tokenManager = TokenManager(context)
                    tokenManager.saveToken(result.data)
                    onResult(result.message)
                }
                .onFailure {
                    result->
                    onResult(result.message?:"Unknown Error")
                }
        }

    }

    fun signIn(context : Context ,name: String, password: String,onResult : (String)-> Unit) {
        val user = UserSignInReq(
            username = name,
            password = password
        )
        viewModelScope.launch {
            repo.signIn(user)
                .onSuccess {
                        result->
                    Log.d("loging",result.data)
                    val tokenManager = TokenManager(context)
                    tokenManager.saveToken(result.data)
                    onResult(result.message)
                }
                .onFailure {
                        result->
                    Log.d("loging",result.message.toString())
                    onResult(result.message?:"Unknown Error")
                }
        }

    }

}