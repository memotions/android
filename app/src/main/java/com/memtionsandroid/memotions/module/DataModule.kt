package com.memtionsandroid.memotions.module

import com.memtionsandroid.memotions.data.repository.DefaultEmotionRepository
import com.memtionsandroid.memotions.data.repository.EmotionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsEmotionRepository(
        emotionRepository: DefaultEmotionRepository
    ): EmotionRepository
}