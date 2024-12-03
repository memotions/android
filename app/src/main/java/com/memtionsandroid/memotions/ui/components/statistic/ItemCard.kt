package com.memtionsandroid.memotions.ui.components.statistic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.customColors


@Composable
fun ItemCard(
    title: String,
    emotion: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val customColors = MaterialTheme.customColors
    val iconPainter = when (emotion) {
        "happy" -> painterResource(id = R.drawable.emo_happy)
        "angry" -> painterResource(id = R.drawable.emo_angry)
        "sad" -> painterResource(id = R.drawable.emo_sad)
        "neutral" -> painterResource(id = R.drawable.emo_netral)
        "scared" -> painterResource(id = R.drawable.emo_scared)
        else -> painterResource(id = R.drawable.emo_netral)
    }


    Surface(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) customColors.secondBackgroundColor else customColors.backgroundColor,
        shadowElevation = 4.dp,
        tonalElevation = 8.dp,
        border = BorderStroke(0.5.dp, customColors.outlineColor)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    color = customColors.TextOnBackgroundColor,
                )
                Image(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(37.dp),
                    painter = iconPainter,
                    contentDescription = "Emotion Icon",
                )
            }
        }
    }
}