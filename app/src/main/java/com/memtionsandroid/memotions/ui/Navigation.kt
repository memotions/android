package com.memtionsandroid.memotions.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.memtionsandroid.memotions.ui.addjournal.AddJournalScreen
import com.memtionsandroid.memotions.ui.login.LoginScreen
import com.memtionsandroid.memotions.ui.main.MainScreen
import com.memtionsandroid.memotions.ui.onboarding.OnBoardingScreen
import com.memtionsandroid.memotions.ui.register.RegisterScreen
import com.memtionsandroid.memotions.ui.setting.SettingScreen
import com.memtionsandroid.memotions.ui.viewjournal.ViewJournalScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoutes.ADD_JOURNAL) {
        composable(NavigationRoutes.LOGIN) {
            LoginScreen()
        }
        composable(NavigationRoutes.REGISTER) {
            RegisterScreen()
        }
        composable(NavigationRoutes.MAIN) {
            MainScreen()
        }
        composable(NavigationRoutes.ADD_JOURNAL) {
            AddJournalScreen()
        }
        composable(NavigationRoutes.VIEW_JOURNAL) {
            ViewJournalScreen()
        }
        composable(NavigationRoutes.ONBOARDING) {
            OnBoardingScreen()
        }
//        composable(NavigationRoutes.ACHIEVEMENT) {
//            AchievementScreen()
//        }
        composable(NavigationRoutes.SETTING) {
            SettingScreen()
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