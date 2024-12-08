package com.memtionsandroid.memotions.ui.main.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memtionsandroid.memotions.data.remote.response.statistics.StatisticsResponse
import com.memtionsandroid.memotions.data.repository.StatisticsRepository
import com.memtionsandroid.memotions.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {
    private val _userStatistics = MutableStateFlow<DataResult<StatisticsResponse>>(DataResult.Idle)
    val userStatistics = _userStatistics.asStateFlow()

    fun getUserStatistics() {
        viewModelScope.launch {
            statisticsRepository.getStatistics().collect {
                when (it) {
                    is DataResult.Error -> _userStatistics.value = it
                    DataResult.Idle -> {}
                    DataResult.Loading -> _userStatistics.value = DataResult.Loading
                    is DataResult.Success -> _userStatistics.value = it
                }
            }
        }
    }
}