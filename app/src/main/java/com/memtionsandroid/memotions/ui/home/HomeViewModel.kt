package com.memtionsandroid.memotions.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memtionsandroid.memotions.data.local.entity.Emotion
import com.memtionsandroid.memotions.data.repository.EmotionRepository
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val emotionRepository: EmotionRepository
) : ViewModel() {

    private val _emotionsState = MutableStateFlow<DataResult<List<Emotion>>>(DataResult.Loading)
    val emotionsState: StateFlow<DataResult<List<Emotion>>> = _emotionsState

    init {
        getEmotions()
    }

    private fun getEmotions() {
        viewModelScope.launch {
            emotionRepository.emotions
                .onStart {
                    _emotionsState.value = DataResult.Loading
                }
                .catch { e ->
                    _emotionsState.value = DataResult.Error(Event(e.message ?: "Unknown Error"))
                }
                .collect { emotions ->
                    _emotionsState.value = DataResult.Success(emotions)
                }
        }
    }

    fun addEmotion(description: String) {
        viewModelScope.launch {
            try {
                emotionRepository.add(description)
                getEmotions()
            } catch (e: Exception) {
                _emotionsState.value = DataResult.Error(Event(e.message ?: "Failed to add emotion"))
            }
        }
    }
}