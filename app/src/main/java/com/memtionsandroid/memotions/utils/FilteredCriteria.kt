package com.memtionsandroid.memotions.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.memtionsandroid.memotions.data.local.entity.Journal
import com.memtionsandroid.memotions.data.remote.response.journals.TagsItem
import java.time.LocalDate

data class FilterCriteria(
    val name: String = "",
    val tags: List<TagsItem> = emptyList(),
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null
) {

    fun addTag(tag: TagsItem): FilterCriteria {
        if (!tags.contains(tag)) {
            return copy(tags = tags + tag)
        }
        return this
    }

    fun removeTag(tag: TagsItem): FilterCriteria {
        if (tags.contains(tag)) {
            return copy(tags = tags - tag)
        }
        return this
    }

    fun countFilter(): Int {
        var count = 0
        if (tags.isNotEmpty()) count += tags.size
        if (startDate != null) count++

        return count
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun matches(journal: Journal): Boolean {
        val journalDateTime = journal.datetime.toLocalDateTime()

        // Memeriksa apakah nama jurnal cocok dengan name (single string)
        val isNameMatching = name.isEmpty() || journal.title.contains(name, ignoreCase = true)

        // Memeriksa apakah salah satu tag dalam FilterCriteria cocok dengan tags dalam jurnal
        val isTagMatching = tags.isEmpty() || tags.any { tag ->
            journal.tags?.any {
                it.contains(
                    tag.name,
                    ignoreCase = true
                )
            } == true
        }

        // Memeriksa apakah tanggal jurnal cocok dengan rentang yang diberikan
        val isStartDateMatching =
            startDate == null || journalDateTime.isAfter(startDate.atStartOfDay())
        val isEndDateMatching =
            endDate == null || journalDateTime.isBefore(endDate.atStartOfDay())

        return isNameMatching && isTagMatching && isStartDateMatching && isEndDateMatching
    }
}