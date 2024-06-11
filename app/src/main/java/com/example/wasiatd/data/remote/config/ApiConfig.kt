package com.example.wasiatd.data.remote.config

import com.example.wasiatd.data.remote.constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiConfig {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }
}