package com.example.wasiatd.data.remote.Responses

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

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("Lokasi")
	val lokasi: String? = null,

	@field:SerializedName("Tanama")
	val tanama: String? = null
) : Parcelable
