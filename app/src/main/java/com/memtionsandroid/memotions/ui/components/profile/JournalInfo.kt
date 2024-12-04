package com.memtionsandroid.memotions.ui.components.profile

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.components.statistic.EmositonStatistic
import com.memtionsandroid.memotions.ui.theme.customColors


@Composable
fun JournalInfo(modifier: Modifier = Modifier) {
    val customColors = MaterialTheme.customColors
    val journalCount = 30
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
                    text = journalCount.toString(),
                    color = customColors.onBackgroundColor,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            EmositonStatistic(modifier = Modifier.padding(top = 8.dp), emotions = emptyList())
        }
    )
}
//
//@Composable
//fun JournalInfo(modifier: Modifier = Modifier) {
//    val customColors = MaterialTheme.customColors
//    val journalCount = 30
//
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(
//                240.dp
//            )
//            .border(
//                width = 0.5.dp,
//                color = customColors.outlineColor,
//                shape = RoundedCornerShape(12.dp)
//            )
//            .background(
//                color = customColors.backgroundColor,
//                shape = RoundedCornerShape(12.dp),
//
//                )
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(12.dp)
//        ) {
//            TitleCardWithIcon(
//                title = "Jurnal"
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_pen),
//                    contentDescription = "Journal Icon",
//                    modifier = Modifier.size(20.dp),
//                    tint = customColors.onBackgroundColor
//                )
//            }
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//            ) {
//                Text(
//                    modifier = Modifier.padding(16.dp),
//                    text = journalCount.toString(),
//                    color = customColors.onBackgroundColor,
//                    style = MaterialTheme.typography.titleLarge.copy(
//                        fontSize = 40.sp,
//                        fontWeight = FontWeight.ExtraBold
//                    )
//                )
//            }
//            EmositonStatistic(modifier = Modifier.padding(top = 8.dp))
//        }
//    }
//}

@Preview
@Composable
fun JournalInfoPreview() {
    JournalInfo()
}