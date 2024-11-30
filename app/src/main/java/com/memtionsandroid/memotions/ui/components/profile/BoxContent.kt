package com.memtionsandroid.memotions.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun BoxContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    header: @Composable () -> Unit
) {
    val customColors = MaterialTheme.customColors
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 0.5.dp,
                color = customColors.outlineColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = customColors.backgroundColor,
                shape = RoundedCornerShape(12.dp),

                )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            header()
            content()
        }
    }
}