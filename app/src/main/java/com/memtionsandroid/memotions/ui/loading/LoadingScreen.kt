package com.memtionsandroid.memotions.ui.loading

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.components.auth.Constant.gradientBackground
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_title_app),
            contentDescription = "Loading",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)

        )
    }
}

@Preview
@Composable
private fun PrecLoadingScreem() {
    MemotionsTheme {
        LoadingScreen()
    }
}