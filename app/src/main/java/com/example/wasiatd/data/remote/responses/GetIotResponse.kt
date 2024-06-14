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

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("lokasi")
	val lokasi: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("id")
	val id: String

) : Parcelable
