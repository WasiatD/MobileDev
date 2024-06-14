package com.example.wasiatd.data.remote.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetIotResponse(

	@field:SerializedName("isi")
	val isi: List<IsiItem?>? = null
) : Parcelable

@Parcelize
data class IsiItem(
	@field:SerializedName("ph")
	val ph: String,

	@field:SerializedName("name")
	val nama: String,

	@field:SerializedName("suhu")
	val suhu: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("kelembapan")
	val kelembapan: String

) : Parcelable
