package com.example.speedyserve.Screen.MainScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedyserve.Models.ApiResponse.UserInfo
import com.example.speedyserve.Models.Canteens.Canteen
import com.example.speedyserve.Repo.Repo
import com.example.speedyserve.utils.SaveUserInfo
import com.example.speedyserve.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(private val repo: Repo): ViewModel() {

    private val _canteenList : MutableStateFlow<List<Canteen>> = MutableStateFlow(emptyList())
    val canteenList : StateFlow<List<Canteen>> = _canteenList

    private val _user : MutableStateFlow<UserInfo?> = MutableStateFlow(null)
    val user : StateFlow<UserInfo?> = _user
    val isLoading : MutableStateFlow<Boolean> = MutableStateFlow(false)



    fun fetchCanteens(onError : (String)-> Unit){
        isLoading.value = true
        viewModelScope.launch {
            repo.getCanteenList()
                .onSuccess {
                    _canteenList.value = it.data
                    isLoading.value=false
                    Log.d("Canteens", it.toString())
                }
                .onFailure {
                    Log.d("Canteens",it.message.toString())
                    isLoading.value=false
                    onError(it.message.toString())
                }
        }
    }

    fun getUserInfo(context : Context){
        val tokenManager = TokenManager(context = context)
        val saveUserInfoManager = SaveUserInfo(context)
        val token = tokenManager.getToken()
        if(token!=null){
            viewModelScope.launch {
                repo.getUserInfo(token)
                    .onSuccess {
                        if(it.success){
                            saveUserInfoManager.saveUserInfo(it.user)
                            _user.value = it.user
                        }else{
                            _user.value = UserInfo(
                                "Guest",
                                "",
                                ""
                            )
                        }
                    }
            }
        }

    }

}