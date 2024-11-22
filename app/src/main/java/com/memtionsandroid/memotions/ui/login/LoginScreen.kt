package com.memtionsandroid.memotions.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.memtionsandroid.memotions.ui.components.auth.AuthCard
import com.memtionsandroid.memotions.ui.components.auth.Constant.gradientBackground
import com.memtionsandroid.memotions.ui.components.auth.GoogleButton
import com.memtionsandroid.memotions.ui.components.auth.TitleApp
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    LoginScreenContent()
}

@Composable
internal fun LoginScreenContent(modifier: Modifier = Modifier) {

    val emailState = remember { mutableStateOf(TextFieldValue("")) }
    val passwordState = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBackground),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleApp()
        Spacer(modifier = Modifier.height(22.dp))
        AuthCard(
            title = "Selamat Datang Kembali",
            onLogin = {},
            onRegister = {},
            inRegister = false,
            emailState = emailState,
            passwordState = passwordState
        )
        Spacer(modifier = Modifier.height(20.dp))
        GoogleButton(buttonText = "Masuk dengan Google", onClick = {})
    }
}

@Composable
@Preview(showBackground = true)
private fun DefaultPreview() {
    MemotionsTheme {
        LoginScreenContent()
    }
}