package com.memtionsandroid.memotions.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emotion")
data class Emotion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @field:ColumnInfo(name = "description")
    val description: String,
)