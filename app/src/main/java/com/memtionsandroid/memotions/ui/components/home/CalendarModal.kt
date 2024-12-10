package com.memtionsandroid.memotions.ui.components.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Typography
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.ui.theme.customColors
import com.memtionsandroid.memotions.utils.toformatDateFromMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarModal(
    dateRangeSelected: Pair<Long?, Long?>,
    onDismissRequest: () -> Unit,
    onDateRangeSelected: (Long?, Long?) -> Unit,
) {
    val customColors = MaterialTheme.customColors
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = dateRangeSelected.first,
        initialSelectedEndDateMillis = dateRangeSelected.second,
    )

    MaterialTheme(
        typography = Typography(
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 12.sp,  // Ukuran teks tanggal
                fontWeight = FontWeight.Bold, // Gaya teks
                textAlign = TextAlign.Start // Perataan teks
            ),
            titleLarge = MaterialTheme.typography.titleLarge.copy(
                fontSize = 12.sp,  // Ukuran teks tanggal
                fontWeight = FontWeight.Bold, // Gaya teks
                textAlign = TextAlign.Start // Perataan teks
            ),
//            titleSmall = MaterialTheme.typography.titleSmall,
            bodyLarge = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,  // Ukuran teks tanggal
                textAlign = TextAlign.Start // Perataan teks
            )
        )
    ) {

        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            colors = DatePickerDefaults.colors(
                containerColor = customColors.backgroundColor,
                titleContentColor = customColors.onBackgroundColor,
                headlineContentColor = customColors.onBackgroundColor,
            ),
            confirmButton = {
                TextButton(
                    onClick = {
                        onDateRangeSelected(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                        onDismissRequest()
                    }
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text("Batal")
                }
            }
        ) {
            DateRangePicker(
                title = {
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomDatePickerHeadline(
                            startDateMillis = dateRangePickerState.selectedStartDateMillis,
                            endDateMillis = dateRangePickerState.selectedEndDateMillis,
                        )
                    }
                },
                headline = {},
                state = dateRangePickerState,
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = customColors.backgroundColor,
                    titleContentColor = customColors.onBackgroundColor,
                    headlineContentColor = customColors.onBackgroundColor,
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(500.dp)
            )
        }
    }
}

@Composable
fun CustomDatePickerHeadline(
    startDateMillis: Long?,
    endDateMillis: Long?,
) {
    val customColors = MaterialTheme.customColors
    Text(
        text = "${startDateMillis.toformatDateFromMillis("Tanggal Mulai")} - ${
            endDateMillis.toformatDateFromMillis(
                "Tanggal Selesai"
            )
        }",
        style = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 20.sp, // Ukuran teks]
            fontWeight = FontWeight.Bold, // Gaya teks
            textAlign = TextAlign.Center, // Perataan teks
            color = customColors.onBackgroundColor
        )
    )
}

