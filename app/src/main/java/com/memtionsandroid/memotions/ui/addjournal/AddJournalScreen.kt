package com.memtionsandroid.memotions.ui.addjournal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.memtionsandroid.memotions.ui.components.journal.AppBar
import com.memtionsandroid.memotions.ui.components.journal.BottomBar
import com.memtionsandroid.memotions.ui.components.journal.FormSection
import com.memtionsandroid.memotions.ui.components.journal.rememberImeState
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme

@Composable
fun AddJournalScreen(viewModel: AddJournalViewModel = hiltViewModel()) {
    AddJournalScreenContent()
}

@Composable
internal fun AddJournalScreenContent(modifier: Modifier = Modifier) {
    val titleState = remember { mutableStateOf(TextFieldValue()) }
    val journalState = remember { mutableStateOf(TextFieldValue()) }
    val starredState = remember { mutableStateOf(false) }

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar(title = "Tambah Jurnal", inView = false, onBack = {}, onAction = {}, starredState = starredState)
        FormSection(dateInfo = "Today", titleState = titleState, journalState = journalState, tag = "#Kuliah", inView = false)
        Spacer(modifier = Modifier.weight(1f))
        BottomBar(
            onTagClick = {},
            onClockClick = {},
            onSaveClick = {},
            starredState = starredState
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun DefaultPreview() {
    MemotionsTheme {
        AddJournalScreenContent()
    }
}