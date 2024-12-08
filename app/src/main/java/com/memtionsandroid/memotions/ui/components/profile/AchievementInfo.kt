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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.customColors


@Composable
fun AchievementInfo(
    modifier: Modifier,
    achievementsCount: Int?
) {
    val customColors = MaterialTheme.customColors

    BoxContent(
        modifier = modifier.height(140.dp),
        header = {
            TitleCardWithIcon(
                title = "Pencapaian"
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_trophy),
                    contentDescription = "Throphy Icon",
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
                    text = (achievementsCount ?: 0).toString(),
                    color = customColors.onBackgroundColor,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }
    )

}