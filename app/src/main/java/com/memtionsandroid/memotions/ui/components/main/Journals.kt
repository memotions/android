package com.memtionsandroid.memotions.ui.components.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.data.local.entity.Journal


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Journals(journals: List<Journal>) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp).fillMaxSize()) {
        itemsIndexed(items = journals) { index, journal ->
            val bottomPadding =
                if (index == journals.lastIndex) 80.dp else 4.dp // Padding khusus item terakhir
            JournalCard(
                journal,
                modifier = Modifier.padding(top = 4.dp, bottom = bottomPadding)
            )
        }
    }
}
