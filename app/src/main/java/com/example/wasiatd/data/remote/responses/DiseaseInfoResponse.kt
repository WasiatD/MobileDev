package com.example.wasiatd.data.remote.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DiseaseInfoResponse(

	@field:SerializedName("disease_info")
	val diseaseInfo: String? = null
) : Parcelable
