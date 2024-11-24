package com.memtionsandroid.memotions.ui.components.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R

@Composable
fun BottomBar(
    onTagClick: () -> Unit,
    onClockClick: () -> Unit,
    onSaveClick: () -> Unit,
    starredState: MutableState<Boolean>
) {
    Surface(
        color = Color(0xFF222222),
        shadowElevation = 6.dp,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        contentColor = Color(0xFF292828),
        modifier = Modifier.imePadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = {
                starredState.value = !starredState.value
            }) {
                Icon(
                    painter = painterResource(id = if (starredState.value) R.drawable.ic_stars_fill else R.drawable.ic_stars_line),
                    contentDescription = "Star",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            IconButton(onClick = {
                onTagClick()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_tag),
                    contentDescription = "Tag",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            IconButton(onClick = {
                onClockClick()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = "Clock",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = {
                    onSaveClick()
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_feather),
                        contentDescription = "Save",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Simpan",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}