package com.memtionsandroid.memotions.data.remote.api

import com.memtionsandroid.memotions.data.local.entity.JournalStatus
import com.memtionsandroid.memotions.data.remote.response.AchievementsResponse
import com.memtionsandroid.memotions.data.remote.response.AchievementsResponseItem
import com.memtionsandroid.memotions.data.remote.response.AuthResponse
import com.memtionsandroid.memotions.data.remote.response.EmotionsResponse
import com.memtionsandroid.memotions.data.remote.response.JournalDataItem
import com.memtionsandroid.memotions.data.remote.response.JournalResponse
import com.memtionsandroid.memotions.data.remote.response.JournalsResponse
import com.memtionsandroid.memotions.data.remote.response.StatisticResponse
import com.memtionsandroid.memotions.data.remote.response.StreakResponse
import com.memtionsandroid.memotions.data.remote.response.TagResponse
import com.memtionsandroid.memotions.data.remote.response.TagsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): AuthResponse

    @POST("auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): AuthResponse

    @GET("auth/profile")
    suspend fun getProfileUser(
        @Header("Authorization") token: String
    ): AuthResponse

    // Not Fixed V========================================V

    @GET("journals")
    suspend fun getJournals(
        @Header("Authorization") token: String,
        @Query("search") search: String? = null,
        @Query("tags") tags: String? = null,
        @Query("emotions") sort: String? = null,
        @Query("date") date: String? = null,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("limit") limit: Int? = null
    ): JournalsResponse

    @POST("journals")
    suspend fun postJournal(
        @Header("Authorization") token: String,
        @Body request: PostJournalRequest
    ): JournalResponse

    @GET("journals/{journalId}")
    suspend fun getJournalById(
        @Header("Authorization") token: String,
        @Path("journalId") journalId: Int
    ): JournalDataItem

    @PATCH("journals/{journalId}")
    suspend fun updateJournalById(
        @Header("Authorization") token: String,
        @Path("journalId") journalId: Int,
        @Body request: UpdateJournalRequest
    ): JournalDataItem

    @PATCH("journals/{journalId}/star")
    suspend fun toggleJournalStar(
        @Header("Authorization") token: String,
        @Path("journalId") journalId: Int
    )

    @GET("journals/{journalId}/tags")
    suspend fun getAllTagsByJournalId(
        @Header("Authorization") token: String,
        @Path("journalId") journalId: Int
    ): TagsResponse

    @PATCH("journals/{journalId}/tags/{tagId}")
    suspend fun addOrRemoveTagFromJournal(
        @Header("Authorization") token: String,
        @Path("journalId") journalId: Int,
        @Path("tagId") tagId: Int
    )

    @GET("tags")
    suspend fun getAllUserTags(
        @Header("Authorization") token: String
    ): TagsResponse

    @POST("tags")
    suspend fun postTag(
        @Header("Authorization") token: String,
        @Body request: PostTagRequest
    ): TagResponse

    @GET("tags/{tagId}")
    suspend fun getTagById(
        @Header("Authorization") token: String,
        @Path("tagId") tagId: Int
    ): TagResponse

    @DELETE("tags/{tagId}")
    suspend fun deleteTagById(
        @Header("Authorization") token: String,
        @Path("tagId") tagId: Int
    )

    @GET("achievements")
    suspend fun getUserAchievements(
        @Header("Authorization") token: String,
    ): AchievementsResponse

    @GET("achievements/{achievementId}")
    suspend fun getAchievementById(
        @Header("Authorization") token: String,
        @Path("achievementId") achievementId: Int
    ): AchievementsResponseItem

    @GET("streak")
    suspend fun getStreak(
        @Header("Authorization") token: String,
    ): StreakResponse

    @GET("emotions")
    suspend fun getUserEmotions(
        @Header("Authorization") token: String,
    ): EmotionsResponse

    @GET("stats")
    suspend fun getStatistic(
        @Header("Authorization") token: String,
    ): StatisticResponse
}

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class PostJournalRequest(
    val title: String,
    val content: String,
    val datetime: String,
    val starred: Boolean,
    val status: JournalStatus,
    val tagIds: List<String?>,
)

data class UpdateJournalRequest(
    val title: String?,
    val content: String?,
    val datetime: String?,
    val starred: Boolean?,
    val status: JournalStatus?,
    val tagIds: List<String?>?,
)

data class PostTagRequest(
    val name: String
)