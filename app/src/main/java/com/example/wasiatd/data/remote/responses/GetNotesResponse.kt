package com.example.wasiatd.data.remote.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetNotesResponse(

	@field:SerializedName("notes")
	val notes: List<NotesItem>
) : Parcelable

@Parcelize
data class NotesItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("content")
	val content: String
) : Parcelable
