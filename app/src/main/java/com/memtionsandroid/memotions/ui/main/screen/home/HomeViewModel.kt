package com.memtionsandroid.memotions.ui.main.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.memtionsandroid.memotions.data.local.datastore.UserPreference
import com.memtionsandroid.memotions.data.local.entity.Journal
import com.memtionsandroid.memotions.data.repository.JournalsRepository
import com.memtionsandroid.memotions.data.repository.LocalRepository
import com.memtionsandroid.memotions.utils.FilterCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val journalsRepository: JournalsRepository,
    private val localRepository: LocalRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    private val _filterCriteria = MutableStateFlow(FilterCriteria())
    val filterCriteria = _filterCriteria.asStateFlow()

    private val _filteredJournals = MutableStateFlow<List<Journal>>(emptyList())
    val filteredJournals = _filteredJournals.asStateFlow()

    private val _journals = MutableStateFlow<List<Journal>>(emptyList())
    val journals = _journals.asStateFlow()


    // Fungsi untuk memfilter jurnal berdasarkan kriteria
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

    // Fungsi untuk melakukan filter berdasarkan filterCriteria
    @RequiresApi(Build.VERSION_CODES.O)
    fun setFilteredJournals() {
        val filtered = journals.value.filter { journal ->
            _filterCriteria.value.matches(journal)
        }
        _filteredJournals.value = filtered
    }


}