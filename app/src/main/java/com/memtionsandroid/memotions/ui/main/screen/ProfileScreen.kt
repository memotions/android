package com.memtionsandroid.memotions.ui.main.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.components.profile.JournalInfo
import com.memtionsandroid.memotions.ui.components.profile.ProfileTopBar

@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            ProfileTopBar()
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                JournalInfo(modifier = Modifier.padding(top = 8.dp))
            }
        }
    )
}