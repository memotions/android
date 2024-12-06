package com.memtionsandroid.memotions.ui.achievement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.components.achievement.AchievementCard
import com.memtionsandroid.memotions.ui.components.achievement.AchievementTopBar


data class Achievement(
    val tier: Int,
    val title: String,
    val image: Int
)

val achievements = listOf(
    Achievement(2, "Pejuang", R.drawable.ach_soon),
    Achievement(0, "Berbahagia", R.drawable.ach_soon),
    Achievement(1, "Pejuang", R.drawable.ach_soon),
    Achievement(1, "Achievement Title", R.drawable.ach_soon),
    Achievement(1, "Achievement Title", R.drawable.ach_soon),
    Achievement(1, "Achievement Title", R.drawable.ach_soon),
    Achievement(1, "Pejuang Mandiri", R.drawable.ach_soon),
    Achievement(3, "Selamat datang", R.drawable.ach_soon)
)


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AchivementScreen(navController: NavHostController) {

    Scaffold(
        modifier = Modifier,
        topBar = {
            AchievementTopBar(
                currentAchievement = 1,
                totalAchievement = 19,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        },
        content = { innerPadding ->
            val scrollState = rememberScrollState()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                ) {
                    achievements.forEach { achievement ->
                        AchievementCard(
                            tier = achievement.tier,
                            title = achievement.title,
                            imageSource = achievement.image
                        ) {
                            // Handle click if needed
                        }
                    }
                }
            }
        }
    )
}