package com.memtionsandroid.memotions.data.repository

import com.memtionsandroid.memotions.data.local.entity.EmotionAnalysis
import com.memtionsandroid.memotions.data.local.entity.Journal
import com.memtionsandroid.memotions.data.local.room.JournalDao
import com.memtionsandroid.memotions.data.remote.response.journals.JournalsDataItem
import com.memtionsandroid.memotions.data.remote.response.journals.JournalsResponse
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LocalRepository {
    suspend fun saveAndGetJournals(
        userId: Int,
        journalsResponse: JournalsResponse
    ): Flow<DataResult<JournalsResponse>>

    fun getJournals(userId: Int): Flow<List<Journal>>
    fun getJournalById(id: Int): Flow<Journal?>
    suspend fun deleteJournal(journalId: Int)
    suspend fun toggleStarredStatus(journalId: Int, isStarred: Boolean)
}

class DefaultLocalRepository @Inject constructor(
    private val journalDao: JournalDao
) : LocalRepository {

    override suspend fun saveAndGetJournals(
        userId: Int,
        journalsResponse: JournalsResponse
    ): Flow<DataResult<JournalsResponse>> = flow {
        emit(DataResult.Loading)
        try {
            val journals = journalsResponse.data.map { it.toJournalEntity(userId) }
            withContext(Dispatchers.IO) {
                journalDao.insertOrUpdateJournals(journals)
            }
            emit(DataResult.Success(journalsResponse))
        } catch (e: Exception) {
            emit(DataResult.Error(Event("Failed to save and get journals")))
        }
    }

    override fun getJournals(userId: Int): Flow<List<Journal>> {
        return journalDao.getJournals(userId)
    }

    override fun getJournalById(id: Int): Flow<Journal?> {
        return journalDao.getJournalById(id)
    }

    override suspend fun deleteJournal(journalId: Int) {
        journalDao.deleteJournalById(journalId)
    }

    override suspend fun toggleStarredStatus(journalId: Int, isStarred: Boolean) {
        journalDao.toggleStarredStatus(journalId, isStarred)
    }

    private fun JournalsDataItem.toJournalEntity(userId: Int): Journal {
        return Journal(
            id = id,
            userId = userId,
            title = title,
            content = content,
            datetime = datetime,
            createdAt = createdAt,
            status = status,
            deleted = deleted,
            starred = starred,
            feedback = feedback,
            tags = tags?.takeIf { it.isNotEmpty() },
            emotionAnalysis = emotionAnalysis?.takeIf { it.isNotEmpty() }?.map {
                EmotionAnalysis(
                    emotion = it.emotion,
                    confidence = it.confidence
                )
            }
        )
    }

}