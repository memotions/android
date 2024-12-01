package com.memtionsandroid.memotions.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.memtionsandroid.memotions.ui.components.auth.AuthCard
import com.memtionsandroid.memotions.ui.components.auth.Constant.gradientBackground
import com.memtionsandroid.memotions.ui.components.auth.GoogleButton
import com.memtionsandroid.memotions.ui.components.auth.TitleApp

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = hiltViewModel()){
    val emailState = remember { mutableStateOf(TextFieldValue("")) }
    val passwordState = remember { mutableStateOf(TextFieldValue("")) }
    val confirmPasswordState = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .navigationBarsPadding()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleApp()
        Spacer(modifier = Modifier.height(22.dp))
        AuthCard(
            title = "Selamat Datang",
            onLogin = {},
            onRegister = {},
            inRegister = true,
            emailState = emailState,
            passwordState = passwordState,
            confirmPasswordState = confirmPasswordState
        )
        Spacer(modifier = Modifier.height(20.dp))
        GoogleButton(buttonText = "Masuk dengan Google", onClick = {})
    }
}