package com.memtionsandroid.memotions.ui.addjournal

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.memtionsandroid.memotions.ui.components.journal.AppBar
import com.memtionsandroid.memotions.ui.components.journal.BottomBar
import com.memtionsandroid.memotions.ui.components.journal.FormSection
import com.memtionsandroid.memotions.ui.components.journal.rememberImeState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.components.home.SearchTagModal
import com.memtionsandroid.memotions.ui.components.home.Tag
import com.memtionsandroid.memotions.ui.components.journal.ConfirmSaveDialog
import com.memtionsandroid.memotions.ui.components.journal.DateTimeAskDialog
import com.memtionsandroid.memotions.ui.components.journal.SaveAsDraftDialog
import com.memtionsandroid.memotions.ui.components.journal.TimePickerDialog
import com.memtionsandroid.memotions.ui.theme.customColors
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJournalScreen(
    navController: NavHostController,
    journalId: String,
    viewModel: AddJournalViewModel = hiltViewModel()
) {
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    var showAskDateTimeDialog by remember { mutableStateOf(false) }
    var showTimeDial by remember { mutableStateOf(true) }
    var showDateDialog by remember { mutableStateOf(false) }
    var showTimeDialog by remember { mutableStateOf(false) }
    var showTagsDialog by remember { mutableStateOf(false) }
    var showSaveAsDraftDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val toggleTimeIcon = if (showTimeDial) {
        painterResource(id = R.drawable.ic_keyboard)
    } else {
        painterResource(id = R.drawable.ic_access_time)
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    val timePickerState = rememberTimePickerState(
        is24Hour = true
    )

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val customColors = MaterialTheme.customColors
    val title = if (journalId == "add") "Tambah Jurnal" else "Edit Jurnal"

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    LaunchedEffect(Unit) {
        if (journalId != "add") {
            viewModel.fetchDraftJournal(journalId.toInt())
        }
        viewModel.fetchUserTags()
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

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AppBar(
                title = title,
                inView = false,
                onBack = {
                    if ((viewModel.titleValue.isNotEmpty() && viewModel.contentValue.isNotEmpty()) || journalId != "add") {
                        showSaveAsDraftDialog = true
                    } else {
                        navController.popBackStack()
                    }
                },
                onAction = {},
                starredValue = null,
                onStarredChange = null
            )
        },
        bottomBar = {
            BottomBar(
                onTagClick = { showTagsDialog = true },
                onClockClick = { showAskDateTimeDialog = true },
                onSaveClick = {
                    focusManager.clearFocus()
                    showConfirmDialog = true
                },
                starredValue = viewModel.starredValue,
                onStarredChange = viewModel::setStarred
            )
        }
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
                onTitleChange = viewModel::setTitle,
                contentValue = viewModel.contentValue,
                onContentChange = viewModel::setContent,
                tags = viewModel.tagsValue.map { Tag(name = it) },
                onTagRemove = { index ->
                    viewModel.updateTagsValue(
                        viewModel.tagsValue.toMutableList().apply { removeAt(index) })
                },
                inView = false
            )
        }

        if (showTagsDialog) {
            SearchTagModal(
                tags = viewModel.userTags,
                onDismissRequest = { showTagsDialog = false },
                onEmptyTagContent = { searchQuery ->
                    if (searchQuery.isNotEmpty()) {
                        TextButton(
                            onClick = {
                                viewModel.addTagsValue(searchQuery)
                                showTagsDialog = false
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            border = BorderStroke(width = 1.dp, customColors.onBackgroundColor)
                        ) {
                            Text(
                                text = "Buat Tag \"$searchQuery\"",
                                style = MaterialTheme.typography.bodyMedium,
                                color = customColors.onBackgroundColor,
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.height(22.dp))
                        Text(
                            text = "Ketik untuk membuat Tag",
                            style = MaterialTheme.typography.bodyMedium,
                            color = customColors.onSecondBackgroundColor,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                onItemClicked = { selectedTagName ->
                    if (viewModel.tagsValue.none { it == selectedTagName }) {
                        viewModel.addTagsValue(selectedTagName)
                        showTagsDialog = false
                    }
                },
                onEmptyTagInputHint = "Buat Tag",
                onEmptyTagInputIcon = painterResource(id = R.drawable.ic_add_tag)
            )
        }

        if (showAskDateTimeDialog) {
            DateTimeAskDialog(
                onDismiss = { showAskDateTimeDialog = false },
                onSetDate = {
                    showDateDialog = true
                    showAskDateTimeDialog = false
                },
                onSetTime = {
                    showTimeDialog = true
                    showAskDateTimeDialog = false
                }
            )
        }

        BackHandler(enabled = true) {
            if (viewModel.titleValue.isNotEmpty() && viewModel.contentValue.isNotEmpty()) {
                showSaveAsDraftDialog = true
            } else {
                navController.popBackStack()
            }
        }

        if (showSaveAsDraftDialog) {
            SaveAsDraftDialog(
                onDismiss = { showSaveAsDraftDialog = false },
                onSaveDraft = {
                    showSaveAsDraftDialog = false
                    if (journalId == "add") {
                        viewModel.saveDraftJournal(navController)
                    } else {
                        viewModel.saveDraftAgainJournal(journalId.toInt(), navController)
                    }
                },
                onDelete = {
                    showSaveAsDraftDialog = false
                    if (journalId == "add") {
                        navController.popBackStack()
                    } else {
                        viewModel.deleteDraftJournal(journalId.toInt(), navController)
                    }
                }
            )
        }

        if (showDateDialog) {
            DatePickerDialog(
                onDismissRequest = { showDateDialog = false },
                colors = DatePickerDefaults.colors(customColors.backgroundColor),
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val selectedDate = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()

                                viewModel.setDatetime(
                                    LocalDateTime.of(
                                        selectedDate,
                                        viewModel.datetimeValue.toLocalTime()
                                    )
                                )
                            }
                            showDateDialog = false
                        }
                    ) {
                        Text("Selesai")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDateDialog = false }
                    ) {
                        Text("Batal")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        containerColor = customColors.backgroundColor,
                    )
                )
            }
        }

        if (showTimeDialog) {
            TimePickerDialog(
                onDismiss = { showTimeDialog = false },
                onConfirm = {
                    viewModel.setDatetime(
                        LocalDateTime.of(
                            viewModel.datetimeValue.toLocalDate(),
                            LocalTime.of(timePickerState.hour, timePickerState.minute)
                        )
                    )
                    showTimeDialog = false
                },
                toggle = {
                    IconButton(onClick = { showTimeDial = !showTimeDial }) {
                        Icon(
                            painter = toggleTimeIcon,
                            contentDescription = "Time picker type toggle"
                        )
                    }
                },
            ) {
                if (showTimeDial) {
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            containerColor = customColors.backgroundColor,
                            timeSelectorSelectedContainerColor = customColors.onBackgroundColor.copy(
                                alpha = 0.3f
                            ),
                            timeSelectorSelectedContentColor = customColors.onBackgroundColor
                        )
                    )
                } else {
                    TimeInput(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            containerColor = customColors.backgroundColor,
                            timeSelectorSelectedContainerColor = customColors.onBackgroundColor.copy(
                                alpha = 0.3f
                            ),
                            timeSelectorSelectedContentColor = customColors.onBackgroundColor
                        )
                    )
                }
            }
        }

        if (showConfirmDialog) {
            ConfirmSaveDialog(
                onDismiss = { showConfirmDialog = false },
                onConfirmSave = {
                    showConfirmDialog = false
                    if (journalId == "add") {
                        viewModel.saveJournal(navController)
                    } else {
                        viewModel.updateJournal(journalId.toInt(), navController)
                    }
                }
            )
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
}