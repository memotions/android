package com.memtionsandroid.memotions.data.repository

import com.google.gson.Gson
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import com.memtionsandroid.memotions.data.remote.api.ApiService
import com.memtionsandroid.memotions.data.remote.api.PostJournalRequest
import com.memtionsandroid.memotions.data.remote.api.PostTagRequest
import com.memtionsandroid.memotions.data.remote.api.UpdateJournalRequest
import com.memtionsandroid.memotions.data.remote.response.auth.CommonErrorResponse
import com.memtionsandroid.memotions.data.remote.response.journals.JournalResponse
import com.memtionsandroid.memotions.data.remote.response.journals.JournalTagsResponse
import com.memtionsandroid.memotions.data.remote.response.journals.JournalsResponse
import com.memtionsandroid.memotions.data.remote.response.journals.TagResponse
import com.memtionsandroid.memotions.data.remote.response.journals.TagsResponse
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

enum class JournalStatus(val status: String) {
    DRAFT("DRAFT"),
    ANALYZED("ANALYZED"),
    PUBLISHED("PUBLISHED")
}

interface JournalsRepository {
    suspend fun getJournals(
        search: String? = null,
        tags: String? = null,
        emotions: String? = null,
        date: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        limit: Int? = null
    ): Flow<DataResult<JournalsResponse>>

    suspend fun postJournal(
        title: String,
        content: String,
        datetime: String?,
        starred: Boolean,
        status: String,
        tags: List<String>?
    ): Flow<DataResult<JournalResponse>>

    suspend fun getJournalById(journalId: Int): Flow<DataResult<JournalResponse>>

    suspend fun updateJournalById(
        journalId: Int,
        title: String,
        content: String,
        datetime: String?,
        starred: Boolean,
        status: String,
        tags: List<String>?
    ): Flow<DataResult<JournalResponse>>

    suspend fun deleteJournalById(journalId: Int): Flow<DataResult<Unit>>

    suspend fun toggleStarStatusJournal(journalId: Int): Flow<DataResult<Unit>>

    suspend fun getAllTagsByJournalId(journalId: Int): Flow<DataResult<JournalTagsResponse>>

    suspend fun addOrRemoveTagFromJournal(journalId: Int, tagName: String): Flow<DataResult<Unit>>

    suspend fun getCurrentUserTags(): Flow<DataResult<TagsResponse>>

    suspend fun postTag(name: String): Flow<DataResult<TagResponse>>

    suspend fun getTagById(tagId: Int): Flow<DataResult<TagResponse>>

    suspend fun deleteTagById(tagId: Int): Flow<DataResult<TagResponse>>
}

class DefaultJournalsRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) : JournalsRepository {

