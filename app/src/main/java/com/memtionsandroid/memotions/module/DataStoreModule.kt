package com.memtionsandroid.memotions.module

import android.content.Context
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideUserPreference(@ApplicationContext context: Context): UserPreference {
        return UserPreference(context)
    }
}