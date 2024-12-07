package com.memtionsandroid.memotions.ui.main.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.data.local.entity.EmotionAnalysis
import com.memtionsandroid.memotions.data.local.entity.Journal
import com.memtionsandroid.memotions.ui.NavigationRoutes
import com.memtionsandroid.memotions.ui.components.home.HomeTopBar
import com.memtionsandroid.memotions.ui.components.main.Journals
import com.memtionsandroid.memotions.ui.theme.customColors
import com.memtionsandroid.memotions.utils.DataResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val journalsState by viewModel.journals.collectAsStateWithLifecycle()
    var journals by remember { mutableStateOf<List<Journal>>(emptyList()) }
    var isRefreshing by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }


    val state = rememberPullToRefreshState()
    val onRefresh: () -> Unit = {
        viewModel.getJournals()
    }

    LaunchedEffect(Unit) {
        viewModel.getJournals()
    }

    LaunchedEffect(journalsState) {
        when (journalsState) {
            is DataResult.Error -> {
                val errorMessage = (journalsState as DataResult.Error).error.getContentIfNotHandled()
                isRefreshing = false
                snackbarHostState.showSnackbar(errorMessage!!)
            }

            DataResult.Idle -> {}
            DataResult.Loading -> {
                isRefreshing = true
            }

            is DataResult.Success -> {
                isRefreshing = false
                journals = (journalsState as DataResult.Success<List<Journal>>).data
            }
        }
    }


    val customColors = MaterialTheme.customColors
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(NavigationRoutes.ADD_JOURNAL)
                },
                containerColor = customColors.barColor,
                contentColor = customColors.onBarColor,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pen),
                    contentDescription = "Add"
                )
            }
        },
        topBar = {
            HomeTopBar()
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            )
            {
                PullToRefreshBox(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    onRefresh = onRefresh,
                    isRefreshing = isRefreshing,
                ) {
                    Journals(journals)
                }
            }
        }
    )
}

val dummyEmotionAnalysis = listOf(
    EmotionAnalysis(emotion = "happy", confidence = 0.85f),
    EmotionAnalysis(emotion = "neutral", confidence = 0.65f)
)

val dummyJournal = Journal(
    id = 1,
    userId = 123,
    title = "A Day in the Life",
    content = "Today was a good day. I learned new things and felt happy throughout.",
    datetime = "2024-12-07T10:30:00",
    createdAt = "2024-12-07T09:00:00",
    status = "DRAFT",
    deleted = false,
    starred = true,
    feedback = "Keep up the good work!",
    tags = listOf("personal", "motivation", "learning"),
    emotionAnalysis = dummyEmotionAnalysis
)

// Membuat list dengan beberapa salinan dummyJournal yang memiliki ID berbeda
val dummyList = listOf(
    dummyJournal.copy(id = 1),
    dummyJournal.copy(id = 2, status = "PUBLISHED"),
    dummyJournal.copy(id = 3)
)






