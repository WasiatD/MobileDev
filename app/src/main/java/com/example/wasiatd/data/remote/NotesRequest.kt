package com.example.wasiatd.data.remote

import com.google.gson.annotations.SerializedName

data class NotesRequest(
    @SerializedName("title") val title: String,
    @SerializedName("id") val id: String,
    @SerializedName("content") val content: String
)
