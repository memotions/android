package com.memtionsandroid.memotions.ui.main.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.components.main.Journals
import com.memtionsandroid.memotions.ui.components.statistic.StatisticTopBar

@Composable
fun StatisticScreen() {
    Scaffold(
        topBar = {
            StatisticTopBar()
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(bottom = 80.dp)
            ) {
                Journals(starredJournalList)
            }
        }
    )
}