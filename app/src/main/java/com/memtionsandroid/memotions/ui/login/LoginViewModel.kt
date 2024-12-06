package com.memtionsandroid.memotions.ui.login

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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreference: UserPreference,
    private val authRepository: AuthRepository
) : ViewModel() {

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

    val isFirstLaunch = userPreference.isFirstLaunchPreference.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    fun setFirstLaunch(value: Boolean) {
        viewModelScope.launch {
            userPreference.setFirstLaunch(value)
        }
    }

    val authToken = userPreference.authTokenPreference.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    private val _loginResult = MutableStateFlow<DataResult<AuthResponse>>(DataResult.Idle)
    val loginResult = _loginResult.asStateFlow()

    fun login() {
        viewModelScope.launch {
            authRepository.loginUser(emailValue, passwordValue).collect { result ->
                _loginResult.value = result
                if (result is DataResult.Success) {
                    userPreference.setAuthToken(result.data.data.token)
                    userPreference.setUserId(result.data.data.user.id)
                    userPreference.setUserName(result.data.data.user.name)
                    userPreference.setUserEmail(result.data.data.user.email)
                }
            }
        }
    }
}