package com.memtionsandroid.memotions.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.memtionsandroid.memotions.ui.onboarding.screen.OnBoardingScreen1
import com.memtionsandroid.memotions.ui.onboarding.screen.OnBoardingScreen2
import com.memtionsandroid.memotions.ui.onboarding.screen.OnBoardingScreen3
import com.memtionsandroid.memotions.ui.onboarding.screen.OnBoardingScreen4
import com.memtionsandroid.memotions.ui.theme.Poppins
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun OnBoardingScreen() {
    val navController = rememberNavController()
    val pagerState = rememberPagerState(pageCount = { 4 })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationX = pageOffset * size.width
                        alpha = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                    }
            ) {
                when (page) {
                    0 -> OnBoardingScreen1()
                    1 -> OnBoardingScreen2()
                    2 -> OnBoardingScreen3()
                    3 -> OnBoardingScreen4()
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color(0xFF292828) else Color(0xFFD1BE89)
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(12.dp)
                )
            }
        }

        Row(
            modifier = Modifier.width(340.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = {
                    onStart(navController)
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.size(125.dp, 34.dp)
            ) {
                Text(
                    text = "Lewati",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF292828)
                    ),
                )
            }

            Button(
                onClick = { if (pagerState.currentPage == 3) {
                    onStart(navController)
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.size(125.dp, 34.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF292828)
                )
            ) {
                Text(
                    text = if (pagerState.currentPage == 3) "Mulai" else "Selanjutnya",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFFFF)
                    ),
                )
            }
        }

    }
}

fun onStart(navController: NavController) {
    navController.navigate("login")
}