    override suspend fun getJournals(
        search: String?,
        tags: String?,
        emotions: String?,
        date: String?,
        startDate: String?,
        endDate: String?,
        limit: Int?
    ): Flow<DataResult<JournalsResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val response = apiService.getJournals(
                token = "Bearer $token",
                search,
                tags,
                emotions,
                date,
                startDate,
                endDate,
                limit
            )
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat mendapatkan data journal, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat mendapatkan data journal, coba lagi atau cek koneksi internet")))
        }
    }

    override suspend fun postJournal(
        title: String,
        content: String,
        datetime: String?,
        starred: Boolean,
        status: String,
        tags: List<String>?
    ): Flow<DataResult<JournalResponse>> = flow {
        if (title.isEmpty() || content.isEmpty()) {
            emit(DataResult.Error(Event("Judul dan konten jurnal harus diisi")))
            return@flow
        }

        if (content.length > 500) {
            emit(DataResult.Error(Event("Konten jurnal maksimal 500 karakter")))
            return@flow
        }

        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val request = PostJournalRequest(title, content, datetime, starred, status, tags)
            val response = apiService.postJournal(token = "Bearer $token", request)
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat menambahkan journal, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat menambahkan journal, coba lagi atau cek koneksi internet")))
        }
    }

    override suspend fun getJournalById(journalId: Int): Flow<DataResult<JournalResponse>> =
        flow {
            emit(DataResult.Loading)
            try {
                val token = userPreference.authTokenPreference.first().toString()
                val response = apiService.getJournalById(token = "Bearer $token", journalId)
                emit(DataResult.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
                val errorMessage =
                    "Terjadi kesalahan saat mendapatkan journal, [${e.code()}]: ${errorBody.errors[0].message}"
                emit(DataResult.Error(Event(errorMessage)))
            } catch (e: Exception) {
                emit(DataResult.Error(Event("Terjadi kesalahan saat mendapatkan journal, coba lagi atau cek koneksi internet")))
            }
        }

    override suspend fun updateJournalById(
        journalId: Int,
        title: String,
        content: String,
        datetime: String?,
        starred: Boolean,
        status: String,
        tags: List<String>?
    ): Flow<DataResult<JournalResponse>> =
        flow {
            if (title.isEmpty() || content.isEmpty()) {
                emit(DataResult.Error(Event("Judul dan konten jurnal harus diisi")))
                return@flow
            }

            if (content.length > 500) {
                emit(DataResult.Error(Event("Konten jurnal maksimal 500 karakter")))
                return@flow
            }

            emit(DataResult.Loading)
            try {
                val token = userPreference.authTokenPreference.first().toString()
                val request = UpdateJournalRequest(title, content, datetime, starred, status, tags)
                val response =
                    apiService.updateJournalById(token = "Bearer $token", journalId, request)
                emit(DataResult.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
                val errorMessage =
                    "Terjadi kesalahan saat update journal, [${e.code()}]: ${errorBody.errors[0].message}"
                emit(DataResult.Error(Event(errorMessage)))
            } catch (e: Exception) {
                emit(DataResult.Error(Event("Terjadi kesalahan saat update journal, coba lagi atau cek koneksi internet")))
            }
        }

    override suspend fun deleteJournalById(journalId: Int): Flow<DataResult<Unit>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            apiService.deleteJournalById(token = "Bearer $token", journalId)
            emit(DataResult.Success(Unit))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat menghapus jurnal, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Success(Unit))
        }
    }

    override suspend fun toggleStarStatusJournal(journalId: Int): Flow<DataResult<Unit>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val response = apiService.toggleStarStatusJournal(token = "Bearer $token", journalId)
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat starred journal, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat starred jurnal, coba lagi atau cek koneksi internet")))
        }
    }

    override suspend fun getAllTagsByJournalId(journalId: Int): Flow<DataResult<JournalTagsResponse>> =
        flow {
            emit(DataResult.Loading)
            try {
                val token = userPreference.authTokenPreference.first().toString()
                val response = apiService.getAllTagsByJournalId(token = "Bearer $token", journalId)
                emit(DataResult.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
                val errorMessage =
                    "Terjadi kesalahan saat mendapatkan tags jurnal, [${e.code()}]: ${errorBody.errors[0].message}"
                emit(DataResult.Error(Event(errorMessage)))
            } catch (e: Exception) {
                emit(DataResult.Error(Event("Terjadi kesalahan saat mendapatkan tags jurnal, coba lagi atau cek koneksi internet")))
            }
        }

    override suspend fun addOrRemoveTagFromJournal(
        journalId: Int,
        tagName: String
    ): Flow<DataResult<Unit>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            apiService.addOrRemoveTagFromJournal(token = "Bearer $token", journalId, tagName)
            emit(DataResult.Success(Unit))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat mengubah tags jurnal, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Success(Unit))
        }
    }

    override suspend fun getCurrentUserTags(): Flow<DataResult<TagsResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val response = apiService.getCurrentUserTags(token = "Bearer $token")
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat mendapatkan tags, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat mendapatkan tags, coba lagi atau cek koneksi internet")))
        }
    }

    override suspend fun postTag(name: String): Flow<DataResult<TagResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val request = PostTagRequest(name)
            val response = apiService.postTag(token = "Bearer $token", request)
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat menambahkan tag, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat menambahkan tag, coba lagi atau cek koneksi internet")))
        }
    }

    override suspend fun getTagById(tagId: Int): Flow<DataResult<TagResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val response = apiService.getTagById(token = "Bearer $token", tagId)
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat mendapatkan tag, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat mendapatkan tag, coba lagi atau cek koneksi internet")))
        }
    }

    override suspend fun deleteTagById(tagId: Int): Flow<DataResult<TagResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val token = userPreference.authTokenPreference.first().toString()
            val response = apiService.deleteTagById(token = "Bearer $token", tagId)
            emit(DataResult.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CommonErrorResponse::class.java)
            val errorMessage =
                "Terjadi kesalahan saat menghapus tag, [${e.code()}]: ${errorBody.errors[0].message}"
            emit(DataResult.Error(Event(errorMessage)))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Terjadi kesalahan saat menghapus tag, coba lagi atau cek koneksi internet")))
        }
    }
}