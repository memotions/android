package com.memtionsandroid.memotions.ui.components.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Journals(journals: List<Journal>) {
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            delay(2000)
            isRefreshing = false
        }
    }
    PullToRefreshBox(
        state = state,
        onRefresh = onRefresh,
        isRefreshing = isRefreshing,

        ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            itemsIndexed(items = journals) { index, journal ->
                val bottomPadding = if (index == journals.lastIndex) 80.dp else 4.dp // Padding khusus item terakhir
                JournalCard(
                    journal,
                    modifier = Modifier.padding(top = 4.dp,bottom = bottomPadding)
                )
            }
        }
    }
}
