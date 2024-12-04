package com.memtionsandroid.memotions.ui.components.statistic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun EmositonStatistic(
    modifier: Modifier,
    emotions: List<String>) {
    val customColors = MaterialTheme.customColors

    val angryImage = painterResource(R.drawable.emo_angry)
    val happyImage = painterResource(R.drawable.emo_happy)
    val sadImage = painterResource(R.drawable.emo_sad)
    val netralImage = painterResource(R.drawable.emo_netral)
    val scaredImage = painterResource(R.drawable.emo_scared)

    val total = emotions.size
    val emotionCounts = emotions.groupingBy { it }.eachCount()

    // Fungsi untuk menghitung persentase dan memformat hasilnya
    fun formatPercentage(count: Int, total: Int): String {
        val percentage = (count.toDouble() / total) * 100
        return "${"%.0f".format(percentage)}%"
    }

// Hitung dan format persentase untuk setiap emosi
    val happy = formatPercentage(emotionCounts["happy"] ?: 0, total)
    val sad = formatPercentage(emotionCounts["sad"] ?: 0, total)
    val neutral = formatPercentage(emotionCounts["neutral"] ?: 0, total)
    val angry = formatPercentage(emotionCounts["angry"] ?: 0, total)
    val scared = formatPercentage(emotionCounts["scared"] ?: 0, total)


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Image(
                    painter = happyImage,
                    contentDescription = "Happy Statistic",
                    modifier = Modifier.size(50.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(25.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(customColors.barColor)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = happy,
                        color = customColors.onBarColor,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 9.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Senang",
                style = MaterialTheme.typography.labelSmall,
                color = customColors.onSecondBackgroundColor
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Image(
                    painter = sadImage,
                    contentDescription = "Sad Statistic",
                    modifier = Modifier.size(50.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(25.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(customColors.barColor)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = sad,
                        color = customColors.onBarColor,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 9.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Sedih",
                style = MaterialTheme.typography.labelSmall,
                color = customColors.onSecondBackgroundColor
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Image(
                    painter = netralImage,
                    contentDescription = "Netral Statistic",
                    modifier = Modifier.size(50.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(25.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(customColors.barColor)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = neutral,
                        color = customColors.onBarColor,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 9.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Netral",
                style = MaterialTheme.typography.labelSmall,
                color = customColors.onSecondBackgroundColor
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Image(
                    painter = angryImage,
                    contentDescription = "Angry Statistic",
                    modifier = Modifier.size(50.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(25.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(customColors.barColor)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = angry,
                        color = customColors.onBarColor,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 9.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Marah",
                style = MaterialTheme.typography.labelSmall,
                color = customColors.onSecondBackgroundColor
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Image(
                    painter = scaredImage,
                    contentDescription = "Scared Statistic",
                    modifier = Modifier.size(50.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(25.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(customColors.barColor)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = scared,
                        color = customColors.onBarColor,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 9.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Takut",
                style = MaterialTheme.typography.labelSmall,
                color = customColors.onSecondBackgroundColor
            )
        }
    }
}