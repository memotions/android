package com.memtionsandroid.memotions.ui.components.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.ui.components.home.Tag
import com.memtionsandroid.memotions.ui.components.home.TagChip
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import com.memtionsandroid.memotions.ui.theme.Poppins
import com.memtionsandroid.memotions.ui.theme.customColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FormSection(
    dateInfo: String,
    titleState: MutableState<TextFieldValue>,
    journalState: MutableState<TextFieldValue>,
    tags: List<Tag>,
    onTagRemove: (Int) -> Unit,
    inView: Boolean
) {
    val customColors = MaterialTheme.customColors

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp)
            .fillMaxWidth()
            .background(customColors.backgroundColor),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = dateInfo,
                style = TextStyle(
                    fontFamily = Poppins,
                    fontSize = 12.sp,
                    color = customColors.onSecondBackgroundColor.copy(alpha = 0.9f),
                ),
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = customColors.backgroundColor),
            modifier = Modifier
                .border(
                    width = 0.4.dp,
                    color = customColors.outlineColor,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BasicTextField(
                    value = titleState.value,
                    onValueChange = { titleState.value = it },
                    readOnly = inView,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = Poppins,
                        color = customColors.TextOnBackgroundColor,
                        fontWeight = FontWeight.Bold
                    ),
                    decorationBox = { innerTextField ->
                        if (titleState.value.text.isEmpty()) {
                            Text(
                                text = "Tuliskan Judul Jurnal",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = Poppins,
                                    color = customColors.onSecondBackgroundColor.copy(alpha = 0.2f),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                BasicTextField(
                    value = journalState.value,
                    onValueChange = { journalState.value = it },
                    readOnly = inView,
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = Poppins,
                        color = customColors.TextOnBackgroundColor,
                    ),
                    decorationBox = { innerTextField ->
                        if (journalState.value.text.isEmpty()) {
                            Text(
                                text = "Apa yang anda pikirkan?",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = Poppins,
                                    color = customColors.onSecondBackgroundColor.copy(alpha = 0.2f),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .defaultMinSize(minHeight = 200.dp)
                )

                if (tags.isNotEmpty()) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tags.forEachIndexed { index, tag ->
                            if(inView){
                                TagChip(
                                    tag = tag,
                                    onRemove = { onTagRemove(index) },
                                    viewOnly = true
                                )
                            }else{
                                TagChip(
                                    tag = tag,
                                    onRemove = { onTagRemove(index) },
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val titleState = remember { mutableStateOf(TextFieldValue()) }
    val journalState = remember { mutableStateOf(TextFieldValue()) }
    val tags = remember { mutableStateOf(listOf(Tag("Sekolah"), Tag("Pribadi"))) }

    MemotionsTheme {
        FormSection(
            dateInfo = "Today",
            titleState = titleState,
            journalState = journalState,
            tags = tags.value,
            onTagRemove = { index ->
                tags.value = tags.value.toMutableList().apply { removeAt(index) }
            },
            inView = false
        )
    }
}