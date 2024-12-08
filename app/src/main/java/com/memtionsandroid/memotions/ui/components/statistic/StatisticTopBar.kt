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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.ui.theme.customColors

object Emotion {
    const val HAPPY = "HAPPY"
    const val SAD = "SAD"
    const val NEUTRAL = "NEUTRAL"
    const val ANGER = "ANGER"
    const val SCARED = "SCARED"
}


@Composable
fun StatisticTopBar(
    selectedMode: String,
    onSelectedMode: (String) -> Unit,
    emotions: List<String>
) {
    val customColors = MaterialTheme.customColors
    var isExchange by remember { mutableStateOf(false) }
    val options = listOf("Semua", "Hari", "Minggu", "Bulan")

    val emotionCounts = emotions.groupingBy { it }.eachCount()
    val total = emotions.size

    fun formatPercentage(count: Int, total: Int): String {
        if (total == 0) {
            return "0%"
        }
        val percentage = (count.toDouble() / total) * 100
        return "${"%.0f".format(percentage)}%"
    }

    val happy = formatPercentage(emotionCounts[Emotion.HAPPY] ?: 0, total)
    val sad = formatPercentage(emotionCounts[Emotion.SAD] ?: 0, total)
    val neutral = formatPercentage(emotionCounts[Emotion.NEUTRAL] ?: 0, total)
    val angry = formatPercentage(emotionCounts[Emotion.ANGER] ?: 0, total)
    val scared = formatPercentage(emotionCounts[Emotion.SCARED] ?: 0, total)

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

                EmositonStatistic(
                    modifier = Modifier.padding(top = 12.dp),
                    happy = happy,
                    sad = sad,
                    neutral = neutral,
                    angry = angry,
                    scared = scared
                )
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
                                chartData = if (total == 0) emptyList() else listOf(
                                    ChartData(
                                        "Happy",
                                        emotionCounts[Emotion.HAPPY] ?: 0,
                                        Color(0xFFFDD438)
                                    ),
                                    ChartData(
                                        "Sad",
                                        emotionCounts[Emotion.SAD] ?: 0,
                                        Color(0xFF493DEB)
                                    ),
                                    ChartData(
                                        "Netral",
                                        emotionCounts[Emotion.NEUTRAL] ?: 0,
                                        Color(0xFF9AA6A7)
                                    ),
                                    ChartData(
                                        "Angry",
                                        emotionCounts[Emotion.ANGER] ?: 0,
                                        Color(0xFFEA543A)
                                    ),
                                    ChartData(
                                        "Scared",
                                        emotionCounts[Emotion.SCARED] ?: 0,
                                        Color(0xFF6F4CB6)
                                    ),
                                )
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = emotions.size.toString(),
                                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp),
                                        color = customColors.onBackgroundColor
                                    )
                                    Text(
                                        text = "Emosi",
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
                            onSelectedMode(it)
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
                    }
                }
            }
        }
    }
}
