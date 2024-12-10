package com.memtionsandroid.memotions

import android.app.Application
import com.memtionsandroid.memotions.utils.FcmTokenManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MemotionsApplication : Application() {
    @Inject
    lateinit var fcmTokenManager: FcmTokenManager

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        fcmTokenManager.retrieveAndSaveToken()
    }
}