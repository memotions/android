package com.memtionsandroid.memotions.data.repository

import com.google.gson.Gson
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import com.memtionsandroid.memotions.data.remote.api.ApiService
import com.memtionsandroid.memotions.data.remote.api.LoginRequest
import com.memtionsandroid.memotions.data.remote.api.RegisterRequest
import com.memtionsandroid.memotions.data.remote.response.AuthResponse
import com.memtionsandroid.memotions.data.remote.response.CommonErrorResponse
import com.memtionsandroid.memotions.data.remote.response.ValidationErrorResponse
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

interface AuthRepository {
    suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Flow<DataResult<AuthResponse>>

    suspend fun loginUser(email: String, password: String): Flow<DataResult<AuthResponse>>

    suspend fun getProfileUser(): Flow<DataResult<AuthResponse>>
}

class DefaultAuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) :
    AuthRepository {
    override suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Flow<DataResult<AuthResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val request = RegisterRequest(name, email, password)
            val response = apiService.registerUser(request)
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ValidationErrorResponse::class.java)
            val errorMessage = "${errorBody.errors[0].path[0]}: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat register user, coba lagi atau cek koneksi internet")))
        }
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Flow<DataResult<AuthResponse>> =
        flow {
            emit(DataResult.Loading)
            try {
                val request = LoginRequest(email, password)
                val response = apiService.loginUser(request)
                emit(DataResult.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ValidationErrorResponse::class.java)
                val errorMessage = "${errorBody.errors[0].path[0]}: ${errorBody.errors[0].message}"
                emit(DataResult.Error(Event(errorMessage)))
            } catch (e: Exception) {
                emit(DataResult.Error(Event("Terjadi kesalahan saat login user, coba lagi atau cek koneksi internet")))
            }
        }

    override suspend fun getProfileUser(): Flow<DataResult<AuthResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val response = apiService.getProfileUser("Bearer $token")
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage = errorBody.errors[0].message
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat mendapatkan profile user, coba lagi atau cek koneksi internet")))
        }
    }

}