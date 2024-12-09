//package com.memtionsandroid.memotions.module
//
//import ConnectivityObserver
//import android.content.Context
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//
//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun provideConnectivityObserver(
//        @ApplicationContext context: Context
//    ): ConnectivityObserver {
//        return ConnectivityObserver(context)
//    }
//}
