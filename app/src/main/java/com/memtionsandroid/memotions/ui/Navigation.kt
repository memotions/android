package com.memtionsandroid.memotions.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.memtionsandroid.memotions.ui.main.MainScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
//            HomeScreen(modifier = Modifier.padding(16.dp))
            MainScreen()
        }
//        composable("home") { HomeScreen() }
//        composable("starred") { StarredScreen() }
//        composable("statistic") { StatisticScreen() }
//        composable("Profile") { ProfileScreen() }
    }
}