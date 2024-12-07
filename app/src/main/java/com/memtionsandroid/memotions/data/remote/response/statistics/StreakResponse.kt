package com.memtionsandroid.memotions.data.remote.response.statistics

import com.google.gson.annotations.SerializedName

data class StreakResponse(

	@field:SerializedName("data")
	val data: StreakData,

	@field:SerializedName("errors")
	val errors: Any?,

	@field:SerializedName("status")
	val status: String
)

data class StreakData(

	@field:SerializedName("endDate")
	val endDate: String,

	@field:SerializedName("streakLength")
	val streakLength: Int,

	@field:SerializedName("startDate")
	val startDate: String
)
