package com.memtionsandroid.memotions.ui.components.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.Poppins

@Composable
fun TitleApp() {
    val icon: Painter = painterResource(id = R.drawable.ic_title_app)

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Image(
            painter = icon,
            contentDescription = "App Icon",
            modifier = Modifier.size(40.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Memotions",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = Poppins
            )
        )
    }
}