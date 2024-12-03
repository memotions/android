package com.memtionsandroid.memotions.ui.components.statistic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun StatisticTopBar() {
    val customColors = MaterialTheme.customColors
    var isExchange by remember { mutableStateOf(false) }
    var selectedMode by remember { mutableStateOf(0) }
    val options = listOf("Semua", "Hari", "Minggu", "Bulan")

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shadowElevation = 4.dp,
        tonalElevation = 8.dp,
        border = BorderStroke(0.5.dp, customColors.outlineColor),
        shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(customColors.backgroundColor)
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .statusBarsPadding()
        ) {
            Column {
                Text(
                    text = "Statistik",
                    style = MaterialTheme.typography.titleLarge
                )

                EmositonStatistic(modifier = Modifier.padding(top = 12.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    AnimatedVisibility(isExchange) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            JournalChart(
                                modifier = Modifier.padding(20.dp),
                                chartData = listOf(
                                    ChartData("Happy", 10, Color(0xFFFDD438)),
                                    ChartData("Sad", 20, Color(0xFF493DEB)),
                                    ChartData("Netral", 10, Color(0xFF9AA6A7)),
                                    ChartData("Angry", 10, Color(0xFFEA543A)),
                                    ChartData("Scared", 10, Color(0xFF6F4CB6)),
                                )
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "12",
                                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp),
                                        color = customColors.onBackgroundColor
                                    )
                                    Text(
                                        text = "Jurnal",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = customColors.onSecondBackgroundColor
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ChoiceButton(options = options, selectedOption = selectedMode) {
                            selectedMode = it
                        }
                        IconButton(
                            onClick = { isExchange = !isExchange },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = if (isExchange) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = if (isExchange) "Sembunyikan" else "Lihat Selengkapnya"
                            )
                        }
//                        TextButton(
//                            onClick = { isExchange = !isExchange },
//                        ) {
//                            Text(
//                                text = if (!isExchange) "Lihat Selengkapnya" else "Sembunyikan",
//                                color = customColors.onSecondBackgroundColor,
//                                style = MaterialTheme.typography.labelMedium,
//                            )
//                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun StatisticTopBarPreview() {
    MemotionsTheme() {
        StatisticTopBar()
    }
}

@Preview
@Composable
fun StatisticTopBarPreviewDark() {
    MemotionsTheme(darkTheme = true) {
        StatisticTopBar()
    }
}