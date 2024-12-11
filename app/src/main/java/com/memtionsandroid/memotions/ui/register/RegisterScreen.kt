package com.memtionsandroid.memotions.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.ui.NavigationRoutes
import com.memtionsandroid.memotions.ui.components.auth.AuthCard
import com.memtionsandroid.memotions.ui.components.auth.Constant.gradientBackground
import com.memtionsandroid.memotions.ui.components.auth.TitleApp
import com.memtionsandroid.memotions.utils.DataResult

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val registerResult = viewModel.registerResult.collectAsStateWithLifecycle()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(registerResult.value) {
        val result = registerResult.value
        if (result is DataResult.Success) {
            viewModel.setFirstLaunch(false)
            navController.navigate(NavigationRoutes.LOGIN) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        } else if (result is DataResult.Error) {
            val errorMessage = result.error.getContentIfNotHandled()
            errorMessage?.let {
                snackbarHostState.showSnackbar(
                    it,
                    duration = SnackbarDuration.Short,
                    withDismissAction = true
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        content = { innerPadding ->
            innerPadding.calculateTopPadding()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .background(gradientBackground),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleApp()
                Spacer(modifier = Modifier.height(22.dp))
                AuthCard(
                    title = "Selamat Datang",
                    onLogin = {
                        navController.navigate(NavigationRoutes.LOGIN)
                    },
                    onRegister = {
                        focusManager.clearFocus()
                        viewModel.register()
                    },
                    inRegister = true,
                    nameValue = viewModel.nameValue,
                    emailValue = viewModel.emailValue,
                    passwordValue = viewModel.passwordValue,
                    onNameChange = viewModel::setName,
                    onEmailChange = viewModel::setEmail,
                    onPasswordChange = viewModel::setPassword,
                )
            }
            if (registerResult.value is DataResult.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.Black.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .wrapContentSize(Alignment.Center)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = Color.White
                    )
                }
            }
        }
    )
}