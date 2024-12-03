package com.memtionsandroid.memotions.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun SearchTagModal(
    tags: List<Tag>,
    onEmptyTagInputIcon: Painter = painterResource(id = R.drawable.ic_search),
    onEmptyTagInputHint: String = "Cari Tag",
    onDismissRequest: () -> Unit,
    onEmptyTagContent: @Composable (String) -> @Composable Unit,
    onItemClicked: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    val customColors = MaterialTheme.customColors
    var searchTags by remember { mutableStateOf("") }
    val filteredTags =
        tags.filter { it.name.contains(searchTags, ignoreCase = true) }

    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Card(
            colors = CardColors(
                containerColor = customColors.backgroundColor,
                contentColor = customColors.onBackgroundColor,
                disabledContentColor = customColors.onSecondBackgroundColor,
                disabledContainerColor = customColors.backgroundColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                BasicTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        }
                        .height(40.dp)
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                            color = customColors.secondBackgroundColor
                        ),
                    value = searchTags,
                    onValueChange = { searchTags = it },

                    singleLine = true,
                    cursorBrush = SolidColor(customColors.TextOnBackgroundColor),
                    textStyle = MaterialTheme.typography.bodySmall.copy(color = customColors.TextOnBackgroundColor),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier.padding(start = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.padding(end = 8.dp),
                                painter = if (filteredTags.isNotEmpty()) painterResource(id = R.drawable.ic_search) else onEmptyTagInputIcon,
                                contentDescription = "Search Icon",
                                tint = if (!isFocused) customColors.onSecondBackgroundColor else customColors.onBackgroundColor
                            )
                            Box(Modifier.weight(1f)) {
                                if (searchTags.isEmpty()) {
                                    Text(
                                        text = if (filteredTags.isNotEmpty()) "Cari Tag" else onEmptyTagInputHint,
                                        color = customColors.onSecondBackgroundColor,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                innerTextField()
                            }
                        }
                    },
                )


                if (filteredTags.isEmpty()) {
                    onEmptyTagContent(searchTags)
                }

                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(items = filteredTags) { tag ->
                        Row(
                            Modifier
                                .height(48.dp)
                                .fillMaxWidth()
                                .clickable {
                                    onItemClicked(tag.name)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_tag),
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(12.dp),
                                contentDescription = null,
                                tint = customColors.onBackgroundColor
                            )
                            Text(
                                text = tag.name,
                                color = customColors.onBackgroundColor,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}