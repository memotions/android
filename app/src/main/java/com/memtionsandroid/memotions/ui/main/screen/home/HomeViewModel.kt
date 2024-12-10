package com.memtionsandroid.memotions.ui.main.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memtionsandroid.memotions.data.local.entity.Journal
import com.memtionsandroid.memotions.data.remote.response.statistics.CurrentLevel
import com.memtionsandroid.memotions.data.repository.StatisticsRepository
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.FilterCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository,
) : ViewModel() {

    private val _currentStreak = MutableStateFlow(1)
    val currentStreak = _currentStreak.asStateFlow()

    private val _currentLevel = MutableStateFlow(CurrentLevel(1, 0, 0, 2))
    val currentLevel = _currentLevel.asStateFlow()

    private val _filterCriteria = MutableStateFlow(FilterCriteria())
    val filterCriteria = _filterCriteria.asStateFlow()

    private val _filteredJournals = MutableStateFlow<List<Journal>>(emptyList())
    val filteredJournals = _filteredJournals.asStateFlow()

    private val _journals = MutableStateFlow<List<Journal>>(emptyList())
    val journals = _journals.asStateFlow()

    fun getUserStatistics() {
        viewModelScope.launch {
            statisticsRepository.getStatistics().collect {
                when (it) {
                    is DataResult.Error -> {}
                    DataResult.Idle -> {}
                    DataResult.Loading -> {}
                    is DataResult.Success -> {
                        _currentStreak.value = it.data.data.currentStreak.streakLength
                        _currentLevel.value = it.data.data.currentLevel
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setFilterCriteria(newFilter: FilterCriteria) {
        _filterCriteria.value = newFilter
        setFilteredJournals()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setJournals(journals: List<Journal>) {
        _journals.value = journals
        setFilteredJournals()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setFilteredJournals() {
        val filtered = journals.value.filter { journal ->
            _filterCriteria.value.matches(journal)
        }
        _filteredJournals.value = filtered
    }
}