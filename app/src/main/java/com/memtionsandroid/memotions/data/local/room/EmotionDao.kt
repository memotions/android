package com.memtionsandroid.memotions.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.memtionsandroid.memotions.data.local.entity.Emotion
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionDao {
    @Query("SELECT * FROM emotion ORDER BY id DESC LIMIT 10")
    fun getEmotions(): Flow<List<Emotion>>

    @Insert
    suspend fun insertEmotion(emotion: Emotion)
}