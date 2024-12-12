package com.memtionsandroid.memotions.utils

import com.memtionsandroid.memotions.data.local.entity.Journal
import com.memtionsandroid.memotions.data.remote.response.journals.TagsItem

data class FilterCriteria(
    val name: String = "",
    val tags: List<TagsItem> = emptyList(),
    val startDate: Long? = null,
    val endDate: Long? = null
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


    fun matches(journal: Journal): Boolean {
        val journalDateTime = journal.datetime.toLocalDateTime()

        val isNameMatching = name.isEmpty() || journal.title.contains(name, ignoreCase = true)

        val isTagMatching = tags.isEmpty() || tags.any { tag ->
            journal.tags?.any {
                it.contains(
                    tag.name,
                    ignoreCase = true
                )
            } == true
        }

        val isStartDateMatching =
            startDate == null || journalDateTime.isAfter(startDate.toLocalDate().atStartOfDay())
        val isEndDateMatching =
            endDate == null || journalDateTime.isBefore(
                endDate.toLocalDate().atStartOfDay().plusDays(1)
            )

        return isNameMatching && isTagMatching && isStartDateMatching && isEndDateMatching
    }
}