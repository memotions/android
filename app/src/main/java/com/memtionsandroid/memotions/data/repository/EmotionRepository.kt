package com.memtionsandroid.memotions.data.repository

import com.memtionsandroid.memotions.data.local.entity.Emotion
import com.memtionsandroid.memotions.data.local.room.EmotionDao
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface EmotionRepository {
    val emotions: Flow<DataResult<List<Emotion>>>
    suspend fun add(description: String):  DataResult<Unit>
}

class DefaultEmotionRepository @Inject constructor(private val emotionDao: EmotionDao) : EmotionRepository {
    override val emotions: Flow<DataResult<List<Emotion>>> = flow {
        emit(DataResult.Loading)
        try{
            emotionDao.getEmotions().collect {
                emit(DataResult.Success(it))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(Event(e.message ?: "Failed to get emotions")))
        }
    }

    override suspend fun add(description: String): DataResult<Unit> {
        return try {
            emotionDao.insertEmotion(Emotion(description = description))
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error(Event(e.message ?: "Failed to add emotion"))
        }
    }
}