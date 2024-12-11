package com.memtionsandroid.memotions.ui.components.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun SettingSection(
    title: String,
    checkedDarkModeValue: Boolean,
    onChangeDarkMode: (Boolean) -> Unit,
    checkedNotificationValue: Boolean,
    onChangeNotification: (Boolean) -> Unit
) {
    val customColors = MaterialTheme.customColors

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp)
            .fillMaxWidth()
            .background(customColors.backgroundColor),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = customColors.onBackgroundColor,
                modifier = Modifier.padding(
                    bottom = 5.dp, start = 10.dp
                )
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = customColors.backgroundColor),
            modifier = Modifier
                .border(
                    width = 0.4.dp,
                    color = customColors.outlineColor,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_darkmode),
                            contentDescription = "Darkmode Icon",
                            modifier = Modifier.size(16.dp),
                            tint = customColors.onBackgroundColor
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Mode Malam",
                            style = MaterialTheme.typography.bodyMedium,
                            color = customColors.onBackgroundColor

                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = checkedDarkModeValue,
                            onCheckedChange = { enabled ->
                                onChangeDarkMode(enabled)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = customColors.onBackgroundColor,
                                checkedTrackColor = customColors.onBackgroundColor.copy(alpha = 0.3f),
                                uncheckedThumbColor = customColors.onBackgroundColor,
                                uncheckedTrackColor = customColors.onBackgroundColor.copy(alpha = 0.3f),
                            ),
                            modifier = Modifier
                                .scale(0.5f)
                                .size(20.dp)
                                .padding(bottom = 5.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = "Darkmode Icon",
                            modifier = Modifier.size(16.dp),
                            tint = customColors.onBackgroundColor
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Notifikasi Harian",
                            style = MaterialTheme.typography.bodyMedium,
                            color = customColors.onBackgroundColor

                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = checkedNotificationValue,
                            onCheckedChange = { enabled ->
                                onChangeNotification(enabled)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = customColors.onBackgroundColor,
                                checkedTrackColor = customColors.onBackgroundColor.copy(alpha = 0.3f),
                                uncheckedThumbColor = customColors.onBackgroundColor,
                                uncheckedTrackColor = customColors.onBackgroundColor.copy(alpha = 0.3f),
                            ),
                            modifier = Modifier
                                .scale(0.5f)
                                .size(20.dp)
                                .padding(bottom = 5.dp)
                        )
                    }
                }
            }
        }
    }
}