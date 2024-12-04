package com.memtionsandroid.memotions.ui.components.statistic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun ChoiceButton(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String,
    onSelected: (String) -> Unit,
) {
    val customColors = MaterialTheme.customColors
    Box(modifier = modifier) {
        Row {
            options.forEachIndexed { index, label ->
                val isSelected = selectedOption == label
                TextButton(
                    onClick = { onSelected(label) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AnimatedVisibility(isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                            color = if (isSelected) customColors.onBackgroundColor else customColors.onSecondBackgroundColor
                        )
                    }
                }
            }
        }
    }
}