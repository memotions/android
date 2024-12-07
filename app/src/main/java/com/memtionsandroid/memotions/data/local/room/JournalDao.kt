package com.memtionsandroid.memotions.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.memtionsandroid.memotions.data.local.entity.Journal
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal WHERE userId = :userId AND deleted = 0 ORDER BY id DESC")
    fun getJournals(userId: Int): Flow<List<Journal>>

    @Query("SELECT * FROM journal WHERE id = :id AND deleted = 0")
    fun getJournalById(id: Int): Flow<Journal?>

    @Upsert
    suspend fun insertOrUpdateJournals(journals: List<Journal>?)

    @Query("DELETE FROM journal WHERE id = :journalId")
    suspend fun deleteJournalById(journalId: Int)

    @Query("UPDATE journal SET starred = :isStarred WHERE id = :journalId")
    suspend fun toggleStarredStatus(journalId: Int, isStarred: Boolean)
}