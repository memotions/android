package com.memtionsandroid.memotions.ui.viewjournal

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import com.memtionsandroid.memotions.data.remote.response.journals.EmotionAnalysisItem
import com.memtionsandroid.memotions.data.repository.JournalStatus
import com.memtionsandroid.memotions.data.repository.JournalsRepository
import com.memtionsandroid.memotions.data.repository.LocalRepository
import com.memtionsandroid.memotions.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ViewJournalViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userPreference: UserPreference,
    private val localRepository: LocalRepository,
    private val journalsRepository: JournalsRepository
) : ViewModel() {

    var titleValue by mutableStateOf("")
        private set

    var contentValue by mutableStateOf("")
        private set

    var feedbackValue: String? by mutableStateOf(null)
        private set

    var starredValue by mutableStateOf(false)
        private set

    fun fetchJournal(journalId: Int) = viewModelScope.launch {
        if (isConnected.value) {
            journalsRepository.getJournalById(journalId).collect { result ->
                when (result) {
                    is DataResult.Success -> {
                        titleValue = result.data.data.title
                        contentValue = result.data.data.content
                        starredValue = result.data.data.starred
                        datetimeValue = stringToLocalDateTime(result.data.data.datetime)
                        statusValue = result.data.data.status
                        feedbackValue = result.data.data.feedback
                        emotionAnalysis =
                            result.data.data.emotionAnalysis?.sortedByDescending { it.confidence }
                        tagsValue = result.data.data.tags ?: emptyList()

                        val userId = userPreference.userIdPreference.first().toString().toInt()
                        localRepository.saveOrUpdateJournal(userId, result.data)
                            .collect { innerResult ->
                                when (innerResult) {
                                    is DataResult.Error -> {
                                        isLoading = false
                                        errorMessages = innerResult.error.getContentIfNotHandled()
                                            ?: "Gagal menyimpan ke database lokal"
                                    }

                                    is DataResult.Success -> {
                                        isLoading = false
                                    }

                                    is DataResult.Loading -> {
                                        isLoading = true
                                    }

                                    else -> {}
                                }
                            }
                    }

                    is DataResult.Error -> {
                        isLoading = false
                        errorMessages = result.error.getContentIfNotHandled()
                            ?: "Terjadi kesalahan saat mendapatkan journal draft, coba lagi atau cek koneksi internet"
                    }

                    is DataResult.Loading -> {
                        isLoading = true
                    }

                    else -> {}
                }
            }
        } else {
            localRepository.getJournalById(journalId).collect { journal ->
                if (journal == null) {
                    isLoading = false
                    errorMessages = "Journal tidak ditemukan di database lokal"
                } else {
                    isLoading = false
                    titleValue = journal.title
                    contentValue = journal.content
                    starredValue = journal.starred
                    datetimeValue = stringToLocalDateTime(journal.datetime)
                    statusValue = journal.status
                    feedbackValue = journal.feedback
                    emotionAnalysis = journal.emotionAnalysis?.map {
                        EmotionAnalysisItem(
                            emotion = it.emotion,
                            confidence = it.confidence
                        )
                    }?.sortedByDescending { it.confidence }
                    tagsValue = journal.tags ?: emptyList()
                }
            }
        }
    }

    fun toggleStarredJournal(journalId: Int) = viewModelScope.launch {
        journalsRepository.toggleStarStatusJournal(journalId).collect { result ->
            when (result) {
                is DataResult.Error -> {
                    isLoading = false
                    errorMessages = result.error.getContentIfNotHandled()
                        ?: "Terjadi kesalahan saat mengubah status starred, coba lagi atau cek koneksi internet"
                }

                is DataResult.Success -> {
                    starredValue = !starredValue
                    withContext(Dispatchers.IO) {
                        localRepository.toggleStarredStatus(journalId, starredValue)
                    }
                    isLoading = false
                }

                is DataResult.Loading -> isLoading = true
                is DataResult.Idle -> {}
            }
        }
    }

    private var statusValue by mutableStateOf(JournalStatus.PUBLISHED.status)

    var datetimeValue: LocalDateTime by mutableStateOf(LocalDateTime.now())
        private set

    var tagsValue by mutableStateOf(listOf<String>())
        private set

    var emotionAnalysis: List<EmotionAnalysisItem>? by mutableStateOf(null)

    var isLoading by mutableStateOf(false)
        private set

    var errorMessages by mutableStateOf("")

    private fun stringToLocalDateTime(datetime: String): LocalDateTime {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val datetimeWithoutZ = datetime.removeSuffix("Z")
        return LocalDateTime.parse(datetimeWithoutZ, formatter)
    }

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isConnected = MutableStateFlow(isInternetAvailable())
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isConnected.value = true
        }

        override fun onLost(network: Network) {
            _isConnected.value = isInternetAvailable()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            _isConnected.value =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
    }

    init {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun isInternetAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onCleared() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        super.onCleared()
    }
}