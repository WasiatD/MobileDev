package com.example.wasiatd.data.remote.config

import com.example.wasiatd.data.remote.constants
import com.example.wasiatd.data.remote.responses.DiseaseInfoResponse
import com.example.wasiatd.data.remote.responses.GetIotResponse
import com.example.wasiatd.data.remote.responses.IsiItem
import com.example.wasiatd.data.remote.responses.LoginResponse
import com.example.wasiatd.data.remote.responses.PredictResponse
import com.example.wasiatd.data.remote.responses.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {

    @GET(constants.GET_IOT)
    suspend fun getIot(): GetIotResponse

    @GET(constants.GET_DISEASE_INFO)
    suspend fun getDiseaseInfo(@Query("disease") disease: String): DiseaseInfoResponse

    @POST(constants.DISEASE_PREDICTION)
    suspend fun predictDisease(@Query("base64_encoded")base64_encoded: String): PredictResponse

    @GET(constants.GET_DATA_BY_ID)
    suspend fun getDataById(@Query("id") id: String): IsiItem

    @FormUrlEncoded
    @POST("/register")
    fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

}