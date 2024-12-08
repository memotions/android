package com.memtionsandroid.memotions.ui.main.screen.profile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.data.remote.response.statistics.StatisticsResponse
import com.memtionsandroid.memotions.ui.NavigationRoutes
import com.memtionsandroid.memotions.ui.components.profile.AchievementInfo
import com.memtionsandroid.memotions.ui.components.profile.BoxContent
import com.memtionsandroid.memotions.ui.components.profile.JournalInfo
import com.memtionsandroid.memotions.ui.components.profile.ProfileTopBar
import com.memtionsandroid.memotions.ui.components.profile.TitleCardWithIcon
import com.memtionsandroid.memotions.ui.login.LoginViewModel
import com.memtionsandroid.memotions.ui.main.MainViewModel
import com.memtionsandroid.memotions.ui.theme.customColors
import com.memtionsandroid.memotions.utils.DataResult

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val customColors = MaterialTheme.customColors
    val snackbarHostState = remember { SnackbarHostState() }
    var userStatistics by remember { mutableStateOf<StatisticsResponse?>(null) }
    val username by mainViewModel.username.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.getUserStatistics()
    }

    LaunchedEffect(profileViewModel.userStatistics) {
        profileViewModel.userStatistics.collect {
            when (it) {
                is DataResult.Error -> {
                    val errorMessage =
                        it.error.getContentIfNotHandled()
                    snackbarHostState.showSnackbar(
                        message = errorMessage!!,
                        actionLabel = "Error",
                        duration = SnackbarDuration.Indefinite
                    )
                }

                DataResult.Idle -> {}
                DataResult.Loading -> {}
                is DataResult.Success -> {
                    userStatistics = it.data
                }
            }
        }
    }

    Scaffold(
        topBar = {
            ProfileTopBar(
                username = username,
                userStatistic = userStatistics
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                JournalInfo(
                    modifier = Modifier.padding(top = 8.dp),
                    userStatistic = userStatistics
                )
                AchievementInfo(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            navHostController.navigate("achievement")
                        },
                    achievementsCount = userStatistics?.data?.achievementCount?.completed
                )
                BoxContent(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            navHostController.navigate(NavigationRoutes.SETTING)
                        },
                    header = {
                        TitleCardWithIcon(
                            title = "Pengaturan"
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_setting),
                                contentDescription = "Setting Icon",
                                modifier = Modifier.size(20.dp),
                                tint = customColors.onBackgroundColor
                            )
                        }
                    },
                    content = {}
                )
                BoxContent(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            loginViewModel.logout()
                            navHostController.navigate(NavigationRoutes.LOGIN) {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                    header = {
                        TitleCardWithIcon(
                            title = "Keluar"
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_logout),
                                contentDescription = "Logout Icon",
                                modifier = Modifier.size(20.dp),
                                tint = customColors.onBackgroundColor
                            )
                        }
                    },
                    content = {}
                )
            }
        }
    )
}