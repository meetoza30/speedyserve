package com.example.speedyserve.Repo

import android.util.Log
import com.example.speedyserve.API.Apis
import com.example.speedyserve.Models.ApiResponse.ApiResponse
import com.example.speedyserve.Models.ApiResponse.CheckAuthResponse
import com.example.speedyserve.Models.ApiResponse.TokenReq
import com.example.speedyserve.Models.AuthModels.UserSignInReq
import com.example.speedyserve.Models.AuthModels.UserSignUpReq
import com.example.speedyserve.Models.Canteens.Canteen
import com.example.speedyserve.Models.Canteens.CanteenResponse
import com.example.speedyserve.Models.Dishes.DishesReq
import com.example.speedyserve.Models.Dishes.DishesResponse
import com.example.speedyserve.Screen.MenuScreen.dishWithQuantity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import javax.inject.Inject

data class CartOrder(
    val canteenId: String ,
    val dishWithQuantity: MutableStateFlow<List<dishWithQuantity>>
)

class Repo @Inject constructor(private val apis: Apis)  {
   val _cartOrderGlobal : MutableStateFlow<CartOrder?> = MutableStateFlow(null)
    val cartOrderGlobal : StateFlow<CartOrder?> = _cartOrderGlobal

    suspend fun signUp(user: UserSignUpReq): Result<ApiResponse> {
        return try {
            val response = apis.signUp(user)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val jsonObject = JSONObject(errorBody)
                    jsonObject.getString("message")
                } catch (e: Exception) {
                    "Unknown error"
                }
                Result.failure(Exception("Sign-up failed: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signIn(user: UserSignInReq): Result<ApiResponse> {
        return try {
            val response = apis.signIn(user)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val jsonObject = JSONObject(errorBody)
                    jsonObject.getString("message")
                } catch (e: Exception) {
                    "Unknown error"}
                Result.failure(Exception("Login failed: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCanteenList() : Result<CanteenResponse>{
        return try {
            val response = apis.getCanteens()
            if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                }?: Result.failure(Exception("Empty Response Body"))
            }else{
                val errorBody = response.errorBody()?.toString()
                val errorMessage = try{
                    val jsonObject = JSONObject(errorBody)
                    jsonObject.getString("message")
                }catch (e : Exception){
                    "Unknown Error"
                }
                Result.failure(Exception("Fetching Canteens failed : $errorMessage"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }

    }

    suspend fun getCanteenDishes(dishesReq: DishesReq) : Result<DishesResponse>{
        return try {
            val response = apis.getCanteenDishes(dishesReq)
            if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                }?: Result.failure(Exception("Empty Response Body"))
            }else{
                val errorBody = response.errorBody()?.toString()
                val errorMessage = try{
                    val jsonObject = JSONObject(errorBody)
                    jsonObject.getString("message")
                }catch (e : Exception){
                    "Unknown Error }"
                }
                Result.failure(Exception("Fetching Dishes failed : $errorMessage"))
            }
        }catch (e : Exception){
            Result.failure(e)
        }
    }

    suspend fun checkAuthentication(token: String) : Result<CheckAuthResponse>{
        return try {
            val tokenReq = TokenReq(token)
            val response = apis.checkAuth(tokenReq)
            if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                }?: Result.failure(Exception("Empty Response Body"))
            }else{
                Result.failure(Exception("error in authentication"))
            }
        }catch (e : Exception){
            Result.failure(e)
        }
    }

    fun saveCartOrder(cartOrder : MutableStateFlow<List<dishWithQuantity>>,canteenId: String){
        _cartOrderGlobal.value = CartOrder(
            canteenId = canteenId,
            dishWithQuantity = cartOrder
        )
        Log.d("repo after sending cartorder",cartOrderGlobal.value!!.dishWithQuantity.value.toString())
    }
}