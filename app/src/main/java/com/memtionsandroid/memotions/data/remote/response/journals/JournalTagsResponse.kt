package com.memtionsandroid.memotions.data.remote.response.journals

import com.google.gson.annotations.SerializedName

data class JournalTagsResponse(

    @field:SerializedName("data")
    val data: List<JournalTagsItem>?,

    @field:SerializedName("errors")
    val errors: Any?,

    @field:SerializedName("status")
    val status: String
)

data class JournalTagsItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)
