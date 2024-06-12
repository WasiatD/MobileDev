package com.example.wasiatd.data.remote.responses

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("expiresIn")
	val expiresIn: String,

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("idToken")
	val idToken: String,

	@field:SerializedName("localId")
	val localId: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)
