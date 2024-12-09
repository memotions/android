package com.memtionsandroid.memotions.ui.components.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun SaveAsDraftDialog(
    onDismiss: () -> Unit,
    onSaveDraft: () -> Unit,
    onDelete: () -> Unit,
) {
    val customColors = MaterialTheme.customColors
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            colors = CardColors(
                containerColor = customColors.backgroundColor,
                contentColor = customColors.onBackgroundColor,
                disabledContentColor = customColors.onSecondBackgroundColor,
                disabledContainerColor = customColors.backgroundColor
            ),
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 12.dp)
                    .padding(horizontal = 12.dp)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = customColors.secondBackgroundColor,
                            shape = RoundedCornerShape(50.dp),
                        )
                        .padding(12.dp)
                ) {
                    Icon(
                        contentDescription = null,
                        painter = painterResource(R.drawable.ic_empty),
                        tint = customColors.onSecondBackgroundColor,
                        modifier = Modifier.size(36.dp)
                    )
                }
                Text(
                    text = "Jurnal Belum Tersimpan!",
                    style = MaterialTheme.typography.titleMedium,
                    color = customColors.onBackgroundColor,
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
                Text(
                    text = "Jika kamu kembali sekarang, jurnal kamu akan hilang",
                    style = MaterialTheme.typography.bodySmall,
                    color = customColors.onSecondBackgroundColor,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 24.dp)
                        .weight(1f)
                )
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Lanjut mengedit",
                        style = MaterialTheme.typography.titleSmall,
                        color = customColors.onBackgroundColor,
                        modifier = Modifier
                    )
                }
                TextButton(
                    onClick = onSaveDraft,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Simpan sebagai draft",
                        style = MaterialTheme.typography.titleSmall,
                        color = customColors.onBackgroundColor,
                        modifier = Modifier
                    )
                }
                TextButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(50.dp),
                            color =
                            customColors.errorRed.copy(alpha = 0.05f),
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Hapus",
                        style = MaterialTheme.typography.titleSmall,
                        color = customColors.errorRed,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}