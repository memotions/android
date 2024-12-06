package com.memtionsandroid.memotions.data.remote.response.statistics

import com.google.gson.annotations.SerializedName

data class StatisticResponse(

	@field:SerializedName("currentLevel")
	val currentLevel: Int,

	@field:SerializedName("currentStreak")
	val currentStreak: CurrentStreak,

	@field:SerializedName("achievements")
	val achievements: Achievements,

	@field:SerializedName("emotions")
	val emotions: Emotions,

	@field:SerializedName("totalJournals")
	val totalJournals: Int,

	@field:SerializedName("totalPoints")
	val totalPoints: Int,

	@field:SerializedName("longestStreak")
	val longestStreak: LongestStreak
)

data class LongestStreak(

	@field:SerializedName("endDate")
	val endDate: String,

	@field:SerializedName("streakLength")
	val streakLength: Int,

	@field:SerializedName("startDate")
	val startDate: String
)

data class Achievements(

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("completed")
	val completed: Int
)

data class Emotions(

	@field:SerializedName("happy")
	val happy: Int,

	@field:SerializedName("sad")
	val sad: Int,

	@field:SerializedName("neutral")
	val neutral: Int,

	@field:SerializedName("angry")
	val angry: Int,

	@field:SerializedName("scared")
	val scared: Int
)

data class CurrentStreak(

	@field:SerializedName("endDate")
	val endDate: String,

	@field:SerializedName("streakLength")
	val streakLength: Int,

	@field:SerializedName("startDate")
	val startDate: String
)
