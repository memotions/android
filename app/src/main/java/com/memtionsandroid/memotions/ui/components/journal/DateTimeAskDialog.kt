package com.memtionsandroid.memotions.ui.components.journal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
fun DateTimeAskDialog(
    onDismiss: () -> Unit,
    onSetDate: () -> Unit,
    onSetTime: () -> Unit
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
                    "Tentukan hari dan jam jurnal kamu",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { onSetDate() },
                        border = BorderStroke(1.dp, MaterialTheme.customColors.outlineColor),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            "Atur hari",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.customColors.TextOnBackgroundColor
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = { onSetTime() },
                        border = BorderStroke(1.dp, MaterialTheme.customColors.outlineColor),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            "Atur jam",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.customColors.TextOnBackgroundColor
                        )
                    }
                }
            }
        }
    }
}