package com.memtionsandroid.memotions.module

import com.memtionsandroid.memotions.data.repository.AuthRepository
import com.memtionsandroid.memotions.data.repository.DefaultAuthRepository
import com.memtionsandroid.memotions.data.repository.DefaultJournalsRepository
import com.memtionsandroid.memotions.data.repository.DefaultLocalRepository
import com.memtionsandroid.memotions.data.repository.DefaultStatisticsRepository
import com.memtionsandroid.memotions.data.repository.JournalsRepository
import com.memtionsandroid.memotions.data.repository.LocalRepository
import com.memtionsandroid.memotions.data.repository.StatisticsRepository
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
    fun bindsAuthRepository(
        authRepository: DefaultAuthRepository
    ): AuthRepository

    @Singleton
    @Binds
    fun bindsJournalsRepository(
        journalsRepository: DefaultJournalsRepository
    ): JournalsRepository

    @Singleton
    @Binds
    fun bindsStatisticsRepository(
        statisticsRepository: DefaultStatisticsRepository
    ): StatisticsRepository

    @Singleton
    @Binds
    fun bindsLocalRepository(
        localRepository: DefaultLocalRepository
    ): LocalRepository
}