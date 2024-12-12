package com.memtionsandroid.memotions.ui.addjournal

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
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import com.memtionsandroid.memotions.data.remote.response.journals.TagsItem
import com.memtionsandroid.memotions.data.repository.JournalStatus
import com.memtionsandroid.memotions.data.repository.JournalsRepository
import com.memtionsandroid.memotions.data.repository.LocalRepository
import com.memtionsandroid.memotions.ui.NavigationRoutes
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
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddJournalViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userPreference: UserPreference,
    private val journalsRepository: JournalsRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    var titleValue by mutableStateOf("")
        private set

    fun setTitle(value: String) {
        titleValue = value
    }

    var contentValue by mutableStateOf("")
        private set

    fun setContent(value: String) {
        contentValue = value
    }

    var starredValue by mutableStateOf(false)
        private set

    fun setStarred(value: Boolean) {
        starredValue = value
    }

    private var statusValue by mutableStateOf(JournalStatus.DRAFT.status)

    private fun setStatus(value: String) {
        statusValue = value
    }

    var datetimeValue: LocalDateTime by mutableStateOf(LocalDateTime.now())
        private set

    fun setDatetime(value: LocalDateTime) {
        datetimeValue = value
    }

    var tagsValue by mutableStateOf(listOf<String>())
        private set

    fun updateTagsValue(value: List<String>) {
        tagsValue = value
    }

    fun addTagsValue(value: String) {
        tagsValue += value
    }

    var userTags by mutableStateOf(listOf<TagsItem>())
        private set


    var isLoading by mutableStateOf(false)
        private set

    var errorMessages by mutableStateOf("")

    fun fetchDraftJournal(journalId: Int) = viewModelScope.launch {
        if (isConnected.value) {
            journalsRepository.getJournalById(journalId).collect { result ->
                when (result) {
                    is DataResult.Success -> {
                        isLoading = false
                        titleValue = result.data.data.title
                        contentValue = result.data.data.content
                        starredValue = result.data.data.starred
                        datetimeValue = stringToLocalDateTime(result.data.data.datetime)
                        tagsValue = result.data.data.tags ?: emptyList()
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
                    tagsValue = journal.tags ?: emptyList()
                }
            }
        }
    }

    fun fetchUserTags() = viewModelScope.launch {
        if (isConnected.value) {
            journalsRepository.getCurrentUserTags().collect { result ->
                when (result) {
                    is DataResult.Success -> {
                        userTags = result.data.data ?: emptyList()
                        isLoading = false
                    }

                    is DataResult.Error -> {
                        isLoading = false
                        errorMessages = result.error.getContentIfNotHandled()
                            ?: "Terjadi kesalahan saat mendapatkan tags, coba lagi atau cek koneksi internet"
                    }

                    is DataResult.Loading -> {
                        isLoading = true
                    }

                    else -> {}
                }
            }
        }
    }

    fun saveDraftJournal(navController: NavHostController) = viewModelScope.launch {
        setStatus(JournalStatus.DRAFT.status)
        val isoFormattedDatetime = datetimeValue
            .toInstant(ZoneOffset.UTC)
            .toString()
        journalsRepository.postJournal(
            titleValue,
            contentValue,
            isoFormattedDatetime,
            starredValue,
            statusValue,
            tagsValue
        ).collect { result ->
            when (result) {
                is DataResult.Success -> {
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
                                    navController.popBackStack()
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
                        ?: "Terjadi kesalahan saat menambahkan journal draft, coba lagi atau cek koneksi internet"
                }

                is DataResult.Loading -> {
                    isLoading = true
                }

                else -> {}
            }
        }
    }

    fun saveDraftAgainJournal(journalId: Int, navController: NavHostController) =
        viewModelScope.launch {
            setStatus(JournalStatus.DRAFT.status)
            val isoFormattedDatetime = datetimeValue
                .toInstant(ZoneOffset.UTC)
                .toString()
            journalsRepository.updateJournalById(
                journalId,
                titleValue,
                contentValue,
                isoFormattedDatetime,
                starredValue,
                statusValue,
                tagsValue
            ).collect { result ->
                when (result) {
                    is DataResult.Success -> {
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
                                        navController.popBackStack()
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
                            ?: "Terjadi kesalahan saat mengupdate journal draft, coba lagi atau cek koneksi internet"
                    }

                    is DataResult.Loading -> {
                        isLoading = true
                    }

                    else -> {}
                }
            }
        }

    fun deleteDraftJournal(journalId: Int, navController: NavHostController) =
        viewModelScope.launch {
            journalsRepository.deleteJournalById(journalId).collect { result ->
                when (result) {
                    is DataResult.Success -> {
                        withContext(Dispatchers.IO) {
                            localRepository.deleteJournal(journalId)
                        }
                        isLoading = false
                        navController.popBackStack()
                    }

                    is DataResult.Error -> {
                        isLoading = false
                        errorMessages = result.error.getContentIfNotHandled()
                            ?: "Terjadi kesalahan saat menghapus journal draft, coba lagi atau cek koneksi internet"
                    }

                    is DataResult.Loading -> {
                        isLoading = true
                    }

                    else -> {}
                }
            }
        }

    fun saveJournal(navController: NavHostController) = viewModelScope.launch {
        setStatus(JournalStatus.PUBLISHED.status)
        val isoFormattedDatetime = datetimeValue
            .toInstant(ZoneOffset.UTC)
            .toString()
        journalsRepository.postJournal(
            titleValue,
            contentValue,
            isoFormattedDatetime,
            starredValue,
            statusValue,
            tagsValue
        ).collect { result ->
            when (result) {
                is DataResult.Success -> {
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
                                    navController.navigate(NavigationRoutes.MAIN) {
                                        popUpTo(0) { inclusive = true }
                                        launchSingleTop = true
                                    }
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
                        ?: "Terjadi kesalahan saat menambahkan journal draft, coba lagi atau cek koneksi internet"
                }

                is DataResult.Loading -> {
                    isLoading = true
                }

                else -> {}
            }
        }
    }

    fun updateJournal(journalId: Int, navController: NavHostController) = viewModelScope.launch {
        setStatus(JournalStatus.PUBLISHED.status)
        val isoFormattedDatetime = datetimeValue
            .toInstant(ZoneOffset.UTC)
            .toString()
        journalsRepository.updateJournalById(
            journalId,
            titleValue,
            contentValue,
            isoFormattedDatetime,
            starredValue,
            statusValue,
            tagsValue
        ).collect { result ->
            when (result) {
                is DataResult.Success -> {
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
                                    navController.navigate(NavigationRoutes.MAIN) {
                                        popUpTo(0) { inclusive = true }
                                        launchSingleTop = true
                                    }
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
                        ?: "Terjadi kesalahan saat mengupdate journal draft, coba lagi atau cek koneksi internet"
                }

                is DataResult.Loading -> {
                    isLoading = true
                }

                else -> {}
            }
        }
    }

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