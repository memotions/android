package com.memtionsandroid.memotions.data.remote.response.statistics

import com.google.gson.annotations.SerializedName

data class EmotionsResponse(

	@field:SerializedName("EmotionsResponse")
	val emotionsResponse: List<EmotionsResponseItem>
)

data class EmotionsResponseItem(

	@field:SerializedName("analyzedAt")
	val analyzedAt: String,

	@field:SerializedName("confidence")
	val confidence: Double,

	@field:SerializedName("class")
	val jsonMemberClass: String
)
