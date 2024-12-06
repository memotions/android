package com.memtionsandroid.memotions.ui.components.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.Poppins
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun AppBar(
    title: String,
    inView: Boolean,
    onBack: () -> Unit,
    onAction: () -> Unit?,
    starredState: MutableState<Boolean>?
) {
    val customColors = MaterialTheme.customColors
    Row(
        modifier = Modifier.fillMaxWidth().statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = { onBack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back Icon",
                    modifier = Modifier.size(16.dp),
                    tint = customColors.onBackgroundColor
                )
            }

            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    color = customColors.onBackgroundColor
                ),
            )
        }

        if (inView && starredState != null) {
            var expanded by remember { mutableStateOf(false) }

            Box {
                TextButton(onClick = { expanded = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_vert),
                        contentDescription = "Action Icon",
                        modifier = Modifier.size(20.dp),
                        tint = customColors.onBackgroundColor
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(250.dp, 45.dp),
                    containerColor = Color(0xFF292828),
                    shape = RoundedCornerShape(8.dp),
                    offset = DpOffset((-10).dp, 0.dp),
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = if (starredState.value) "Hapus Bintang" else "Beri Bintang",
                                style = TextStyle(
                                    fontFamily = Poppins, fontSize = 16.sp,
                                    color = Color.White
                                ),
                            )
                        },
                        onClick = {
                            starredState.value = !starredState.value
                            onAction()
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(
                                    id = if (starredState.value) R.drawable.ic_stars_fill else R.drawable.ic_stars_line
                                ),
                                contentDescription = "Star Icon",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        contentPadding = PaddingValues(start = 15.dp, top = 0.dp, bottom = 17.5.dp),
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DefaultPreview() {
    val starredState = remember { mutableStateOf(false) }
    AppBar(
        title = "Tambah Journal",
        inView = true,
        onBack = {},
        onAction = {},
        starredState = starredState
    )
}