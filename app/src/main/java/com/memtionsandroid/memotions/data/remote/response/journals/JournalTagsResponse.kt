package com.memtionsandroid.memotions.data.remote.response.journals

import com.google.gson.annotations.SerializedName

data class JournalTagsResponse(

	@field:SerializedName("data")
	val data: List<String>?,

	@field:SerializedName("errors")
	val errors: Any?,

	@field:SerializedName("status")
	val status: String
)
