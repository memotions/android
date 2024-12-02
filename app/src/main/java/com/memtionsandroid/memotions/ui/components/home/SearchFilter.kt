package com.memtionsandroid.memotions.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.customColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchFilter() {
    var tags by remember { mutableStateOf(listOf<Tag>()) }
    val customColors = MaterialTheme.customColors

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = {
                    tags = tags.toMutableList().apply { add(Tag("Sekolah")) }
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_tag),
                        modifier = Modifier.padding(end = 8.dp).size(18.dp),
                        contentDescription = null,
                        tint = customColors.onBackgroundColor
                    )
                    Text(
                        text = "Pilih Tag",
                        color = customColors.onBackgroundColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            TextButton(
                onClick = {tags = tags.toMutableList().apply { add(Tag("Kerja")) }}
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_date),
                        modifier = Modifier.padding(end = 8.dp).size(18.dp),
                        contentDescription = null,
                        tint = customColors.onBackgroundColor
                    )
                    Text(
                        text = "Pilih Tanggal",
                        color = customColors.onBackgroundColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),

            ) {
            tags.forEachIndexed { index, tag ->
                TagChip(
                    tag = tag,
                    onRemove = {
                        tags = tags.toMutableList().apply { removeAt(index) }
                    })
            }
        }
    }
}