package com.memtionsandroid.memotions.ui.main.screen.statistic

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.components.main.journalList
import com.memtionsandroid.memotions.ui.components.statistic.ItemCard
import com.memtionsandroid.memotions.ui.components.statistic.StatisticTopBar
import com.memtionsandroid.memotions.ui.theme.customColors
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

const val MODE_HARI = "Hari"
const val MODE_BULAN = "Bulan"
const val MODE_MINGGU = "Minggu"
const val MODE_SEMUA = "Semua"


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticScreen() {
    val customColors = MaterialTheme.customColors

    var itemSelected by remember { mutableStateOf("") }
    var modeSelected by remember { mutableStateOf(MODE_SEMUA) }
    var emotionsSelected by remember { mutableStateOf(listOf<String>()) }

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val groupedJournalListDate = journalList.groupBy { journal ->
        val localDate = LocalDate.parse(journal.date, dateFormatter)
        localDate.format(DateTimeFormatter.ofPattern("yyyy MM"))
    }.toSortedMap(reverseOrder()).mapValues { entry ->
        entry.value.groupBy { journal ->
            val localDate = LocalDate.parse(journal.date, dateFormatter)
            localDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
        }.toSortedMap(reverseOrder())
    }

    val groupedJournalListMonthWeek = journalList.groupBy { journal ->
        val localDate = LocalDate.parse(journal.date, dateFormatter)
        localDate.format(DateTimeFormatter.ofPattern("yyyy MM"))
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


    if (modeSelected == MODE_SEMUA) {
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
            Crossfade(targetState = modeSelected) { currentMode ->
                if (modeSelected != MODE_SEMUA) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding)
                    ) {
                        val journalsMode = when (currentMode) {
                            MODE_HARI -> groupedJournalListDate
                            MODE_BULAN -> groupedJournalListMonth
                            MODE_MINGGU -> groupedJournalListMonthWeek
                            else -> emptyMap()
                        }

                        val lastKey = journalsMode.keys.lastOrNull() ?: ""
                        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                            journalsMode.forEach { (head, journals) ->
                                val bottomPadding = if (head == lastKey) 80.dp else 8.dp
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
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = bottomPadding),
                                        shape = RoundedCornerShape(12.dp),
                                        color = customColors.backgroundColor,
                                        shadowElevation = 4.dp,
                                        tonalElevation = 8.dp,
                                    ) {
                                        Column {
                                            journals.forEach { (subHead, journals) ->
                                                ItemCard(
                                                    title = subHead.split(" ")[0],
                                                    emotions = journals.map { it.emotion },
                                                    isSelected = itemSelected == subHead,
                                                    onClick = {
                                                        emotionsSelected =
                                                            journals.map { it.emotion }
                                                        itemSelected = subHead
                                                    },
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = modeSelected == MODE_SEMUA
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier,
                        shape = RoundedCornerShape(12.dp),
                        color = customColors.backgroundColor,
                        shadowElevation = 4.dp,
                        tonalElevation = 8.dp,
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Selamat Datang di Statistik",
                                color = customColors.onBackgroundColor,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Memotions membantu kamu untuk melihat progress emosi kamu berdasarkan Hari, Mingguan, bahkan Bulan!",
                                color = customColors.onSecondBackgroundColor,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier,
                                textAlign = TextAlign.Start
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier.padding(top = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = customColors.backgroundColor,
                        shadowElevation = 4.dp,
                        tonalElevation = 8.dp,
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append("Saat ini kamu tidak memilih mode statistik")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(" semua. ")
                                    }
                                    append("Kamu dapat memilih ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("statistik")
                                    }
                                    append(" lebih lengkap dengan memilih ")
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("mode")
                                    }
                                },
                                color = customColors.onSecondBackgroundColor,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    )
}