package com.memtionsandroid.memotions.ui.setting

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.ui.components.setting.AppBar
import com.memtionsandroid.memotions.ui.components.setting.SettingSection
import com.memtionsandroid.memotions.ui.theme.customColors
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingScreen(navController: NavHostController, viewModel: SettingViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val customColors = MaterialTheme.customColors
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val darkModeValue by viewModel.darkModeValue.collectAsStateWithLifecycle()
    val notificationValue by viewModel.notificationValue.collectAsStateWithLifecycle()

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.setNotification(true)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        "Notifikasi harian aktif",
                        duration = SnackbarDuration.Short,
                    )
                }
            } else {
                viewModel.setNotification(false)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        "Izin notifikasi diperlukan untuk fitur ini",
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    )

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
                onChangeNotification = { newValue ->
                    if (newValue) {
                        if (Build.VERSION.SDK_INT >= 33) {
                            val isGranted = ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED

                            if (!isGranted) {
                                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                            } else {
                                viewModel.setNotification(true)
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Notifikasi harian aktif",
                                        duration = SnackbarDuration.Short,
                                    )
                                }
                            }
                        } else {
                            viewModel.setNotification(true)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    "Notifikasi harian aktif",
                                    duration = SnackbarDuration.Short,
                                )
                            }
                        }
                    } else {
                        viewModel.setNotification(false)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                "Notifikasi harian nonaktif",
                                duration = SnackbarDuration.Short,
                            )
                        }
                    }
                }
            )
        }
    }
}