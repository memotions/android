package com.memtionsandroid.memotions.ui.main.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.NavigationRoutes
import com.memtionsandroid.memotions.ui.components.home.EmptyState
import com.memtionsandroid.memotions.ui.components.home.HomeTopBar
import com.memtionsandroid.memotions.ui.components.main.Journals
import com.memtionsandroid.memotions.ui.main.MainViewModel
import com.memtionsandroid.memotions.ui.theme.customColors
import com.memtionsandroid.memotions.utils.DataResult
import com.memtionsandroid.memotions.utils.toNickname

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val customColors = MaterialTheme.customColors

    val journalsState by mainViewModel.journals.collectAsStateWithLifecycle()
    val currentTags by mainViewModel.currentTags.collectAsStateWithLifecycle()
    val username by mainViewModel.username.collectAsState()

    val filteredJournal by homeViewModel.filteredJournals.collectAsState()
    val filterCriteria by homeViewModel.filterCriteria.collectAsState()
    val currentLevel by homeViewModel.currentLevel.collectAsState()
    val currentStreak by homeViewModel.currentStreak.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val state = rememberPullToRefreshState()

    val onRefresh: () -> Unit = {
        mainViewModel.getJournals()
        mainViewModel.getCurrentTags()
        homeViewModel.getUserStatistics()
    }

    LaunchedEffect(Unit) {
        homeViewModel.getUserStatistics()
    }

    LaunchedEffect(journalsState) {
        when (journalsState) {
            is DataResult.Error -> {
                val errorMessage =
                    (journalsState as DataResult.Error).error.getContentIfNotHandled()
                isRefreshing = false
                snackbarHostState.showSnackbar(
                    message = errorMessage!!,
                    duration = SnackbarDuration.Short
                )
            }

            DataResult.Idle -> {}
            DataResult.Loading -> {
                isRefreshing = true
            }

            is DataResult.Success -> {
                isRefreshing = false
                val journals = (journalsState as DataResult.Success).data
                homeViewModel.setJournals(journals)
            }
        }
    }

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
            HomeTopBar(
                username = username?.toNickname() ?: "Guest",
                streak = currentStreak,
                currentEXP = currentLevel.totalPoints,
                nextLevelEXP = currentLevel.pointsRequired,
                level = currentLevel.currentLevel,
                filterCount = filterCriteria.countFilter(),
                searchText = filterCriteria.name,
                tags = currentTags,
                activeTags = filterCriteria.tags,
                onTagAdded = { tag ->
                    homeViewModel.setFilterCriteria(
                        filterCriteria.addTag(tag)
                    )
                },
                onTagRemoved = { tag ->
                    homeViewModel.setFilterCriteria(
                        filterCriteria.removeTag(tag)
                    )
                },
                onSearchValueChange = { value ->
                    homeViewModel.setFilterCriteria(filterCriteria.copy(name = value))
                }
            )
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
                if (filteredJournal.isEmpty()) {
                    EmptyState(
                        modifier = Modifier.align(Alignment.Center),
                        title = "Tidak ada Jurnal"
                    )
                }
                Journals(filteredJournal)
            }
        }
    )
}






