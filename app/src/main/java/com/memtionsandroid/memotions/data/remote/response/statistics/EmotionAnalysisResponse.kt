package com.memtionsandroid.memotions.data.remote.response.statistics

import com.google.gson.annotations.SerializedName

data class EmotionAnalysisResponse(

	@field:SerializedName("data")
	val data: List<EmotionAnalysisDataItem>?,

	@field:SerializedName("errors")
	val errors: Any?,

	@field:SerializedName("status")
	val status: String
)

data class EmotionAnalysisItem(

	@field:SerializedName("emotion")
	val emotion: String,

	@field:SerializedName("confidence")
	val confidence: Float
)

data class EmotionAnalysisDataItem(

	@field:SerializedName("emotionAnalysis")
	val emotionAnalysis: List<EmotionAnalysisItem>,

	@field:SerializedName("analyzedAt")
	val analyzedAt: String,

	@field:SerializedName("journalId")
	val journalId: Int,

	@field:SerializedName("journalDatetime")
	val journalDatetime: String
)
