package com.memtionsandroid.memotions.ui.viewjournal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.ui.components.home.Tag
import com.memtionsandroid.memotions.ui.components.journal.AppBar
import com.memtionsandroid.memotions.ui.components.journal.BottomSheetContent
import com.memtionsandroid.memotions.ui.components.journal.EmotionType
import com.memtionsandroid.memotions.ui.components.journal.FormSection
import com.memtionsandroid.memotions.ui.theme.customColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewJournalScreen(navController: NavHostController, viewModel: ViewJournalViewModel = hiltViewModel()) {
    val titleState = remember { mutableStateOf(TextFieldValue("Aku Ngetik Ini sambil gemetar")) }
    val journalState =
        remember { mutableStateOf(TextFieldValue("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit.")) }
    val starredState = remember { mutableStateOf(false) }
    val tags = remember { mutableStateOf(listOf(Tag("Sekolah"), Tag("Pribadi"))) }

    val scrollState = rememberScrollState()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val customColors = MaterialTheme.customColors

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 85.dp,
        sheetContainerColor = Color(0xFF292828),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            BottomSheetContent(
                title = "Hasil Analisis",
                emotionType = EmotionType.SCARED,
                confidenceScore = 0.75f,
                feedback = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque"
            )
        },
//        topBar = {
//            AppBar(
//                title = "",
//                inView = true,
//                onBack = {
//                    navController.popBackStack()
//                },
//                onAction = {},
//                starredState = starredState
//            )
//        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(customColors.backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            FormSection(
//                dateInfo = "Today",
//                titleState = titleState,
//                journalState = journalState,
//                tags = tags.value,
//                onTagRemove = {},
//                inView = true
//            )
        }
    }
}