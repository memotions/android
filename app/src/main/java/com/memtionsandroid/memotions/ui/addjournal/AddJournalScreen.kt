package com.memtionsandroid.memotions.ui.addjournal

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.memtionsandroid.memotions.ui.components.journal.AppBar
import com.memtionsandroid.memotions.ui.components.journal.BottomBar
import com.memtionsandroid.memotions.ui.components.journal.FormSection
import com.memtionsandroid.memotions.ui.components.journal.rememberImeState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.data.remote.response.journals.TagsItem
import com.memtionsandroid.memotions.ui.components.home.SearchTagModal
import com.memtionsandroid.memotions.ui.components.journal.DateTimeAskDialog
import com.memtionsandroid.memotions.ui.components.journal.SaveAsDraftDialog
import com.memtionsandroid.memotions.ui.components.journal.TimePickerDialog
import com.memtionsandroid.memotions.ui.components.main.TagDummy
import com.memtionsandroid.memotions.ui.components.main.tagsJurnal
import com.memtionsandroid.memotions.ui.theme.customColors
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJournalScreen(navController: NavHostController, viewModel: AddJournalViewModel = hiltViewModel()) {
    val titleState = remember { mutableStateOf(TextFieldValue()) }
    val journalState = remember { mutableStateOf(TextFieldValue()) }
    val starredState = remember { mutableStateOf(false) }
    val tags = remember { mutableStateOf(listOf<TagDummy>()) }

    var showDialog by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }
    var showTimeDialog by remember { mutableStateOf(false) }
    var showTimeDial by remember { mutableStateOf(true) }
    var showTagModal by remember { mutableStateOf(false) }
    var showSaveDraftModal by remember { mutableStateOf(false) }

    val toggleIcon = if (showTimeDial) {
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

    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val customColors = MaterialTheme.customColors

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                title = "Tambah Jurnal",
                inView = false,
                onBack = {
                    showSaveDraftModal = true
                },
                onAction = {},
                starredState = starredState
            )
        },
        bottomBar = {
            BottomBar(
                onTagClick = { showTagModal = true },
                onClockClick = { showDialog = true },
                onSaveClick = {},
                starredState = starredState
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
                dateInfo = selectedDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")),
                titleState = titleState,
                journalState = journalState,
                tags = listOf(TagsItem("K",1)),
                onTagRemove = { index ->
                    tags.value = tags.value.toMutableList().apply { removeAt(index) }
                },
                inView = false
            )
        }

        if (showTagModal) {
            SearchTagModal(
                tags = emptyList(),
                onDismissRequest = { showTagModal = false },
                onEmptyTagContent = { searchQuery ->
                    TextButton(
                        onClick = {
                            tags.value += TagDummy(searchQuery)
                            showTagModal = false
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
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                onItemClicked = { selectedTagName ->
//                    if (tags.value.none { it.name == selectedTagName }) {
//                        tags.value += TagDummy(selectedTagName)
//                        showTagModal = false
//                    }
                },
                onEmptyTagInputHint = "Buat Tag",
                onEmptyTagInputIcon = painterResource(id = R.drawable.ic_add_tag)
            )
        }

        if (showDialog) {
            DateTimeAskDialog(
                onDismiss = { showDialog = false },
                onSetDate = {
                    showDateDialog = true
                    showDialog = false
                },
                onSetTime = {
                    showTimeDialog = true
                    showDialog = false
                }
            )
        }

        BackHandler(enabled = true) {
            showSaveDraftModal = true
        }

        if (showSaveDraftModal) {
            SaveAsDraftDialog(
                onDismiss = { showSaveDraftModal = false },
                onSaveDraft = {
                    showSaveDraftModal = false
                },
                onBack = {
                    showSaveDraftModal = false
                    navController.popBackStack()
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

                                selectedDateTime = LocalDateTime.of(
                                    selectedDate,
                                    selectedDateTime.toLocalTime()
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
                    selectedDateTime = LocalDateTime.of(
                        selectedDateTime.toLocalDate(),
                        LocalTime.of(timePickerState.hour, timePickerState.minute)
                    )
                    showTimeDialog = false
                },
                toggle = {
                    IconButton(onClick = { showTimeDial = !showTimeDial }) {
                        Icon(
                            painter = toggleIcon,
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
    }
}