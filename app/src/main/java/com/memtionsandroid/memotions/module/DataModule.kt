package com.memtionsandroid.memotions.module

import com.memtionsandroid.memotions.data.repository.DefaultEmotionRepository
import com.memtionsandroid.memotions.data.repository.DefaultPostRepository
import com.memtionsandroid.memotions.data.repository.EmotionRepository
import com.memtionsandroid.memotions.data.repository.PostRepository
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

    @Singleton
    @Binds
    fun bindsPostRepository(
        postRepository: DefaultPostRepository
    ): PostRepository

    // TODO: add another repository
}