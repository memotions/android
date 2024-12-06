package com.memtionsandroid.memotions.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.memtionsandroid.memotions.ui.achievement.AchivementScreen
import com.memtionsandroid.memotions.ui.main.MainScreen
import com.memtionsandroid.memotions.ui.main.screen.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
//            HomeScreen(modifier = Modifier.padding(16.dp))
            MainScreen(navController)
        }

        composable("achievement") {
            AchivementScreen(navController)
        }

//        composable("home") { HomeScreen() }
//        composable("starred") { StarredScreen() }
//        composable("statistic") { StatisticScreen() }
//        composable("Profile") { ProfileScreen() }
    }
}