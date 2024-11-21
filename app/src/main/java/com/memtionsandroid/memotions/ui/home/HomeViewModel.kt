package com.memtionsandroid.memotions.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import com.memtionsandroid.memotions.data.local.entity.Emotion
import com.memtionsandroid.memotions.data.remote.response.PostResponse
import com.memtionsandroid.memotions.data.repository.EmotionRepository
import com.memtionsandroid.memotions.data.repository.PostRepository
import com.memtionsandroid.memotions.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val emotionRepository: EmotionRepository,
    private val postRepository: PostRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    val postState: StateFlow<DataResult<PostResponse>> =
        postRepository.getPost("1").stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DataResult.Loading
        )

    val emotionsState: StateFlow<DataResult<List<Emotion>>> =
        emotionRepository.emotions.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DataResult.Loading
        )

    val authTokenState: StateFlow<String?> =
        userPreference.authTokenPreference.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun setAuthToken(authToken: String) {
        viewModelScope.launch {
            userPreference.setAuthToken(authToken)
        }
    }

    fun addEmotion(description: String) {
        viewModelScope.launch {
            emotionRepository.add(description)
        }
    }
}