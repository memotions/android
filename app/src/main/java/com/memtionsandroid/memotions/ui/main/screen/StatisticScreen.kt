package com.memtionsandroid.memotions.ui.main.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.components.statistic.ItemCard
import com.memtionsandroid.memotions.ui.components.statistic.StatisticTopBar

@Composable
fun StatisticScreen() {
    var itemSelected by remember { mutableStateOf(0) }


    Scaffold(
        topBar = {
            StatisticTopBar()
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    itemsIndexed(items = journalList) { index, journal ->
                        ItemCard(
                            title = journal.date,
                            emotion = "angry",
                            isSelected = itemSelected == index,
                            onClick = { itemSelected = index },
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    )
}