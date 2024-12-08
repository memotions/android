package com.memtionsandroid.memotions.ui.achievement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.ui.components.achievement.AchievementCard
import com.memtionsandroid.memotions.ui.components.achievement.AchievementTopBar
import com.memtionsandroid.memotions.ui.components.home.EmptyState
import com.memtionsandroid.memotions.utils.DataResult

data class Achievement(
    val tier: Int,
    val title: String,
    val image: Int
)

//val achievements = listOf(
//    Achievement(2, "Pejuang", R.drawable.ach_soon),
//    Achievement(0, "Berbahagia", R.drawable.ach_soon),
//    Achievement(1, "Pejuang", R.drawable.ach_soon),
//    Achievement(1, "Achievement Title", R.drawable.ach_soon),
//    Achievement(1, "Achievement Title", R.drawable.ach_soon),
//    Achievement(1, "Achievement Title", R.drawable.ach_soon),
//    Achievement(1, "Pejuang Mandiri", R.drawable.ach_soon),
//    Achievement(3, "Selamat datang", R.drawable.ach_soon)
//)


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AchievementScreen(
    navController: NavHostController,
    achievementViewModel: AchievementViewModel = hiltViewModel()
) {

    val achievements by achievementViewModel.achievements.collectAsState()
    val unlockedAchievement by achievementViewModel.filteredAchievements.collectAsState()
    val totalAchievementCount by achievementViewModel.totalAchievementCount.collectAsState()
    val completedAchievementCount by achievementViewModel.completedAchievementCount.collectAsState()

    val state = rememberPullToRefreshState()
    val scrollState = rememberScrollState()
    var isRefreshing by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val onRefresh = {
        achievementViewModel.getAchievements()
    }

    LaunchedEffect(Unit) {
        achievementViewModel.getAchievements()
    }

    LaunchedEffect(achievements) {
        when (achievements) {
            is DataResult.Error -> {
                isRefreshing = false
                val errorMessage =
                    (achievements as DataResult.Error).error.getContentIfNotHandled()
                isRefreshing = false
                snackbarHostState.showSnackbar(
                    message = errorMessage!!,
                    duration = SnackbarDuration.Short,
                )
            }

            DataResult.Idle -> {}
            DataResult.Loading -> {
                isRefreshing = true
            }

            is DataResult.Success -> {
                isRefreshing = false
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AchievementTopBar(
                currentAchievement = completedAchievementCount,
                totalAchievement = totalAchievementCount,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        },
        content = { innerPadding ->
            PullToRefreshBox(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                state = state,
                onRefresh = onRefresh,
                isRefreshing = isRefreshing,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
//                        modifier = Modifier
                    ) {
                        if (unlockedAchievement.isEmpty()) {
                            EmptyState(
                                Modifier.padding(top = 64.dp),
                                title = "Pencapaian Kosong"
                            )
                        }
                        unlockedAchievement.forEach { achievement ->
                            AchievementCard(
                                tier = achievement.tier,
                                title = achievement.name,
                                imageSource = achievement.iconUrl,
                            ) {

                            }
                        }
                    }
                }
            }
        }
    )
}