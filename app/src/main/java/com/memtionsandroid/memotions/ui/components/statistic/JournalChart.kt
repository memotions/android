package com.memtionsandroid.memotions.ui.components.statistic


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.memtionsandroid.memotions.ui.theme.MemotionsTheme
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ChartData(
    val label: String,
    val value: Int,
    val color: Color
)

data class ArcData(
    val animation: Animatable<Float, AnimationVector1D>,
//    val startAngle: Float,
    val sweepAngle: Float,
    val color: Color,
)


@Composable
fun JournalChart(
    modifier: Modifier = Modifier,
    chartData: List<ChartData>,
    content: @Composable () -> Unit = {}
) {
    val localModifier = modifier.size(200.dp)
    val totalValue = chartData.fold(0f) { acc, pieData ->
        acc + pieData.value
    }.div(360)
    var currentSum = 0
    val arcs = chartData.map {
        currentSum += it.value
        ArcData(
            sweepAngle = currentSum.div(totalValue),
            color = it.color,
            animation = Animatable(0f),

        )
    }

    LaunchedEffect(key1 = arcs) {
        arcs.map {
            launch {
                delay(405)
                it.animation.animateTo(
                    targetValue = it.sweepAngle,
                    animationSpec = androidx.compose.animation.core.tween(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }

    Box {
        Canvas(modifier = localModifier) {
            val stroke = Stroke(width = 20f)
            //        var currentStartAngle = -90f
            arcs.reversed().map {
                drawArc(
                    startAngle = -90f,
                    sweepAngle = it.animation.value,
                    color = it.color,
                    useCenter = false,
                    style = stroke
                )
            }
        }
        Column (modifier = Modifier.align(Alignment.Center)){
            content()
        }
    }

}

@Preview
@Composable
private fun JournalChartPreview() {
    MemotionsTheme {
        JournalChart(
            modifier = Modifier.padding(20.dp),
            chartData = listOf(
                ChartData("Happy", 10, Color.Yellow),
                ChartData("Sad", 20, Color.Blue),
                ChartData("Netral", 10, Color.Gray),
                ChartData("Angry", 10, Color.Red),
                ChartData("Scared", 10, Color.Magenta),
            )
        )
    }

}