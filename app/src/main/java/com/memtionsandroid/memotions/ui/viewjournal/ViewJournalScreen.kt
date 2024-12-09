package com.memtionsandroid.memotions.ui.viewjournal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.ui.components.home.Tag
import com.memtionsandroid.memotions.ui.components.journal.AppBar
import com.memtionsandroid.memotions.ui.components.journal.BottomSheetContent
import com.memtionsandroid.memotions.ui.components.journal.FormSection
import com.memtionsandroid.memotions.ui.theme.customColors
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewJournalScreen(
    navController: NavHostController,
    journalId: String,
    viewModel: ViewJournalViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val customColors = MaterialTheme.customColors
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchJournal(journalId.toInt())
    }

    LaunchedEffect(viewModel.errorMessages) {
        if (viewModel.errorMessages.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                viewModel.errorMessages,
                duration = SnackbarDuration.Long,
                withDismissAction = true
            )
            viewModel.errorMessages = ""
        }
    }

    BottomSheetScaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 130.dp,
        sheetContainerColor = Color(0xFF292828),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            BottomSheetContent(
                title = if (viewModel.feedbackValue != null) "Hasil Analisis" else "Sedang Menganalisis",
                emotionType = viewModel.emotionAnalysis?.emotion ?: "",
                confidenceScore = viewModel.emotionAnalysis?.confidence ?: 0f,
                feedback = viewModel.feedbackValue
            )
        },
        topBar = {
            AppBar(
                title = "",
                inView = true,
                onBack = {
                    navController.popBackStack()
                },
                starredValue = viewModel.starredValue,
                onStarredClick = {
                    if (isConnected) {
                        viewModel.toggleStarredJournal(journalId.toInt())
                    }
                }
            )
        },
        sheetSwipeEnabled = viewModel.feedbackValue != null,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(customColors.backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FormSection(
                datetime = viewModel.datetimeValue.format(DateTimeFormatter.ofPattern("HH:mm • dd MMMM yyyy")),
                titleValue = viewModel.titleValue,
                onTitleChange = {},
                contentValue = viewModel.contentValue,
                onContentChange = {},
                tags = viewModel.tagsValue.map { Tag(name = it) },
                onTagRemove = {},
                inView = true,
                isConnected = isConnected
            )
        }
    }
    if (viewModel.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.Black.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(10.dp)
                )
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = Color.White
            )
        }
    }
}