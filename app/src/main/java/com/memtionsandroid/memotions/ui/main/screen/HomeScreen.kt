package com.memtionsandroid.memotions.ui.main.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.components.home.HomeTopBar
import com.memtionsandroid.memotions.ui.components.main.Journal
import com.memtionsandroid.memotions.ui.components.main.Journals
import com.memtionsandroid.memotions.ui.components.main.journalList
import com.memtionsandroid.memotions.ui.theme.customColors


@Composable
fun HomeScreen() {
    val customColors = MaterialTheme.customColors
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Tambahkan aksi saat FAB diklik
                    Log.d("FAB", "Floating Action Button Clicked!")
                },
                containerColor = customColors.barColor,
                contentColor = customColors.onBarColor,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pen), // Ganti dengan ikon FAB
                    contentDescription = "Add"
                )
            }
        },
        topBar = {
            HomeTopBar()
        },
//        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
//                    .padding(bottom = 80.dp)
            )
            {
                Journals(journalList)
            }
        }
    )
}




