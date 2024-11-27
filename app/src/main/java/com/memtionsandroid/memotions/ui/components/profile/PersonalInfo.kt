package com.memtionsandroid.memotions.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.customColors


data class User(
    val name: String,
    val yearCreated: String,
)

@Composable
fun PersonalInfo(user: User) {
    val customColors = MaterialTheme.customColors
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(40.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                color = customColors.onBackgroundColor
            )
            Text(
                text = "Bergabung sejak ${user.yearCreated}",
                style = MaterialTheme.typography.bodySmall,
                color = customColors.onSecondBackgroundColor
            )
        }
    }
}

@Preview
@Composable
fun PersonalInfoPreview() {
    val user = User(name = "Liangga", yearCreated = "2024")
    PersonalInfo(user)
}