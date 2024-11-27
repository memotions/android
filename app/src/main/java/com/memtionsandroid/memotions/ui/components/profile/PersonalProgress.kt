package com.memtionsandroid.memotions.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun PersonalProgress(modifier: Modifier = Modifier) {
    val customColors = MaterialTheme.customColors
    val level = 1
    val currentEXP = 120
    val nextLevelEXP = 200
    val streak = 12

    Box(modifier = modifier.width(220.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Box {
                    Image(
                        painter = painterResource(id = R.drawable.exp),
                        contentDescription = "Streak Icon",
                        modifier = Modifier
                            .size(70.dp)

                    )
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        color = customColors.TextOnBackgroundColor,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        ),
                        text = "${level}"
                    )
                }
                Spacer(modifier.padding(top = 8.dp))
                Text(
                    color = customColors.TextOnBackgroundColor,
                    style = MaterialTheme.typography.titleMedium,
                    text = "${currentEXP}/${nextLevelEXP}"
                )
            }

            Spacer(modifier.height(10.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.streak),
                    contentDescription = "Streak Icon",
                    modifier = Modifier
                        .size(70.dp)
                )
                Spacer(modifier.padding(top = 8.dp))
                Text(
                    color = customColors.TextOnBackgroundColor,
                    style = MaterialTheme.typography.titleMedium,
                    text = "$streak hari"
                )
                Text(
                    color = customColors.TextOnBackgroundColor,
                    style = MaterialTheme.typography.titleMedium,
                    text = "Beruntun"
                )
            }
        }
    }
}

@Preview
@Composable
private fun PersonalProgressPreview() {
    PersonalProgress()
}