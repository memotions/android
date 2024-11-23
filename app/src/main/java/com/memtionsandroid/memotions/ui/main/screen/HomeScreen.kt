package com.memtionsandroid.memotions.ui.main.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.lang.reflect.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.memtionsandroid.memotions.ui.components.main.Journal
import com.memtionsandroid.memotions.ui.components.main.JournalCard


@Composable
fun HomeScreen() {
    val journal = Journal(
        "Hari yang sangat melelahkan hingga saya ingin membunuh semua orang",
        "bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla",
        "Date",
        listOf("Sekolah", "Kerja", "Belajar")
    )
    JournalCard(journal)
}