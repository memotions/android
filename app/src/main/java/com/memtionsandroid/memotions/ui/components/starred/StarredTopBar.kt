package com.memtionsandroid.memotions.ui.components.starred

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.components.home.HomeTopBar
import com.memtionsandroid.memotions.ui.components.main.SearchBar
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun StarredTopBar() {
    val customColors = MaterialTheme.customColors
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shadowElevation = 4.dp,
        tonalElevation = 8.dp,
        border = BorderStroke(0.5.dp, customColors.outlineColor),
        shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp),
        ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(customColors.backgroundColor)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Column {
                Text(text = "Jurnal Berbintang",
                    style = MaterialTheme.typography.titleLarge)
                SearchBar(modifier = Modifier.padding(top = 12.dp))
            }
        }
    }
}


@Preview
@Composable
fun HomeTopBarPreview() {
    MemotionsTheme (){
        StarredTopBar()
    }
}

@Preview
@Composable
fun HomeTopBarPreviewDark() {
    MemotionsTheme (darkTheme = true){
        StarredTopBar()
    }
}