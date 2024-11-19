package com.kraos.querycalendar.compose_widget

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * @author kraos
 * @date 2024/11/19
 * @desc 倒计时动画控件
 */
object CountdownTimerWidget {

    data class CountdownTimerProperties(
        val totalTime: Int = 10, // 总时间（秒）
        val centerTextStyle: TextStyle = TextStyle(
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        ),
        val bgColors: List<Color> =
            listOf(
                Color.Yellow.copy(0.5f),
                Color.Black.copy(0.5f),
            ) // 进度环渐变色
    )

    private val LocalCountdownTimerProperties = compositionLocalOf {
        CountdownTimerProperties()
    }

    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        properties: CountdownTimerProperties = LocalCountdownTimerProperties.current,
        onTimerEnd: () -> Unit = {} // 倒计时结束回调
    ) {
        var timeLeft by remember {
            val time = properties.totalTime
            mutableIntStateOf(time)
        }
        val progress by animateFloatAsState(
            targetValue = timeLeft / properties.totalTime.toFloat(),
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing), label = "progress"
        )

        LaunchedEffect(key1 = timeLeft) {
            if (timeLeft > 0) {
                delay(1000)
                timeLeft--
            } else {
                onTimerEnd()
            }
        }

        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // 进度环
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                // 创建一个从中心向外扩展的渐变
                val brush = Brush.radialGradient(
                    colors = properties.bgColors,
                    center = center, // 中心点
                    radius = size.minDimension // 半径为画布的最小边长的一半
                )

                drawArc(
                    brush = brush,
                    startAngle = -90f,
                    sweepAngle = -360 * progress,
                    useCenter = true,
                    style = Fill,
                )
            }

            // 中心数字
            Text(
                text = "$timeLeft",
                style = properties.centerTextStyle
            )
        }
    }
}

@Composable
@Preview
fun CountdownTimerWidgetPreview() {
    CountdownTimerWidget.Content(
        modifier = Modifier.size(150.dp),
    )
}