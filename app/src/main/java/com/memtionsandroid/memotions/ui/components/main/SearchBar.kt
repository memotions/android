package com.memtionsandroid.memotions.ui.components.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    filterCount: Int = 0,
    isFilter: Boolean = false,
    searchText: String = "",
    onValueChange: (String) -> Unit = {},
    onFilterClicked: () -> Unit = {},
) {
    val customColors = MaterialTheme.customColors

    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = modifier
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
        value = searchText,
        onValueChange = { onValueChange(it) },

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
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    tint = if (!isFocused) customColors.onSecondBackgroundColor else customColors.onBackgroundColor
                )
                Box(Modifier.weight(1f)) {
                    if (searchText.isEmpty()) {
                        Text(
                            text = "Cari Jurnal",
                            color = customColors.onSecondBackgroundColor,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    innerTextField()
                }
                IconButton(
                    onClick = { onFilterClicked() },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = "Filter Icon",
                            tint = if (!isFilter) customColors.onSecondBackgroundColor else customColors.onBackgroundColor
                        )
                        if (filterCount > 0) {
                            Text(
                                text = filterCount.toString(),
                                modifier = Modifier.padding(start = 4.dp),
                                color = if (!isFilter) customColors.onSecondBackgroundColor else customColors.onBackgroundColor,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        },
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    MemotionsTheme(darkTheme = true) {
        SearchBar()
    }
}