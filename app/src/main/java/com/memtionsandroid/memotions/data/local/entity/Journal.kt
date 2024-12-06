package com.memtionsandroid.memotions.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.memtionsandroid.memotions.data.local.room.ListConverter

@Entity(tableName = "journal")
data class Journal(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val userId: Int,
    val title: String,
    val content: String,
    val datetime: String,
    val createdAt: String,
    val status: String,
    val deleted: Boolean,
    val starred: Boolean,
    val feedback: String? = null,

    @TypeConverters(ListConverter::class)
    val tags: List<String>? = null,

    @TypeConverters(ListConverter::class)
    val emotionAnalysis: List<EmotionAnalysis>? = null
)

data class EmotionAnalysis(
    val emotion: String,
    val confidence: Float
)