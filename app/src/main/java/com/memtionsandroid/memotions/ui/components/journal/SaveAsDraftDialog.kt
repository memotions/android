package com.memtionsandroid.memotions.ui.components.journal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.theme.customColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveAsDraftDialog(
    onDismiss: () -> Unit,
    onSaveDraft: () -> Unit,
    onDelete: () -> Unit,
) {
    BasicAlertDialog(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp)),
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            color = MaterialTheme.customColors.backgroundColor,
            shape = RoundedCornerShape(10.dp),
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
            ) {
                Text(
                    text =
                    "Jika kamu kembali sekarang, jurnal kamu akan hilang, apakah kamu yakin?",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { onSaveDraft() },
                        border = BorderStroke(1.dp, MaterialTheme.customColors.outlineColor),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Simpan draft",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.customColors.TextOnBackgroundColor
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = { onDelete() },
                        border = BorderStroke(1.dp, MaterialTheme.customColors.outlineColor),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Ya, hapus",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}