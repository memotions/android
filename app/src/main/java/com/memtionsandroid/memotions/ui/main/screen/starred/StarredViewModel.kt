package com.memtionsandroid.memotions.ui.main.screen.starred

import androidx.lifecycle.ViewModel
import com.memtionsandroid.memotions.data.local.entity.Journal
import com.memtionsandroid.memotions.utils.FilterCriteria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StarredViewModel: ViewModel() {
    private val _filterCriteria = MutableStateFlow(FilterCriteria())
    val filterCriteria = _filterCriteria.asStateFlow()

    private val _filteredJournals = MutableStateFlow<List<Journal>>(emptyList())
    val filteredJournals = _filteredJournals.asStateFlow()

    private val _journals = MutableStateFlow<List<Journal>>(emptyList())
    val journals = _journals.asStateFlow()


    fun setFilterCriteria(newFilter: FilterCriteria) {
        _filterCriteria.value = newFilter
        setFilteredJournals()
    }

    fun setJournals(journals: List<Journal>) {
        _journals.value = journals.filter{journal ->
            journal.starred
        }
        setFilteredJournals()
    }

    fun setFilteredJournals() {
        val filtered = journals.value.filter { journal ->
            _filterCriteria.value.matches(journal)
        }
        _filteredJournals.value = filtered
    }

}