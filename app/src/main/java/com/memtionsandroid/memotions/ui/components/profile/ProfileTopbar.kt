package com.memtionsandroid.memotions.ui.components.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.components.main.SearchBar
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import com.memtionsandroid.memotions.ui.theme.customColors


@Composable
fun ProfileTopBar() {
    val customColors = MaterialTheme.customColors
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shadowElevation = 4.dp,
        tonalElevation = 8.dp,
        border = BorderStroke(0.5.dp, customColors.outlineColor),
        shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(customColors.backgroundColor)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .statusBarsPadding()
        ) {
            Column {
                PersonalInfo(user = User(name = "Liangga", yearCreated = "2024"))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
                    ) {
                    PersonalProgress()
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileTopBarPreview() {
    MemotionsTheme {
        ProfileTopBar()
    }
}

@Preview
@Composable
fun ProfileTopBarPreviewDark() {
    MemotionsTheme(darkTheme = true) {
        ProfileTopBar()
    }
}