package com.memtionsandroid.memotions.utils

import com.google.firebase.messaging.FirebaseMessaging
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmTokenManager @Inject constructor(
    private val userPreference: UserPreference
) {
    fun retrieveAndSaveToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                token?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        userPreference.setFcmToken(it)
                    }
                }
            }
        }
    }
}