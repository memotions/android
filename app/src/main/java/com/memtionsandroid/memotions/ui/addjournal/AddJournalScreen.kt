package com.memtionsandroid.memotions.ui.addjournal

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.components.journal.DateTimeAskDialog
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
fun AddJournalScreen(viewModel: AddJournalViewModel = hiltViewModel()) {
    val titleState = remember { mutableStateOf(TextFieldValue()) }
    val journalState = remember { mutableStateOf(TextFieldValue()) }
    val starredState = remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }
    var showTimeDialog by remember { mutableStateOf(false) }
    var showTimeDial by remember { mutableStateOf(true) }
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .navigationBarsPadding()
            .statusBarsPadding()
            .background(customColors.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar(
            title = "Tambah Jurnal",
            inView = false,
            onBack = {},
            onAction = {},
            starredState = starredState
        )
        FormSection(
            dateInfo = selectedDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")),
            titleState = titleState,
            journalState = journalState,
            tag = "#Kuliah",
            inView = false
        )
        Spacer(modifier = Modifier.weight(1f))
        BottomBar(
            onTagClick = {},
            onClockClick = { showDialog = true },
            onSaveClick = {},
            starredState = starredState
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
                        timeSelectorSelectedContainerColor = customColors.onBackgroundColor.copy(alpha = 0.3f),
                        timeSelectorSelectedContentColor = customColors.onBackgroundColor
                    )
                )
            } else {
                TimeInput(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        containerColor = customColors.backgroundColor,
                        timeSelectorSelectedContainerColor = customColors.onBackgroundColor.copy(alpha = 0.3f),
                        timeSelectorSelectedContentColor = customColors.onBackgroundColor
                    )
                )
            }
        }
    }
}