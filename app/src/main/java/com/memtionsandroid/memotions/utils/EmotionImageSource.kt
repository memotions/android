package com.memtionsandroid.memotions.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.components.statistic.Emotion

@Composable
fun EmotionImageSource(emotion: String?): Painter {
    return when (emotion) {
        "HAPPY" -> painterResource(id = R.drawable.emo_happy)
        "ANGER" -> painterResource(id = R.drawable.emo_angry)
        "SAD" -> painterResource(id = R.drawable.emo_sad)
        "NEUTRAL" -> painterResource(id = R.drawable.emo_netral)
        "SCARED" -> painterResource(id = R.drawable.emo_scared)
        else -> painterResource(id = R.drawable.emo_netral)
    }
}