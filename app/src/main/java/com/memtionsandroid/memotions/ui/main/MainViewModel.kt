package com.memtionsandroid.memotions.ui.main

import ConnectivityObserver
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import com.memtionsandroid.memotions.data.local.entity.Journal
import com.memtionsandroid.memotions.data.remote.response.journals.TagsItem
import com.memtionsandroid.memotions.data.repository.JournalsRepository
import com.memtionsandroid.memotions.data.repository.LocalRepository
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val journalsRepository: JournalsRepository,
    private val localRepository: LocalRepository,
    private val userPreference: UserPreference
) : ViewModel() {
    private val firstLaunchedex = MutableStateFlow(false)
    private val connectivityObserver = ConnectivityObserver(context)

    val isConnected: StateFlow<Boolean> = connectivityObserver.isConnected
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = connectivityObserver.checkCurrentState()
        )

    private val _journals = MutableStateFlow<DataResult<List<Journal>>>(DataResult.Idle)
    val journals = _journals.asStateFlow()

    private val _currentTags = MutableStateFlow<List<TagsItem>>(emptyList())
    val currentTags = _currentTags.asStateFlow()

    fun getCurrentTags() {
        viewModelScope.launch {
            journalsRepository.getCurrentUserTags().collect {
                when (it) {
                    is DataResult.Error -> _currentTags.value = emptyList()
                    DataResult.Idle -> _currentTags.value = emptyList()
                    DataResult.Loading -> _currentTags.value = emptyList()
                    is DataResult.Success -> _currentTags.value = it.data.data ?: emptyList()
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getJournals() {
        if (firstLaunchedex.value) return
        firstLaunchedex.value = true
        viewModelScope.launch {
            val userId = userPreference.userIdPreference.first()
            journalsRepository.getJournals().collect { remoteState ->
                when (remoteState) {
                    is DataResult.Error -> {
                        userId?.let { userId ->
                            localRepository.getJournals(userId)
                                .collect { localJournals ->
                                    val sortedJournals = localJournals.sortedByDescending {
                                        LocalDateTime.parse(
                                            it.datetime,
                                            DateTimeFormatter.ISO_DATE_TIME
                                        )
                                    }
                                    _journals.value =
                                        DataResult.Success(sortedJournals)
                                }
                        } ?: run {
                            _journals.value = DataResult.Error(Event("User ID not found"))
                        }
                    }

                    DataResult.Idle -> {}
                    DataResult.Loading -> _journals.value = DataResult.Loading
                    is DataResult.Success -> {
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
                                                    val sortedJournals =
                                                        localJournals.sortedByDescending {
                                                            LocalDateTime.parse(
                                                                it.datetime,
                                                                DateTimeFormatter.ISO_DATE_TIME
                                                            )
                                                        }
                                                    _journals.value =
                                                        DataResult.Success(sortedJournals)
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