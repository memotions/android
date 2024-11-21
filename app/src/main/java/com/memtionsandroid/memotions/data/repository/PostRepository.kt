package com.memtionsandroid.memotions.data.repository

import com.memtionsandroid.memotions.data.remote.api.ApiService
import com.memtionsandroid.memotions.data.remote.response.PostResponse
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface PostRepository {
    fun getPost(id: String): Flow<DataResult<PostResponse>>
}

class DefaultPostRepository @Inject constructor(private val apiService: ApiService) :
    PostRepository {

    override fun getPost(id: String): Flow<DataResult<PostResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val response = apiService.getPost(id)
            emit(DataResult.Success(response))
        } catch (e: Exception) {
            emit(DataResult.Error(Event(e.message ?: "Failed to get post")))
        }
    }
}