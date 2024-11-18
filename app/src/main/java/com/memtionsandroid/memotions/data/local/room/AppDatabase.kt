package com.memtionsandroid.memotions.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.memtionsandroid.memotions.data.local.entity.Emotion

@Database(entities = [Emotion::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun emotionDao(): EmotionDao
}