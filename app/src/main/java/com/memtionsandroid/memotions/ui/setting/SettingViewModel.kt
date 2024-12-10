package com.memtionsandroid.memotions.ui.setting

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import com.memtionsandroid.memotions.utils.DailyReminderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class SettingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userPreference: UserPreference
) : ViewModel() {

    val darkModeValue = userPreference.darkModePreference.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun setDarkMode(value: Boolean) {
        viewModelScope.launch {
            userPreference.setDarkMode(value)
        }
    }

    val notificationValue = userPreference.notificationPreference.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun setNotification(value: Boolean) {
        viewModelScope.launch {
            userPreference.setNotification(value)
            if (value) {
                scheduleDailyReminder()
            } else {
                cancelDailyReminder()
            }
        }
    }

    private fun scheduleDailyReminder() {
        val workRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "DailyReminder",
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
    }

    private fun cancelDailyReminder() {
        WorkManager.getInstance(context).cancelUniqueWork("DailyReminder")
    }

    private fun calculateInitialDelay(): Long {
        val now = java.time.ZonedDateTime.now()
        val targetTime = now.toLocalDate().atTime(8, 0).atZone(java.time.ZoneId.systemDefault())
        val delay = if (now.isAfter(targetTime)) {
            targetTime.plusDays(1).toInstant().toEpochMilli() - now.toInstant().toEpochMilli()
        } else {
            targetTime.toInstant().toEpochMilli() - now.toInstant().toEpochMilli()
        }
        return delay
    }
}