package com.memtionsandroid.memotions.data.remote.api

import com.memtionsandroid.memotions.data.remote.response.PostResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts/{id}")
    suspend fun getPost(
        @Path("id") id: String
    ): PostResponse
}