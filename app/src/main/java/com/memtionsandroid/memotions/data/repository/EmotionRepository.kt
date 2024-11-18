package com.memtionsandroid.memotions.data.repository

import com.memtionsandroid.memotions.data.local.entity.Emotion
import com.memtionsandroid.memotions.data.local.room.EmotionDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface EmotionRepository {
    val emotions: Flow<List<Emotion>>
    suspend fun add(description: String)
}

class DefaultEmotionRepository @Inject constructor(private val emotionDao: EmotionDao) : EmotionRepository {
    override val emotions: Flow<List<Emotion>>
        get() = emotionDao.getEmotions()

    override suspend fun add(description: String) {
        emotionDao.insertEmotion(Emotion(description = description))
    }
}