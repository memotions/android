package com.memtionsandroid.memotions.data.repository

import com.google.gson.Gson
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import com.memtionsandroid.memotions.data.remote.api.ApiService
import com.memtionsandroid.memotions.data.remote.response.statistics.AchievementsResponse
import com.memtionsandroid.memotions.data.remote.response.auth.CommonErrorResponse
import com.memtionsandroid.memotions.data.remote.response.statistics.AchievementResponse
import com.memtionsandroid.memotions.data.remote.response.statistics.EmotionsResponse
import com.memtionsandroid.memotions.data.remote.response.statistics.StatisticsResponse
import com.memtionsandroid.memotions.data.remote.response.statistics.StreakResponse
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

interface StatisticsRepository {
    suspend fun getUserAchievements(): Flow<DataResult<AchievementsResponse>>

    suspend fun getAchievementById(achievementId: Int): Flow<DataResult<AchievementResponse>>

    suspend fun getStreak(): Flow<DataResult<StreakResponse>>

    suspend fun getUserEmotions(): Flow<DataResult<EmotionsResponse>>

    suspend fun getStatistics(): Flow<DataResult<StatisticsResponse>>
}

class DefaultStatisticsRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) : StatisticsRepository {
    override suspend fun getUserAchievements(): Flow<DataResult<AchievementsResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val response = apiService.getUserAchievements(token = "Bearer $token")
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat mendapatkan data achievements, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat mendapatkan data achievements, coba lagi atau cek koneksi internet")))
        }
    }

    override suspend fun getAchievementById(achievementId: Int): Flow<DataResult<AchievementResponse>> =
        flow {
            emit(DataResult.Loading)
            try {
                val token = userPreference.authTokenPreference.first().toString()
                val response = apiService.getAchievementById(token = "Bearer $token", achievementId)
                emit(DataResult.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
                val errorMessage =
                    "Terjadi kesalahan saat mendapatkan achievement, [${e.code()}]: ${errorBody.errors[0].message}"
                emit(DataResult.Error(Event(errorMessage)))
            } catch (e: Exception) {
                emit(DataResult.Error(Event("Terjadi kesalahan saat mendapatkan achievement, coba lagi atau cek koneksi internet")))
            }
        }

    override suspend fun getStreak(): Flow<DataResult<StreakResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val response = apiService.getStreak(token = "Bearer $token")
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat mendapatkan streak, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat mendapatkan streak, coba lagi atau cek koneksi internet")))
        }
    }

    // TODO(Don't use GetUserEmotions -> Not finished)
    override suspend fun getUserEmotions(): Flow<DataResult<EmotionsResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val response = apiService.getUserEmotions(token = "Bearer $token")
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat mendapatkan emotions, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat mendapatkan emotions, coba lagi atau cek koneksi internet")))
        }
    }

    override suspend fun getStatistics(): Flow<DataResult<StatisticsResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val response = apiService.getStatistics(token = "Bearer $token")
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat mendapatkan statistic, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat mendapatkan statistic, coba lagi atau cek koneksi internet")))
        }
    }
}