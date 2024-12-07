package com.memtionsandroid.memotions.ui.main.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import com.memtionsandroid.memotions.data.local.entity.Journal
import com.memtionsandroid.memotions.data.repository.JournalsRepository
import com.memtionsandroid.memotions.data.repository.LocalRepository
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val journalsRepository: JournalsRepository,
    private val localRepository: LocalRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    private val _journals = MutableStateFlow<DataResult<List<Journal>>>(DataResult.Idle)
    val journals = _journals.asStateFlow()


    fun getJournals() {
        viewModelScope.launch {
            val userId = userPreference.userIdPreference.first()
            journalsRepository.getJournals().collect { remoteState ->
                when (remoteState) {
                    is DataResult.Error -> {
//                        _journals.value = DataResult.Error(remoteState.error)
                        userId?.let { userId ->
                            localRepository.getJournals(userId)
                                .collect { localJournals ->
                                    _journals.value =
                                        DataResult.Success(localJournals)
                                }
                        } ?: run {
                            _journals.value = DataResult.Error(Event("User ID not found"))
                        }
                    }

                    DataResult.Idle -> {}
                    DataResult.Loading -> _journals.value = DataResult.Loading
                    is DataResult.Success -> {
//                        _journals.value = DataResult.Success(remoteState.data)
                        userId?.let { userId ->
                            localRepository.saveAndGetJournals(userId, remoteState.data)
                                .collect { localState ->
                                    when (localState) {
                                        is DataResult.Error -> _journals.value =
                                            DataResult.Error(localState.error)

                                        DataResult.Idle -> {}
                                        DataResult.Loading -> _journals.value = DataResult.Loading
                                        is DataResult.Success -> {
                                            localRepository.getJournals(userId)
                                                .collect { localJournals ->
                                                    _journals.value =
                                                        DataResult.Success(localJournals)
                                                }
                                        }
                                    }
                                }
                        } ?: run {
                            _journals.value = DataResult.Error(Event("User ID not found"))
                        }
                    }
                }
            }
        }
    }

}