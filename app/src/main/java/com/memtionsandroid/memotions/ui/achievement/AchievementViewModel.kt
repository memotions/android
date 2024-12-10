package com.memtionsandroid.memotions.ui.achievement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memtionsandroid.memotions.data.remote.response.statistics.AchievementsResponse
import com.memtionsandroid.memotions.data.remote.response.statistics.AchievementsResponseItem
import com.memtionsandroid.memotions.data.repository.StatisticsRepository
import com.memtionsandroid.memotions.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {
    private val _achievements = MutableStateFlow<DataResult<AchievementsResponse>>(DataResult.Idle)
    val achievements = _achievements.asStateFlow()

    private val _totalAchievements = MutableStateFlow(0)
    val totalAchievementCount = _totalAchievements.asStateFlow()

    private val _completedAchievements = MutableStateFlow(0)
    val completedAchievementCount = _completedAchievements.asStateFlow()

    private val _filteredAchievements =
        MutableStateFlow<List<AchievementsResponseItem>>(
            emptyList()
        )
    val filteredAchievements = _filteredAchievements.asStateFlow()


    fun getAchievements() {
        viewModelScope.launch {
            statisticsRepository.getUserAchievements().collect {
                when (it) {
                    is DataResult.Error -> _achievements.value = it
                    DataResult.Idle -> {}
                    DataResult.Loading -> _achievements.value = it
                    is DataResult.Success -> {
                        _achievements.value = it

                        _totalAchievements.value = it.data.data
                            ?.filter { item ->
                                item.tier > 0
                            }?.size ?: 0
                        _completedAchievements.value = it.data.data
                            ?.filter { item ->
                                item.tier > 0
                            }?.count { item ->
                                item.completed
                            } ?: 0

                        val achievements = it.data.data
                            ?.filter { item ->
                                item.completed
                            }
                        val filterGroup = achievements?.groupBy { item ->
                            item.code
                        }?.mapValues { entry ->
                            entry.value.sortedByDescending { it.tier }
                        } ?: emptyMap()

                        _filteredAchievements.value = filterGroup.values.mapNotNull { group ->
                            group.firstOrNull()
                        }
                    }
                }
            }
        }
    }
}