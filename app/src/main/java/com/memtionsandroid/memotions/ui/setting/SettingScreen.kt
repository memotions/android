package com.memtionsandroid.memotions.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.ui.components.setting.AppBar
import com.memtionsandroid.memotions.ui.components.setting.SettingSection
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun SettingScreen(navController: NavHostController, viewModel: SettingViewModel = hiltViewModel()) {
    val customColors = MaterialTheme.customColors
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val darkModeValue by viewModel.darkModeValue.collectAsStateWithLifecycle()
    val notificationValue by viewModel.notificationValue.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        topBar = {
            AppBar(title = "Pengaturan", onBack = {
                navController.popBackStack()
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(customColors.backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            SettingSection(
                title = "Pengaturan Aplikasi",
                checkedDarkModeValue = darkModeValue,
                onChangeDarkMode = viewModel::setDarkMode,
                checkedNotificationValue = notificationValue,
                onChangeNotification = viewModel::setNotification
            )
        }
    }
}