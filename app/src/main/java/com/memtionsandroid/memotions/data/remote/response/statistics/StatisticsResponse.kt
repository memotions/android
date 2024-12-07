package com.memtionsandroid.memotions.data.remote.response.statistics

import com.google.gson.annotations.SerializedName

data class StatisticsResponse(

	@field:SerializedName("data")
	val data: StatisticsData,

	@field:SerializedName("errors")
	val errors: Any?,

	@field:SerializedName("status")
	val status: String
)

data class CurrentStreak(

	@field:SerializedName("endDate")
	val endDate: String,

	@field:SerializedName("streakLength")
	val streakLength: Int,

	@field:SerializedName("startDate")
	val startDate: String
)

data class StatisticsData(

	@field:SerializedName("currentLevel")
	val currentLevel: CurrentLevel,

	@field:SerializedName("currentStreak")
	val currentStreak: CurrentStreak,

	@field:SerializedName("achievementsCount")
	val achievementsCount: AchievementsCount,

	@field:SerializedName("emotions")
	val emotions: Emotions,

	@field:SerializedName("journalsCount")
	val journalsCount: Int
)

data class CurrentLevel(

	@field:SerializedName("currentLevel")
	val currentLevel: Int,

	@field:SerializedName("totalPoints")
	val totalPoints: Int,

	@field:SerializedName("pointsRequired")
	val pointsRequired: Int,

	@field:SerializedName("nextLevel")
	val nextLevel: Int
)

data class Emotions(

	@field:SerializedName("happy")
	val happy: Int,

	@field:SerializedName("sad")
	val sad: Int,

	@field:SerializedName("neutral")
	val neutral: Int,

	@field:SerializedName("anger")
	val anger: Int,

	@field:SerializedName("scared")
	val scared: Int
)

data class AchievementsCount(

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("completed")
	val completed: Int
)
