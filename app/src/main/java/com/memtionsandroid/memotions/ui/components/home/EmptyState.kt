package com.memtionsandroid.memotions.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    title: String = "Not Found",
) {
    val customColors = MaterialTheme.customColors
    Column(
        modifier = modifier,
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
                painter = painterResource(R.drawable.ic_empty),
                tint = customColors.onSecondBackgroundColor,
                modifier = Modifier.size(36.dp)
            )
        }
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = title,
            color = customColors.onSecondBackgroundColor,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
