package com.example.wasiatd.data.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetDetailIotResponse(
	@field:SerializedName("ph")
	val ph: String,
	@field:SerializedName("suhu")
	val suhu: String,
	@field:SerializedName("kelembapan")
	val kelembapan: String
) : Parcelable
