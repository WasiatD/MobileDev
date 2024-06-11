package com.example.wasiatd.data.remote.config
import com.example.wasiatd.data.local.Plant
import com.example.wasiatd.data.remote.constants
import retrofit2.http.GET

interface ApiServices {

    @GET(constants.GET_PLANT)
    suspend fun getPlant(): Plant
}
