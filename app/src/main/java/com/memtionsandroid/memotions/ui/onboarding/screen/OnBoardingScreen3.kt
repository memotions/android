package com.memtionsandroid.memotions.ui.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.components.auth.Constant.gradientBackground
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import com.memtionsandroid.memotions.ui.theme.Poppins

@Composable
fun OnBoardingScreen3() {
    val contentDesc = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Memotions ")
        }
        append("menggunakan kecerdasan buatan untuk memberikan saran yang membantu Anda memahami emosi dengan lebih baik")
    }
    val contentImage: Painter = painterResource(id = R.drawable.onboarding3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Analisis Berbasis AI",
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.width(231.dp)
        )

        Spacer(modifier = Modifier.height(65.dp))

        Text(
            text = contentDesc,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = Poppins,
            ),
            modifier = Modifier.size(width = 317.dp, height = 246.dp)
        )

        Image(
            painter = contentImage,
            contentDescription = "Content Image",
            modifier = Modifier.size(width = 338.dp, 212.dp)
        )

        Spacer(modifier = Modifier.height(112.dp))
    }
}

@Composable
@Preview(showBackground = true)
private fun DefaultPreview() {
    MemotionsTheme {
        OnBoardingScreen3()
    }
}