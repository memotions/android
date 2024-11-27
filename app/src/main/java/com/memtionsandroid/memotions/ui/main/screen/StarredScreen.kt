package com.memtionsandroid.memotions.ui.main.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.components.home.HomeTopBar
import com.memtionsandroid.memotions.ui.components.main.Journal
import com.memtionsandroid.memotions.ui.components.main.JournalCard
import com.memtionsandroid.memotions.ui.components.main.Journals
import com.memtionsandroid.memotions.ui.components.starred.StarredTopBar


val starredJournalList = listOf(
    Journal(
        "Sangat bahagia karena mendapatkan hadiah besar",
        "Hari ini saya merasa sangat beruntung karena mendapatkan hadiah yang saya inginkan sejak lama. Semoga terus membawa keberuntungan.",
        "Date 2",
        listOf("Liburan", "Hadiah"),
        true
    ),
    Journal(
        "Pagi yang cerah dan menyenangkan",
        "Cuaca hari ini sangat cerah dan menyenangkan. Saya merasa sangat berenergi untuk memulai hari ini.",
        "Date 4",
        listOf("Sekolah", "Olahraga"),
        true
    ),
)

@Composable
fun StarredScreen() {
    Scaffold (
        topBar = {
            StarredTopBar()
        },
        content = { innerPadding ->
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)) {
                Journals(starredJournalList)
            }
        }
    )
}
