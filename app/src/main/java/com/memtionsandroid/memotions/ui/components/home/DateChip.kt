package com.memtionsandroid.memotions.ui.components.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.theme.customColors
import com.memtionsandroid.memotions.utils.toformatDateFromMillis


@Composable
fun DateChip(
    modifier: Modifier = Modifier,
    viewOnly: Boolean = false,
    dateRange: (Pair<Long?, Long?>),
    onRemove: () -> Unit
) {
    val customColors = MaterialTheme.customColors

    Box(
        modifier = modifier
            .border(
                width = 0.5.dp,
                color = customColors.onSecondBackgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onRemove() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${dateRange.first.toformatDateFromMillis("Tanggal Mulai")} - ${
                    dateRange.second.toformatDateFromMillis(
                        "Tanggal Selesai"
                    )
                }",
                style = MaterialTheme.typography.labelSmall,
                color = customColors.onSecondBackgroundColor
            )
            if (!viewOnly) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    tint = customColors.onSecondBackgroundColor,
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp)
                )
            }
        }

    }
}