package com.memtionsandroid.memotions.ui.components.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    val customColors = MaterialTheme.customColors
    var searchText by remember { mutableStateOf("") }

    BasicTextField(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                color = customColors.secondBackgroundColor
            ),
        value = searchText,
        onValueChange = { searchText = it },
        singleLine = true,
        cursorBrush = SolidColor(customColors.TextOnBackgroundColor),
        textStyle = MaterialTheme.typography.bodySmall.copy(color = customColors.TextOnBackgroundColor),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    tint = customColors.onSecondBackgroundColor
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
                Icon(
                    modifier = Modifier.padding(start = 8.dp),
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Filter Icon",
                    tint = customColors.onSecondBackgroundColor
                )
            }
        },
    )

//    TextField(
//        singleLine = true,
//        value = searchText,
//        onValueChange = { searchText = it },
//        leadingIcon = {
//            Icon(
//                painter = painterResource(id = R.drawable.search),
//                contentDescription = "Search Icon",
//                tint = customColors.onSecondBackgroundColor
//            )
//        },
//        trailingIcon = {
//            Icon(
//                painter = painterResource(id = R.drawable.search),
//                contentDescription = "Filter Icon",
//                tint = customColors.onSecondBackgroundColor
//            )
//        },
//        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
//        placeholder = {
//            Text(
//                text = "Cari Jurnal",
//                color = customColors.onSecondBackgroundColor,
//                style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp)
//            )
//        },
//        colors = TextFieldDefaults.colors(
//            focusedTextColor = customColors.TextOnBackgroundColor,
//            unfocusedTextColor = customColors.TextOnBackgroundColor,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            focusedContainerColor = customColors.secondBackgroundColor,
//            unfocusedContainerColor = customColors.secondBackgroundColor,
//            cursorColor = customColors.TextOnBackgroundColor,
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(40.dp)
//            .focusable()
//    )
//
}

@Preview
@Composable
fun SearchBarPreview() {
    MemotionsTheme(darkTheme = true) {
        SearchBar()
    }
}