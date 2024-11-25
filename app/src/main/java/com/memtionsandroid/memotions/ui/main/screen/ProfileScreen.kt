package com.memtionsandroid.memotions.ui.main.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.components.main.Journals
import com.memtionsandroid.memotions.ui.components.profile.ProfileTopBar
import com.memtionsandroid.memotions.ui.components.starred.StarredTopBar

@Composable
fun ProfileScreen() {
    Scaffold (
        topBar = {
            ProfileTopBar()
        },
        content = { innerPadding ->
        }
    )
}