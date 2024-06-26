package com.example.wasiatd.data.remote.config

import com.example.wasiatd.data.remote.NotesRequest
import com.example.wasiatd.data.remote.PredictRequest
import com.example.wasiatd.data.remote.constants
import com.example.wasiatd.data.remote.responses.DetailDiseaseResponse
import com.example.wasiatd.data.remote.responses.DiseaseInfoResponse
import com.example.wasiatd.data.remote.responses.GetDetailIotResponse
import com.example.wasiatd.data.remote.responses.GetIotResponse
import com.example.wasiatd.data.remote.responses.GetNotesResponse
import com.example.wasiatd.data.remote.responses.IsiItem
import com.example.wasiatd.data.remote.responses.LoginResponse
import com.example.wasiatd.data.remote.responses.PredictResponse
import com.example.wasiatd.data.remote.responses.RegisterResponse
import com.example.wasiatd.data.remote.responses.UpdatePlantResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET(constants.GET_IOT)
    suspend fun getIot(): GetIotResponse

//    @GET(constants.GET_DETAIL_IOT)
//    suspend fun getIotDetail(): GetDetailIotResponse

//    @GET(constants.GET_DISEASE_INFO)
//    suspend fun getDiseaseInfo(@Query("disease") disease: String): DiseaseInfoResponse
//
//    @GET(constants.GET_DISEASE_INFO)
//    suspend fun getDiseaseInfo(@Body requestBody: PredictRequest): DiseaseInfoResponse

    @GET("/disease-info/{disease}")
    suspend fun getDiseaseInfo(@Path("disease") disease: String): DetailDiseaseResponse

    @POST(constants.DISEASE_PREDICTION)
    suspend fun predictDisease(@Body requestBody: PredictRequest): PredictResponse

//    @GET(constants.GET_DATA_BY_ID)
//    suspend fun getDataById(@Body requestBody: PredictRequest): IsiItem

    @GET("getDataById/{IoT_ID}")
    fun getDetailIotResponse(@Path("IoT_ID") id: String): Call<GetDetailIotResponse>

    @POST("/addNotes")
    fun addNotes(@Body requestBody: NotesRequest): Call<GetNotesResponse>

    @PATCH("updateDataIOT")
    fun updatePlantData(@Body requestBody: RequestBody): Call<UpdatePlantResponse>

//    @FormUrlEncoded
//    @POST("/predict")
//    fun predictDisease(
//        @Field("base64_encoded") base64_encoded: String,
//    ): Call<PredictResponse>

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

    @GET(constants.GET_NOTES)
    suspend fun getNotes(): GetNotesResponse
}