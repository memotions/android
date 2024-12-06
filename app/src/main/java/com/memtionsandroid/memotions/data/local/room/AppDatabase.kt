package com.memtionsandroid.memotions.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.memtionsandroid.memotions.data.local.entity.Journal

@Database(entities = [Journal::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
}