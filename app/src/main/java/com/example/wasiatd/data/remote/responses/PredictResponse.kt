package com.example.wasiatd.data.remote.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PredictResponse(

	@field:SerializedName("predicted_class")
	val predictedClass: String? = null
) : Parcelable
