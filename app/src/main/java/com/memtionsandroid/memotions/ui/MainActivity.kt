package com.memtionsandroid.memotions.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memtionsandroid.memotions.ui.setting.SettingViewModel
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MemotionsApp(settingViewModel = settingViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MemotionsApp(settingViewModel: SettingViewModel) {
    val isDarkMode by settingViewModel.darkModeValue.collectAsStateWithLifecycle()

    MemotionsTheme(darkTheme = isDarkMode) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainNavigation()
        }
    }
}