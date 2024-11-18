package com.memtionsandroid.memotions.module

import android.content.Context
import androidx.room.Room
import com.memtionsandroid.memotions.data.local.room.AppDatabase
import com.memtionsandroid.memotions.data.local.room.EmotionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideEmotionDao(appDatabase: AppDatabase): EmotionDao {
        return appDatabase.emotionDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "memotions_db"
        ).build()
    }
}