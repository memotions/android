package com.memtionsandroid.memotions.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class CommonErrorResponse(

	@field:SerializedName("data")
	val data: Any?,

	@field:SerializedName("errors")
	val errors: List<CommonErrorsItem>,

	@field:SerializedName("status")
	val status: String
)

data class CommonErrorsItem(

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("message")
	val message: String
)
