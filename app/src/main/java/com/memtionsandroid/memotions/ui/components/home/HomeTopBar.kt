package com.memtionsandroid.memotions.ui.components.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.components.main.SearchBar
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun HomeTopBar(

) {
    val customColors = MaterialTheme.customColors
    val streakImage = painterResource(id = R.drawable.streak)
    val streak = 12

    val expImage = painterResource(id = R.drawable.exp)
    val currentEXP = 120
    val nextLevelEXP = 200
    val level = 1

    val username = "Liangga"
    val profileImage = painterResource(id = R.drawable.profile)

    var isFilter by remember { mutableStateOf(false) }


    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shadowElevation = 4.dp,
        tonalElevation = 8.dp,
        border = BorderStroke(0.5.dp, customColors.outlineColor),
        shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp),

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(customColors.backgroundColor)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .statusBarsPadding()
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = profileImage,
                            contentDescription = "Profile Image",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f),
                            text = username,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    Row(
                        modifier = Modifier.padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            color = customColors.TextOnBackgroundColor,
                            style = MaterialTheme.typography.titleSmall,
                            text = "${currentEXP}/${nextLevelEXP}"
                        )
                        Box {
                            Image(
                                painter = expImage,
                                contentDescription = "Streak Icon",
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .size(24.dp)

                            )
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                color = customColors.TextOnBackgroundColor,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center
                                ),
                                text = " ${level}"
                            )
                        }
                    }



                    Row(
                        modifier = Modifier.padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            color = customColors.TextOnBackgroundColor,
                            style = MaterialTheme.typography.titleSmall,
                            text = streak.toString()
                        )
                        Image(
                            painter = streakImage,
                            contentDescription = "Streak Icon",
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(24.dp)
                        )
                    }
                }
                SearchBar(modifier = Modifier.padding(top = 24.dp), isFilter = isFilter) {
                    isFilter = !isFilter
                }
                AnimatedVisibility(isFilter) {
                    SearchFilter()
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeTopBarPreview() {
    MemotionsTheme(darkTheme = true) {
        HomeTopBar()
    }
}