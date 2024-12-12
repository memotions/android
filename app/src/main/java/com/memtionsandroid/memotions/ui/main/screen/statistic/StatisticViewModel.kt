package com.memtionsandroid.memotions.ui.main.screen.statistic

import androidx.lifecycle.ViewModel
import com.memtionsandroid.memotions.data.local.entity.Journal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale
import java.util.SortedMap

class StatisticViewModel : ViewModel() {

    private val _journals = MutableStateFlow<List<Journal>>(emptyList())

    val journals = _journals.asStateFlow()

    private val _groupedJournalListDate =
        MutableStateFlow<Map<String, SortedMap<String, List<Journal>>>>(
            emptyMap()
        )
    val groupedJournalListDate = _groupedJournalListDate.asStateFlow()

    private val _groupedJournalListWeek =
        MutableStateFlow<Map<String, SortedMap<String, List<Journal>>>>(
            emptyMap()
        )
    val groupedJournalListWeek = _groupedJournalListWeek.asStateFlow()

    private val _groupedJournalListMonth =
        MutableStateFlow<Map<String, SortedMap<String, List<Journal>>>>(
            emptyMap()
        )
    val groupedJournalListMonth = _groupedJournalListMonth.asStateFlow()

    fun setJournals(journals: List<Journal>) {
        _journals.value = journals
        setJournalsDateMode(journals)
        Timber.tag("StatisticViewModel").d(groupedJournalListDate.toString())
        setJournalsWeekMode(journals)
        setJournalsMonthMode(journals)
    }

    fun setJournalsDateMode(journals: List<Journal>) {
        val dateFormatterMonth = DateTimeFormatter.ofPattern("yyyy MM")
        val dateFormatterDate = DateTimeFormatter.ofPattern("dd MMMM yyyy")

        _groupedJournalListDate.value = journals.groupBy { journal ->
            val localDate = OffsetDateTime.parse(journal.datetime).toLocalDate()
            localDate.format(dateFormatterMonth)
        }.toSortedMap(reverseOrder()).mapValues { entry ->
            entry.value.groupBy { journal ->
                val localDate = OffsetDateTime.parse(journal.datetime).toLocalDate()
                localDate.format(dateFormatterDate)
            }.toSortedMap(reverseOrder())
        }
    }

    fun setJournalsWeekMode(journals: List<Journal>) {
        val dateFormatterMonth = DateTimeFormatter.ofPattern("yyyy MM")

        _groupedJournalListWeek.value = journals.groupBy { journal ->
            val localDate = OffsetDateTime.parse(journal.datetime).toLocalDate()
            localDate.format(dateFormatterMonth)
        }.toSortedMap(reverseOrder()).mapValues { entry ->
            entry.value.groupBy { journal ->
                val localDate = OffsetDateTime.parse(journal.datetime).toLocalDate()
                val weekFields = WeekFields.of(Locale.getDefault())
                val weekNumber = localDate.get(weekFields.weekOfMonth())
                "Minggu-$weekNumber ${entry.key}"
            }.toSortedMap(reverseOrder())
        }
    }

    fun setJournalsMonthMode(journals: List<Journal>) {
        val dateFormatterYear = DateTimeFormatter.ofPattern("yyyy")
        val dateFormatterMonthYear = DateTimeFormatter.ofPattern("MMMM yyyy")

        _groupedJournalListMonth.value = journals.groupBy { journal ->
            val localDate = OffsetDateTime.parse(journal.datetime).toLocalDate()
            localDate.format(dateFormatterYear)
        }.mapValues { entry ->
            entry.value.groupBy { journal ->
                val localDate = OffsetDateTime.parse(journal.datetime).toLocalDate()
                localDate.format(dateFormatterMonthYear)
            }.toSortedMap()
        }
    }
}