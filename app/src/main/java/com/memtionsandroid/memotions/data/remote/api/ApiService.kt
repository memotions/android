package com.memtionsandroid.memotions.data.remote.api

import com.memtionsandroid.memotions.data.remote.response.statistics.AchievementsResponse
import com.memtionsandroid.memotions.data.remote.response.auth.AuthResponse
import com.memtionsandroid.memotions.data.remote.response.statistics.EmotionsResponse
import com.memtionsandroid.memotions.data.remote.response.journals.JournalResponse
import com.memtionsandroid.memotions.data.remote.response.journals.JournalTagsResponse
import com.memtionsandroid.memotions.data.remote.response.journals.JournalsResponse
import com.memtionsandroid.memotions.data.remote.response.statistics.StreakResponse
import com.memtionsandroid.memotions.data.remote.response.journals.TagResponse
import com.memtionsandroid.memotions.data.remote.response.journals.TagsResponse
import com.memtionsandroid.memotions.data.remote.response.statistics.AchievementResponse
import com.memtionsandroid.memotions.data.remote.response.statistics.StatisticsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // *** Auth Endpoint ***

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

    // *** Journals Endpoint ***

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
    ): JournalResponse

    @PATCH("journals/{journalId}")
    suspend fun updateJournalById(
        @Header("Authorization") token: String,
        @Path("journalId") journalId: Int,
        @Body request: UpdateJournalRequest
    ): JournalResponse

    @DELETE("journals/{journalId}")
    suspend fun deleteJournalById(
        @Header("Authorization") token: String,
        @Path("journalId") journalId: Int
    )

    @PATCH("journals/{journalId}/star")
    suspend fun toggleStarStatusJournal(
        @Header("Authorization") token: String,
        @Path("journalId") journalId: Int
    )

    @GET("journals/{journalId}/tags")
    suspend fun getAllTagsByJournalId(
        @Header("Authorization") token: String,
        @Path("journalId") journalId: Int
    ): JournalTagsResponse

    @PATCH("journals/{journalId}/tags/{tagId}")
    suspend fun addOrRemoveTagFromJournal(
        @Header("Authorization") token: String,
        @Path("journalId") journalId: Int,
        @Path("tagId") tagName: String
    )

    // *** Tags Endpoint ***

    @GET("tags")
    suspend fun getCurrentUserTags(
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
    ): TagResponse

    // *** Statistic Endpoint ***

    @GET("achievements")
    suspend fun getUserAchievements(
        @Header("Authorization") token: String,
    ): AchievementsResponse

    @GET("achievements/{achievementId}")
    suspend fun getAchievementById(
        @Header("Authorization") token: String,
        @Path("achievementId") achievementId: Int
    ): AchievementResponse

    @GET("streak")
    suspend fun getStreak(
        @Header("Authorization") token: String,
    ): StreakResponse

    @GET("emotions")
    suspend fun getUserEmotions(
        @Header("Authorization") token: String,
    ): EmotionsResponse

    @GET("stats")
    suspend fun getStatistics(
        @Header("Authorization") token: String,
    ): StatisticsResponse
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
    val datetime: String?,
    val starred: Boolean,
    val status: String,
    val tags: List<String>?,
)

data class UpdateJournalRequest(
    val title: String,
    val content: String,
    val datetime: String?,
    val starred: Boolean,
    val status: String,
    val tags: List<String>?,
)

data class PostTagRequest(
    val name: String
)