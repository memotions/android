package com.memtionsandroid.memotions.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreference @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val NOTIFICATION_KEY = booleanPreferencesKey("notification")
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDarkMode
        }
    }

    val darkModePreference: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }

    suspend fun setNotification(isNotification: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_KEY] = isNotification
        }
    }

    val notificationPreference: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[NOTIFICATION_KEY] ?: false
        }

    suspend fun setAuthToken(authToken: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = authToken
        }
    }

    val authTokenPreference: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }

    suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}