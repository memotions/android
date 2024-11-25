package com.memtionsandroid.memotions.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.memtionsandroid.memotions.ui.components.setting.AppBar
import com.memtionsandroid.memotions.ui.components.setting.SettingSection
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme

@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel()) {
    SettingScreenContent()
}

@Composable
internal fun SettingScreenContent () {
    val checkedDarkMode = remember { mutableStateOf(true) }
    val checkedNotification = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar(title = "Pengaturan", onBack = {})
        Spacer(modifier = Modifier.height(20.dp))
        SettingSection(title = "Pengaturan Aplikasi", checkedDarkModeState = checkedDarkMode, checkedNotificationState = checkedNotification)
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MemotionsTheme {
        SettingScreenContent()
    }
}