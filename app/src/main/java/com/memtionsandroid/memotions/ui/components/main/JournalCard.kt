package com.memtionsandroid.memotions.ui.components.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.data.local.entity.Journal
import com.memtionsandroid.memotions.ui.theme.customColors
import com.memtionsandroid.memotions.utils.EmotionImageSource
import com.memtionsandroid.memotions.utils.formatDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JournalCard(
    journal: Journal, modifier: Modifier = Modifier
) {
    val customColors = MaterialTheme.customColors
    val tags = journal.tags?.joinToString(" ") { "#$it" }

    val icon = painterResource(id = R.drawable.ic_star)
    Surface(
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = customColors.backgroundColor,
        shadowElevation = 4.dp,
        tonalElevation = 8.dp,
        border = BorderStroke(0.5.dp, customColors.outlineColor)
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Column {
                Row(
                    modifier = Modifier.weight(1f)
                ) {

                    when (journal.status) {
                        "DRAFT" -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = customColors.secondBackgroundColor,
                                            shape = RoundedCornerShape(50.dp),
                                        )
                                        .padding(12.dp)
                                ) {
                                    Icon(
                                        contentDescription = null,
                                        painter = painterResource(R.drawable.ic_draft),
                                        tint = customColors.onBackgroundColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                StatusTag(
                                    status = "Draft", modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        "PUBLISHED" -> Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = customColors.secondBackgroundColor,
                                        shape = RoundedCornerShape(50.dp),
                                    )
                                    .padding(12.dp)
                            ) {
                                CombinedAnimationIcon(isGenerating = true)
                            }
                            StatusTag(
                                status = "Analyze", modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        "ANALYZED" -> {
                            Box( // Memberi jarak antar gambar
                            ) {
                                journal.emotionAnalysis?.reversed()
                                    ?.forEachIndexed { index, emotion ->
                                        Image(
                                            painter = EmotionImageSource(emotion.emotion),
                                            contentDescription = "Emotion Image",
                                            modifier = Modifier
                                                .size(37.dp)
                                                .align(Alignment.Center)
                                                .offset(
                                                    y = (index * 7).dp
                                                )
                                                .zIndex(index.toFloat())
                                        )

                                    }

                            }
//                            Image(
//                                painter = painter,
//                                contentDescription = "Emotion Image",
//                                modifier = Modifier.size(37.dp)
//                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = journal.title,
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium,
                            overflow = TextOverflow.Ellipsis,
                            color = customColors.TextOnBackgroundColor,
                        )
                        Text(
                            text = journal.content,
                            maxLines = 3,
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis,
                            color = customColors.TextOnBackgroundColor,
                        )
                    }
                    Icon(
                        painter = icon,
                        contentDescription = "Star Icon",
                        modifier = Modifier.size(12.dp),
                        tint = if (journal.starred) customColors.TextOnBackgroundColor else Color.Transparent,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = tags ?: "",
                        maxLines = 1,
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis,
                        color = customColors.onSecondBackgroundColor,
                    )
                    Text(
                        text = journal.datetime.formatDateTime(),
                        maxLines = 1,
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis,
                        color = customColors.onSecondBackgroundColor,
                    )
                }
            }

        }
    }
}


@Composable
fun CombinedAnimationIcon(isGenerating: Boolean) {
    val customColors = MaterialTheme.customColors
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.2f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Icon(
        painter = painterResource(R.drawable.ic_analyze),
        tint = customColors.onBackgroundColor,
        contentDescription = "Generating",
        modifier = Modifier
            .size(16.dp)
            .graphicsLayer(
                rotationZ = if (isGenerating) rotation else 0f,
                scaleX = if (isGenerating) scale else 1f,
                scaleY = if (isGenerating) scale else 1f
            )
    )
}


@Composable
fun StatusTag(
    modifier: Modifier = Modifier, status: String
) {
    val customColors = MaterialTheme.customColors
    Box(
        modifier = modifier.background(
            color = customColors.secondBackgroundColor,
            shape = RoundedCornerShape(12.dp),
        )
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = status,
            maxLines = 1,
            style = MaterialTheme.typography.labelSmall,
            color = customColors.onSecondBackgroundColor,
        )
    }

}
//
//@Preview
//@Composable
//fun JournalCardPreview() {
//    MemotionsTheme() {
//        val journal = Journal(
//            "Hari yang sangat melelahkan hingga saya ingin membunuh semua orang",
//            "bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla",
//            "Date",
//            listOf("Sekolah", "Kerja", "Belajar"),
//            "happy",
//            false,
//            "Draft"
//        )
//        JournalCard(journal)
////        Text(text = journal.Tag.joinToString(" "))
//    }
//}
//
//@Preview
//@Composable
//fun JournalCardPreviewDark() {
//    MemotionsTheme(darkTheme = true) {
//        val journal = Journal(
//            "Hari yang sangat melelahkan hingga saya ingin membunuh semua orang",
//            "bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla",
//            "Date",
//            listOf("Sekolah", "Kerja", "Belajar"),
//            "happy",
//            true,
//            "Draft"
//        )
//        JournalCard(journal)
////        Text(text = journal.Tag.joinToString(" "))
//    }
//}


