package com.memtionsandroid.memotions.ui.viewjournal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.memtionsandroid.memotions.ui.components.journal.AppBar
import com.memtionsandroid.memotions.ui.components.journal.BottomSheetContent
import com.memtionsandroid.memotions.ui.components.journal.EmotionType
import com.memtionsandroid.memotions.ui.components.journal.FormSection
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme

@Composable
fun ViewJournalScreen(viewModel: ViewJournalViewModel = hiltViewModel()) {
    ViewJournalScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ViewJournalScreenContent(modifier: Modifier = Modifier) {
    val titleState = remember { mutableStateOf(TextFieldValue("Aku Ngetik Ini sambil gemetar")) }
    val journalState =
        remember { mutableStateOf(TextFieldValue("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit.")) }
    val starredState = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 50.dp,
        sheetContainerColor = Color(0xFF292828),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            BottomSheetContent(
                emotionType = EmotionType.SCARED,
                confidenceScore = 0.7f,
                feedback = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque"
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppBar(title = "", inView = true, onBack = {}, onAction = {}, starredState = starredState)
            FormSection(
                dateInfo = "Today",
                titleState = titleState,
                journalState = journalState,
                tag = "#Kuliah",
                inView = true
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DefaultPreview() {
    MemotionsTheme {
        ViewJournalScreenContent()
    }
}