package com.memtionsandroid.memotions.ui.components.achievement

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import com.memtionsandroid.memotions.ui.components.profile.BoxContent
import com.memtionsandroid.memotions.ui.theme.customColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R

@Composable
fun AchievementCard(
    tier: Int,
    title: String,
//    description: Int,
    imageSource: Int,
    onCardClick: () -> Unit
) {
    val customColors = MaterialTheme.customColors
    BoxContent(
        modifier = Modifier.width(170.dp)
            .height(200.dp)
            .clickable { onCardClick() },
        header = {},
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Image(
                    painter = painterResource(imageSource),
                    contentDescription = null,
                    modifier = Modifier.size(70.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = customColors.onBackgroundColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth().weight(1f)
                )
                LinearProgressIndicator(

                    progress = { tier.toFloat() / 3f},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = customColors.onBackgroundColor,
                    trackColor = customColors.secondBackgroundColor
                )
            }
        }
    )
}