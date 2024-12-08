package com.memtionsandroid.memotions.ui.main.screen.starred

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.ui.components.home.EmptyState
import com.memtionsandroid.memotions.ui.components.main.Journals
import com.memtionsandroid.memotions.ui.components.starred.StarredTopBar
import com.memtionsandroid.memotions.ui.main.MainViewModel
import com.memtionsandroid.memotions.ui.main.screen.home.HomeViewModel
import com.memtionsandroid.memotions.ui.theme.customColors
import com.memtionsandroid.memotions.utils.DataResult


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StarredScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    starredViewModel: StarredViewModel = hiltViewModel()
) {
    val customColors = MaterialTheme.customColors

    val journalsState by mainViewModel.journals.collectAsStateWithLifecycle()
    val currentTags by mainViewModel.currentTags.collectAsStateWithLifecycle()

    val filteredJournal by starredViewModel.filteredJournals.collectAsState()
    val filterCriteria by starredViewModel.filterCriteria.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val state = rememberPullToRefreshState()
    val onRefresh: () -> Unit = {
        mainViewModel.getJournals()
        mainViewModel.getCurrentTags()
    }

    LaunchedEffect(journalsState) {
        when (journalsState) {
            is DataResult.Error -> {
                val errorMessage =
                    (journalsState as DataResult.Error).error.getContentIfNotHandled()
                isRefreshing = false
                snackbarHostState.showSnackbar(
                    message = errorMessage!!,
                    actionLabel = "Error",
                    duration = SnackbarDuration.Indefinite
                )
            }

            DataResult.Idle -> {}
            DataResult.Loading -> {
                isRefreshing = true
            }

            is DataResult.Success -> {
                isRefreshing = false
                val journals = (journalsState as DataResult.Success).data
                starredViewModel.setJournals(journals)
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            StarredTopBar(
                filterCount = filterCriteria.countFilter(),
                searchText = filterCriteria.name,
                tags = currentTags,
                activeTags = filterCriteria.tags,
                onTagAdded = { tag ->
                    starredViewModel.setFilterCriteria(
                        filterCriteria.addTag(tag)
                    )
                },
                onTagRemoved = { tag ->
                    starredViewModel.setFilterCriteria(
                        filterCriteria.removeTag(tag)
                    )
                },
                onSearchValueChange = { value ->
                    starredViewModel.setFilterCriteria(filterCriteria.copy(name = value))
                })
        },
        content = { innerPadding ->
            PullToRefreshBox(
                modifier = Modifier.fillMaxSize()
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
