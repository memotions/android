package com.memtionsandroid.memotions.ui.components.achievement

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun AchievementTopBar(
    modifier: Modifier = Modifier,
    currentAchievement: Int,
    totalAchievement: Int,
    onBackClick: () -> Unit,

    ) {
    val customColors = MaterialTheme.customColors

    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shadowElevation = 4.dp,
        tonalElevation = 8.dp,
        border = BorderStroke(0.5.dp, customColors.outlineColor),
        shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(customColors.backgroundColor)
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .statusBarsPadding()
        ) {
            TextButton(
                onClick = { onBackClick() },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp),
                        tint = customColors.onBackgroundColor
                    )
                    Text(
                        text = "Pencapaian",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_trophy),
                    contentDescription = "Achievement Icon",
                    modifier = Modifier
                        .size(36.dp)
                        .padding(end = 8.dp),
                )
                Text(
                    text = "$currentAchievement/$totalAchievement",
                    style = MaterialTheme.typography.titleLarge,
                    color = customColors.onBackgroundColor
                )
            }
        }
    }

}