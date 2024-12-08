package com.memtionsandroid.memotions.ui.components.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.data.remote.response.statistics.StatisticsResponse
import com.memtionsandroid.memotions.ui.components.statistic.EmositonStatistic
import com.memtionsandroid.memotions.ui.theme.customColors
import timber.log.Timber


@Composable
fun JournalInfo(
    modifier: Modifier = Modifier,
    userStatistic: StatisticsResponse?
) {
    val customColors = MaterialTheme.customColors
    Timber.tag("Journal Info").d(userStatistic.toString())

    BoxContent(
        modifier = modifier.height(240.dp),
        header = {
            TitleCardWithIcon(
                title = "Jurnal"
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pen),
                    contentDescription = "Journal Icon",
                    modifier = Modifier.size(20.dp),
                    tint = customColors.onBackgroundColor
                )
            }
        },
        content = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = (userStatistic?.data?.journalCount ?: 0).toString(),
                    color = customColors.onBackgroundColor,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            EmositonStatistic(
                modifier = Modifier.padding(top = 8.dp),
                happy = (userStatistic?.data?.emotionCount?.happy ?: 0).toString(),
                sad = (userStatistic?.data?.emotionCount?.sad ?: 0).toString(),
                neutral = (userStatistic?.data?.emotionCount?.neutral ?: 0).toString(),
                angry = (userStatistic?.data?.emotionCount?.anger ?: 0).toString(),
                scared = (userStatistic?.data?.emotionCount?.scared ?: 0).toString()
            )
        }
    )
}
