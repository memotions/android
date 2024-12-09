package com.memtionsandroid.memotions.ui.components.achievement

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.data.remote.response.statistics.AchievementsResponseItem
import com.memtionsandroid.memotions.ui.theme.customColors
import com.memtionsandroid.memotions.utils.toRomanNumerals
import kotlinx.coroutines.delay

@Composable
fun AchievementModal(
    modifier: Modifier = Modifier,
    achievement: AchievementsResponseItem,
    onDismissRequest: () -> Unit
) {
    val customColors = MaterialTheme.customColors
    var startAnimation by remember { mutableStateOf(false) }

    // Target animasi bergantung pada `startAnimation`
    val progress by animateFloatAsState(
        targetValue = if (startAnimation) achievement.tier.toFloat() / 3f else 0f,
        label = "Progress Animation"
    )

    // Mulai animasi saat komposisi dimuat
    LaunchedEffect(Unit) {
        delay(200)
        startAnimation = true
    }

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            colors = CardColors(
                containerColor = customColors.backgroundColor,
                contentColor = customColors.onBackgroundColor,
                disabledContentColor = customColors.onSecondBackgroundColor,
                disabledContainerColor = customColors.backgroundColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 12.dp)
                    .padding(horizontal = 24.dp)
            ) {
                AsyncImage(
                    model = achievement.iconUrl,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp),
                    placeholder = painterResource(R.drawable.ach_soon),
                    error = painterResource(R.drawable.ach_soon)
                )
                Text(
                    text = "${achievement.name} ${achievement.tier.toRomanNumerals()}",
                    style = MaterialTheme.typography.titleMedium,
                    color = customColors.onBackgroundColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = customColors.onSecondBackgroundColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .weight(1f)
                )

                AnimatedVisibility(
                    visible = startAnimation,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "+ ${achievement.pointsAwarded}",
                            style = MaterialTheme.typography.titleMedium,
                            color = customColors.onBackgroundColor,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(end = 8.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.exp),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = customColors.onBackgroundColor,
                    trackColor = customColors.secondBackgroundColor,
                )
                TextButton(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.End)
                ) {
                    Text(
                        text = "Tutup",
                        style = MaterialTheme.typography.titleSmall,
                        color = customColors.onBackgroundColor,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                    )
                }
            }


        }
    }

}