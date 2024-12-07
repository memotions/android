package com.memtionsandroid.memotions.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.memtionsandroid.memotions.data.remote.response.journals.TagsItem
import com.memtionsandroid.memotions.ui.theme.customColors


//val tagsJurnal = listOf(
//    Tag(name = "Refleksi Harian"),
//    Tag(name = "Rasa Syukur"),
//    Tag(name = "Pelacakan Mood"),
//    Tag(name = "Catatan Impian"),
//    Tag(name = "Target Harian"),
//    Tag(name = "Kesehatan Mental"),
//    Tag(name = "Produktivitas"),
//    Tag(name = "Perenungan"),
//    Tag(name = "Momen Bahagia"),
//    Tag(name = "Inspirasi Harian")
//)


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchFilter(
    tags: List<TagsItem>,
    activeTags: List<TagsItem>,
    onTagAdded: (TagsItem) -> Unit,
    onTagRemoved: (TagsItem) -> Unit
) {
//    var tags by remember { mutableStateOf(listOf<Tag>()) }
    var openDialog by remember { mutableStateOf(false) }
    val customColors = MaterialTheme.customColors

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = {
                    openDialog = true
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_tag),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(18.dp),
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
                onClick = {  }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_date),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(18.dp),
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
            activeTags.forEach { tag ->
                TagChip(
                    tag = tag,
                    onRemove = { onTagRemoved(tag) }
                )
            }
//            tags.forEach { tag ->
//                TagChip(
//                    tag = tag,
//                    onRemove = {
////                        tags = tags.toMutableList().apply { removeAt(index) }
//                    })
//            }
        }
    }

    if (openDialog) {
        SearchTagModal(
            tags = tags,
            onDismissRequest = { openDialog = false },
            onItemClicked = {
                onTagAdded(it)
                openDialog = false
            },
            onEmptyTagContent = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "Tidak ada tag",
                        color = customColors.onSecondBackgroundColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        )
    }
}