package com.memtionsandroid.memotions.ui.login

import androidx.lifecycle.ViewModel
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreference: UserPreference
): ViewModel() {
}