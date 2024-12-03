package com.memtionsandroid.memotions.ui.components.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import com.memtionsandroid.memotions.ui.theme.customColors

data class Journal(
    val title: String,
    val content: String,
    val date: String,
    val Tag: List<String> = emptyList(),
    val isStarred: Boolean = false
)


@Composable
fun JournalCard(
    journal: Journal,
    modifier: Modifier = Modifier
) {
    val customColors = MaterialTheme.customColors
    val tags = journal.Tag.joinToString(" ") { "#$it" }
    val painter = painterResource(id = R.drawable.emo_angry)
    val icon = painterResource(id = R.drawable.ic_star)
    Surface(
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = customColors.backgroundColor,
        shadowElevation = 4.dp,
        tonalElevation = 8.dp,
        border = BorderStroke(0.5.dp, customColors.outlineColor)
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Column {
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "Angry Icon",
                        modifier = Modifier.size(37.dp)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = journal.title,
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium,
                            overflow = TextOverflow.Ellipsis,
                            color = customColors.TextOnBackgroundColor,
                        )
                        Text(
                            text = journal.content,
                            maxLines = 3,
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis,
                            color = customColors.TextOnBackgroundColor,
                        )
                    }
                    Icon(
                        painter = icon,
                        contentDescription = "Star Icon",
                        modifier = Modifier.size(12.dp),
                        tint = if (journal.isStarred) customColors.TextOnBackgroundColor else Color.Transparent,
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = tags,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis,
                        color = customColors.onSecondBackgroundColor,
                    )
                    Text(
                        text = journal.date,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis,
                        color = customColors.onSecondBackgroundColor,
                    )
                }
            }

        }
    }
}


@Preview
@Composable
fun JournalCardPreview() {
    MemotionsTheme() {
        val journal = Journal(
            "Hari yang sangat melelahkan hingga saya ingin membunuh semua orang",
            "bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla",
            "Date",
            listOf("Sekolah", "Kerja", "Belajar"),
            true
        )
        JournalCard(journal)
//        Text(text = journal.Tag.joinToString(" "))
    }
}

@Preview
@Composable
fun JournalCardPreviewDark() {
    MemotionsTheme(darkTheme = true) {
        val journal = Journal(
            "Hari yang sangat melelahkan hingga saya ingin membunuh semua orang",
            "bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla bla bla bla bla bla",
            "Date",
            listOf("Sekolah", "Kerja", "Belajar"),
            true
        )
        JournalCard(journal)
//        Text(text = journal.Tag.joinToString(" "))
    }
}


