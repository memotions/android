package com.memtionsandroid.memotions.data.remote.response.journals

import com.google.gson.annotations.SerializedName

data class TagResponse(

	@field:SerializedName("data")
	val data: TagData,

	@field:SerializedName("errors")
	val errors: Any?,

	@field:SerializedName("status")
	val status: String
)

data class TagData(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,
)
