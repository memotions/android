package com.memtionsandroid.memotions.ui.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
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
        }
    }
}