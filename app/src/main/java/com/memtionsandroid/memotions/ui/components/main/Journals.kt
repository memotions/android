package com.memtionsandroid.memotions.ui.components.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun Journals(journals: List<Journal>){
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(items = journals) { journal ->
            JournalCard(journal, modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}
