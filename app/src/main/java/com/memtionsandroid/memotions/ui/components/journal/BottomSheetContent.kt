package com.memtionsandroid.memotions.ui.components.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.Poppins

@Composable
fun BottomSheetContent(
    emotionType: EmotionType,
    confidenceScore: Float,
    feedback: String
) {
    val emotionIcon = when (emotionType) {
        EmotionType.HAPPY -> painterResource(id = R.drawable.ic_happy_vjournal)
        EmotionType.SAD -> painterResource(id = R.drawable.ic_sad_vjournal)
        EmotionType.NEUTRAL -> painterResource(id = R.drawable.ic_neutral_vjournal)
        EmotionType.ANGRY -> painterResource(id = R.drawable.ic_angry_vjournal)
        EmotionType.SCARED -> painterResource(id = R.drawable.ic_scared_vjournal)
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Hasil Analisis",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(31.dp))
            Icon(
                painter = emotionIcon,
                contentDescription = "Emotion Icon",
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                formatPercentage(confidenceScore),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = feedback,
                    style = TextStyle(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        fontFamily = Poppins,
                        color = Color.White
                    )
                )
            }
        }
    }
}

fun formatPercentage(value: Float): String {
    val percentage = (value * 100).toInt()
    return "$percentage%"
}
