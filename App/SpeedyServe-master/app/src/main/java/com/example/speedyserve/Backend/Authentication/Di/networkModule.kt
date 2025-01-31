package com.example.speedyserve.Backend.Authentication.Di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.speedyserve.Backend.Authentication.Api.AuthApi
import com.example.speedyserve.Backend.Authentication.Repo.AuthRepoImp
import com.example.speedyserve.Backend.Authentication.Repo.CanteenRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object networkModule {

    @Provides
    @Singleton
    fun provideAuthApi() : AuthApi{
        return Retrofit.Builder()
            .baseUrl("http://192.168.67.201:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideSharedPreffs(app : Application) : SharedPreferences{
        val mode = Context.MODE_PRIVATE
        return app.getSharedPreferences("prefs",mode)
    }

    @Provides
    @Singleton
    fun provideRepo(authApi: AuthApi,preferences: SharedPreferences) : AuthRepoImp{
        return AuthRepoImp(authApi,preferences)
    }

    @Provides
    @Singleton
    fun provideCanteenRpo(authApi: AuthApi) : CanteenRepoImpl{
        return CanteenRepoImpl(authApi)
    }

}