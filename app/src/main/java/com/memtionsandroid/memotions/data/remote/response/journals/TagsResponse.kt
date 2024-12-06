package com.memtionsandroid.memotions.data.remote.response.journals

import com.google.gson.annotations.SerializedName

data class TagsResponse(

	@field:SerializedName("data")
	val data: List<TagsItem>,

	@field:SerializedName("errors")
	val errors: Any?,

	@field:SerializedName("status")
	val status: String
)

data class TagsItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
