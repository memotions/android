package com.memtionsandroid.memotions.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import com.memtionsandroid.memotions.data.remote.response.auth.AuthResponse
import com.memtionsandroid.memotions.data.repository.AuthRepository
import com.memtionsandroid.memotions.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userPreference: UserPreference,
    private val authRepository: AuthRepository
): ViewModel() {
    var nameValue by mutableStateOf("")
        private set

    fun setName(value: String) {
        nameValue = value
    }

    var emailValue by mutableStateOf("")
        private set

    fun setEmail(value: String) {
        emailValue = value
    }

    var passwordValue by mutableStateOf("")
        private set

    fun setPassword(value: String) {
        passwordValue = value
    }

    fun setFirstLaunch(value: Boolean) {
        viewModelScope.launch {
            userPreference.setFirstLaunch(value)
        }
    }

    private val _registerResult = MutableStateFlow<DataResult<AuthResponse>>(DataResult.Idle)
    val registerResult = _registerResult.asStateFlow()

    fun register() {
        viewModelScope.launch {
            authRepository.registerUser(nameValue, emailValue, passwordValue).collect { result ->
                _registerResult.value = result
            }
        }
    }
}