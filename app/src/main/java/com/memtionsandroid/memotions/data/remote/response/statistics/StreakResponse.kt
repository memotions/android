package com.memtionsandroid.memotions.data.remote.response.statistics

import com.google.gson.annotations.SerializedName

data class StreakResponse(

	@field:SerializedName("endDate")
	val endDate: String,

	@field:SerializedName("streakLength")
	val streakLength: Int,

	@field:SerializedName("startDate")
	val startDate: String
)
