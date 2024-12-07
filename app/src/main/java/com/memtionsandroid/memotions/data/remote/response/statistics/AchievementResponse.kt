package com.memtionsandroid.memotions.data.remote.response.statistics

import com.google.gson.annotations.SerializedName

data class AchievementResponse(

	@field:SerializedName("data")
	val data: AchievementData,

	@field:SerializedName("errors")
	val errors: Any?,

	@field:SerializedName("status")
	val status: String
)

data class AchievementData(

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("tier")
	val tier: Int,

	@field:SerializedName("criteria")
	val criteria: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("pointsAwarded")
	val pointsAwarded: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("iconUrl")
	val iconUrl: String,

	@field:SerializedName("completed")
	val completed: Boolean,

	@field:SerializedName("type")
	val type: String
)
