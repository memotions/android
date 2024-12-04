package com.memtionsandroid.memotions.ui.main.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.memtionsandroid.memotions.ui.theme.customColors
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticScreen() {
    val customColors = MaterialTheme.customColors

    var itemSelected by remember { mutableStateOf("") }
    var modeSelected by remember { mutableStateOf("Semua") }
    var emotionsSelected by remember { mutableStateOf(listOf<String>()) }

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val groupedJournalListDate = journalList.groupBy { journal ->
        val localDate = LocalDate.parse(journal.date, dateFormatter)
        localDate.format(DateTimeFormatter.ofPattern("yyyy MM")) // Grup pertama: bulan dan tahun
    }.toSortedMap(reverseOrder()).mapValues { entry ->
        entry.value.groupBy { journal ->
            val localDate = LocalDate.parse(journal.date, dateFormatter)
            localDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) // Grup kedua: tanggal
        }.toSortedMap(reverseOrder())
    }


    val groupedJournalListMonthWeek = journalList.groupBy { journal ->
        val localDate = LocalDate.parse(journal.date, dateFormatter)
        localDate.format(DateTimeFormatter.ofPattern("yyyy MM")) // Grup pertama: bulan dan tahun
    }.toSortedMap(reverseOrder()).mapValues { entry ->
        entry.value.groupBy { journal ->
            val localDate = LocalDate.parse(journal.date, dateFormatter)
            val weekFields = WeekFields.of(Locale.getDefault())
            val weekNumber = localDate.get(weekFields.weekOfMonth())
            "Minggu-$weekNumber ${entry.key}" // Grup kedua: minggu ke-N
        }.toSortedMap(reverseOrder())
    }

    val groupedJournalListMonth = journalList.groupBy { journal ->
        val localDate = LocalDate.parse(journal.date, dateFormatter)
        localDate.format(DateTimeFormatter.ofPattern("yyyy")) // Grup pertama: bulan dan tahun
    }.mapValues { entry ->
        entry.value.groupBy { journal ->
            val localDate = LocalDate.parse(journal.date, dateFormatter)
            localDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")) // Grup kedua: tanggal
        }
    }

    val journalsMode = when (modeSelected) {
        "Hari" -> groupedJournalListDate
        "Bulan" -> groupedJournalListMonth
        "Minggu" -> groupedJournalListMonthWeek
        else -> emptyMap() // Pastikan tipe return tetap konsisten
    }

    if (modeSelected == "Semua") {
        emotionsSelected = journalList.map { it.emotion }
    }

    Scaffold(
        topBar = {
            StatisticTopBar(
                selectedMode = modeSelected,
                emotions = emotionsSelected,
                onSelectedMode = { mode ->
                    modeSelected = mode
                })
        },
        content = { innerPadding ->
            if (modeSelected != "Semua") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                ) {
                    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                        journalsMode.forEach { (head, journals) ->
                            item {
                                Text(
                                    text = head,
                                    style = MaterialTheme.typography.titleSmall,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp, bottom = 8.dp),
                                    textAlign = TextAlign.Start
                                )
                            }
                            item {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    color = customColors.backgroundColor,
                                    shadowElevation = 4.dp,
                                    tonalElevation = 8.dp,
                                ) {
                                    Column {
                                        journals.forEach { (subHead, journals) ->
                                            // Tambahkan item untuk setiap jurnal di bulan tersebut
                                            ItemCard(
                                                title = subHead.split(" ")[0],
                                                emotions = journals.map { it.emotion },
                                                isSelected = itemSelected == subHead,
                                                onClick = {
                                                    emotionsSelected = journals.map { it.emotion }
                                                    itemSelected = subHead
                                                },
                                            )
                                        }
                                    }
                                }
                            }


//                            journals.forEach { (date, journals) ->
//                                item {
//                                    ItemCard(
//                                        title = date,
//                                        emotions = journals.map { it.emotion },
//                                        isSelected = itemSelected == date,
//                                        onClick = { itemSelected = date },
//                                    )
//                                }
//                            }
//                            itemsIndexed(items = journals) { (index, journal)->
//                                ItemCard(
//                                    title = journal,
//                                    emotion = journal.emotion,
//                                    isSelected = itemSelected == index,
//                                    onClick = { itemSelected = index },
//                                    modifier = Modifier.padding(vertical = 4.dp)
//                                )
//                            }
                        }
                    }
                }
            }
        }
    )
}