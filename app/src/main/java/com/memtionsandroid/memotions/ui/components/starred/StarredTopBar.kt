package com.memtionsandroid.memotions.ui.components.starred

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.data.remote.response.journals.TagsItem
import com.memtionsandroid.memotions.ui.components.home.SearchFilter
import com.memtionsandroid.memotions.ui.components.main.SearchBar
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun StarredTopBar(
    searchText: String,
    tags: List<TagsItem>,
    activeTags: List<TagsItem>,
    onTagAdded: (TagsItem) -> Unit,
    onTagRemoved: (TagsItem) -> Unit,
    onSearchValueChange: (String) -> Unit

) {
    val customColors = MaterialTheme.customColors
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
                Text(
                    text = "Jurnal Berbintang",
                    style = MaterialTheme.typography.titleLarge
                )
                SearchBar(
                    modifier = Modifier.padding(top = 24.dp),
                    isFilter = isFilter,
                    searchText = searchText,
                    onValueChange = onSearchValueChange,
                    onFilterClicked = { isFilter = !isFilter }
                )
                AnimatedVisibility(isFilter) {
                    SearchFilter(
                        tags = tags,
                        activeTags = activeTags,
                        onTagAdded = { onTagAdded(it) },
                        onTagRemoved = { onTagRemoved(it) },
                    )
                }
            }
        }
    }
}
