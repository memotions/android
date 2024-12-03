package com.memtionsandroid.memotions.ui.main.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.components.main.journalList
import com.memtionsandroid.memotions.ui.components.statistic.ItemCard
import com.memtionsandroid.memotions.ui.components.statistic.StatisticTopBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticScreen() {
    var itemSelected by remember { mutableStateOf(0) }

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val groupedJournalList = journalList.groupBy { journal ->
        val localDate = LocalDate.parse(journal.date, dateFormatter)
        localDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
    }

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

                    groupedJournalList.forEach { (month, journals) ->
                        item {
                            Text(
                                text = month,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                textAlign = TextAlign.End
                            )
                        }
                        itemsIndexed(items = journals) { index, journal ->
                            ItemCard(
                                title = journal.date,
                                emotion = journal.emotion,
                                isSelected = itemSelected == index,
                                onClick = { itemSelected = index },
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}