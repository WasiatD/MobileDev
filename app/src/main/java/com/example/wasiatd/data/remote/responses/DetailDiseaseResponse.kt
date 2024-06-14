package com.example.wasiatd.data.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailDiseaseResponse(

	@field:SerializedName("disease_info")
	val diseaseInfo: String
) : Parcelable
