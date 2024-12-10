package com.memtionsandroid.memotions.ui.main.screen.statistic

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memtionsandroid.memotions.ui.components.home.EmptyState
import com.memtionsandroid.memotions.ui.components.statistic.ItemCard
import com.memtionsandroid.memotions.ui.components.statistic.StatisticTopBar
import com.memtionsandroid.memotions.ui.main.MainViewModel
import com.memtionsandroid.memotions.ui.theme.customColors
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.EmotionImageSource
import com.memtionsandroid.memotions.utils.formatMonthYear

const val MODE_HARI = "Hari"
const val MODE_BULAN = "Bulan"
const val MODE_MINGGU = "Minggu"
const val MODE_SEMUA = "Semua"


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    statisticViewModel: StatisticViewModel = hiltViewModel(),
    showNavBar: () -> Unit
) {
    val customColors = MaterialTheme.customColors

    var itemSelected by remember { mutableStateOf("") }
    var modeSelected by remember { mutableStateOf(MODE_SEMUA) }
    var emotionsSelected by remember { mutableStateOf(listOf<String>()) }

    val journalsState by mainViewModel.journals.collectAsStateWithLifecycle()

    val journals = statisticViewModel.journals.collectAsStateWithLifecycle()
    val groupedJournalListDate =
        statisticViewModel.groupedJournalListDate.collectAsStateWithLifecycle()
    val groupedJournalListWeek =
        statisticViewModel.groupedJournalListWeek.collectAsStateWithLifecycle()
    val groupedJournalListMonth =
        statisticViewModel.groupedJournalListMonth.collectAsStateWithLifecycle()

    var isRefreshing by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val state = rememberPullToRefreshState()

    val onRefresh: () -> Unit = {
        mainViewModel.getJournals()
    }

    LaunchedEffect(journalsState) {
        when (journalsState) {
            is DataResult.Error -> {
                val errorMessage =
                    (journalsState as DataResult.Error).error.getContentIfNotHandled()
                isRefreshing = false
                snackbarHostState.showSnackbar(
                    message = errorMessage!!,
                )
            }

            DataResult.Idle -> {}
            DataResult.Loading -> {
                isRefreshing = true
            }

            is DataResult.Success -> {
                isRefreshing = false
                val rawJournals = (journalsState as DataResult.Success).data
                statisticViewModel.setJournals(rawJournals)
            }
        }
    }




    if (modeSelected == MODE_SEMUA) {
        emotionsSelected = journals.value.flatMap { journal ->
            journal.emotionAnalysis?.map {
                it.emotion
            } ?: emptyList()
        }
        showNavBar()
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
            PullToRefreshBox(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                state = state,
                onRefresh = onRefresh,
                isRefreshing = isRefreshing,
            ) {
                Crossfade(targetState = modeSelected) { currentMode ->
                    if (modeSelected != MODE_SEMUA) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            val journalsMode = when (currentMode) {
                                MODE_HARI -> groupedJournalListDate
                                MODE_BULAN -> groupedJournalListMonth
                                MODE_MINGGU -> groupedJournalListWeek
                                else -> {
                                    null
                                }
                            }
                            val lastKey = journalsMode?.value?.keys?.lastOrNull() ?: ""
                            if (journalsMode?.value?.isEmpty() == true) {
                                EmptyState(
                                    title = "Tidak ada Emosi",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                                if (journalsMode != null) {
                                    journalsMode.value.forEach { (head, journals) ->
                                        val bottomPadding = if (head == lastKey) 80.dp else 8.dp
                                        item {
                                            Text(
                                                text = head.formatMonthYear(),
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
                                                            emotions = journals.flatMap { journal ->
                                                                journal.emotionAnalysis?.map {
                                                                    it.emotion
                                                                } ?: emptyList()
                                                            },
                                                            isSelected = itemSelected == subHead,
                                                            onClick = {
                                                                emotionsSelected =
                                                                    journals.flatMap { journal ->
                                                                        journal.emotionAnalysis?.map {
                                                                            it.emotion
                                                                        } ?: emptyList()
                                                                    }
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
                }
                AnimatedVisibility(
                    visible = modeSelected == MODE_SEMUA
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 24.dp)
                            .padding(horizontal = 24.dp)
                            .verticalScroll(
                                rememberScrollState()
                            ),
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
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        append("Saat ini kamu memilih mode statistik")
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
                        Surface(
                            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = customColors.backgroundColor,
                            shadowElevation = 4.dp,
                            tonalElevation = 8.dp,
                        ) {
                            Column (
                                modifier = Modifier.padding(16.dp)
                            ){
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                ) {
                                    emotionsSelected.forEach { emotion ->
                                        Image(
                                            painter = EmotionImageSource(emotion),
                                            contentDescription = "Emotion Image",
                                            modifier = Modifier.size(37.dp)
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    )
}