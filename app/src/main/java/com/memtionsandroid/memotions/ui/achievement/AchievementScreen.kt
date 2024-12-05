package com.memtionsandroid.memotions.ui.achievement

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.memtionsandroid.memotions.ui.components.achievement.AchievementTopBar
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun AchivementScreen() {
    val customColors = MaterialTheme.customColors
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier,
        topBar = {
            AchievementTopBar(
                currentAchievement = 0,
                totalAchievement = 0,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        },
        content = { innerPadding ->
            innerPadding.calculateTopPadding()
        }
    )
}