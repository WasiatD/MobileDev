package com.example.wasiatd.data.remote.config
import com.example.wasiatd.data.remote.responses.DiseaseInfoResponse
import com.example.wasiatd.data.remote.responses.GetIotResponse
import com.example.wasiatd.data.remote.responses.IsiItem
import com.example.wasiatd.data.remote.responses.PredictResponse
import com.example.wasiatd.data.remote.constants
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


}
