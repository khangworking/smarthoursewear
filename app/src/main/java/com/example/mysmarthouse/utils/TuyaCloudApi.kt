package com.example.mysmarthouse.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TuyaCloudApi {
    fun getInstace(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}