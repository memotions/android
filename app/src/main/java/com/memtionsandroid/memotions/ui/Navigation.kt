package com.memtionsandroid.memotions.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.memtionsandroid.memotions.ui.achievement.AchievementScreen
import com.memtionsandroid.memotions.ui.addjournal.AddJournalScreen
import com.memtionsandroid.memotions.ui.loading.LoadingScreen
import com.memtionsandroid.memotions.ui.login.LoginScreen
import com.memtionsandroid.memotions.ui.login.LoginViewModel
import com.memtionsandroid.memotions.ui.main.MainScreen
import com.memtionsandroid.memotions.ui.onboarding.OnBoardingScreen
import com.memtionsandroid.memotions.ui.register.RegisterScreen
import com.memtionsandroid.memotions.ui.setting.SettingScreen
import com.memtionsandroid.memotions.ui.viewjournal.ViewJournalScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation(viewModel: LoginViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val isFirstLaunch = viewModel.isFirstLaunch.collectAsStateWithLifecycle()
    val authToken = viewModel.authToken.collectAsStateWithLifecycle()

    if (isFirstLaunch.value == null || authToken.value == null) {
        LoadingScreen()
    } else {
        val startRoute = when {
            isFirstLaunch.value == true -> NavigationRoutes.ONBOARDING
            authToken.value.isNullOrEmpty() -> NavigationRoutes.LOGIN
            else -> NavigationRoutes.MAIN
        }

        NavHost(navController = navController, startDestination = startRoute) {
            composable(NavigationRoutes.LOGIN) {
                LoginScreen(navController)
            }
            composable(NavigationRoutes.REGISTER) {
                RegisterScreen(navController)
            }
            composable(NavigationRoutes.MAIN) {
                MainScreen(navController)
            }
            composable(
                "${NavigationRoutes.ADD_JOURNAL}/{journalId}",
                arguments = listOf(navArgument("journalId") { type = NavType.StringType })
            ) { backStackEntry ->
                val journalId = backStackEntry.arguments?.getString("journalId")
                AddJournalScreen(navController, journalId.toString())
            }
            composable(
                "${NavigationRoutes.VIEW_JOURNAL}/{journalId}",
                arguments = listOf(navArgument("journalId") { type = NavType.StringType })
            ) { backStackEntry ->
                val journalId = backStackEntry.arguments?.getString("journalId")
                ViewJournalScreen(navController, journalId.toString())
            }
            composable(NavigationRoutes.ONBOARDING) {
                OnBoardingScreen(navController)
            }
            composable(NavigationRoutes.ACHIEVEMENT) {
                AchievementScreen(navController)
            }
            composable(NavigationRoutes.SETTING) {
                SettingScreen(navController)
            }
        }
    }
}

object NavigationRoutes {
    const val MAIN = "main"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val ADD_JOURNAL = "add_journal"
    const val VIEW_JOURNAL = "view_journal"
    const val ONBOARDING = "onboarding"
    const val ACHIEVEMENT = "achievement"
    const val SETTING = "setting"
}