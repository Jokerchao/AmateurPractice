@file:OptIn(ExperimentalFoundationApi::class)

package com.kraos.querycalendar.compose_widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min

/**
 * @author kraos
 * @date 2024/7/25
 * @desc 能超出数量限制的Pager指示器
 */
object BeyondPagerIndicator {

    data class BeyondPagerIndicatorProperties(
        val activeColor: Color = Color.White,
        val inactiveColor: Color = Color.White.copy(alpha = 0.35f),
        val indicatorCount: Int = 7,
        //指示器大小
        val indicatorSize: Dp = 10.dp,
        //指示器间距
        val indicatorSpacing: PaddingValues = PaddingValues(5.dp)
    )

    private val LocalBeyondPagerIndicatorProperties = compositionLocalOf {
        BeyondPagerIndicatorProperties()
    }

    @Composable
    fun Content(
        pagerState: PagerState,
        modifier: Modifier = Modifier,
        properties: BeyondPagerIndicatorProperties = LocalBeyondPagerIndicatorProperties.current
    ) {
        if (pagerState.pageCount <= 1) {
            return
        }

        //派生真正的指示器数量
        val realIndicatorCount by remember {
            derivedStateOf {
                min(properties.indicatorCount, pagerState.pageCount)
            }
        }


        LazyRow(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
            userScrollEnabled = false,
        ) {
            items(realIndicatorCount) { index ->

                val centerItemIndex = realIndicatorCount / 2

                val startPage = when {
                    pagerState.currentPage <= centerItemIndex -> 0
                    pagerState.currentPage >= pagerState.pageCount - centerItemIndex -> pagerState.pageCount - realIndicatorCount
                    else -> pagerState.currentPage - centerItemIndex
                }.coerceIn(0, pagerState.pageCount - realIndicatorCount)

                val actualPage = startPage + index

                //最左边指示器是否到达page的最左边
                val isLeftEdgeItem = startPage == 0

                //是否左边缘第一个符合尺寸变化
                val left1 = index == 0 && !isLeftEdgeItem

                //是否左边缘第二个符合尺寸变化
                val left2 = index == 1 && !isLeftEdgeItem

                //最右边指示器是否到达page的最右边
                val isRightEdgeItem = startPage == pagerState.pageCount - realIndicatorCount

                //是否右边缘第一个符合尺寸变化
                val right1 = index == realIndicatorCount - 1 && !isRightEdgeItem

                //是否右边缘第二个符合尺寸变化
                val right2 = index == realIndicatorCount - 2 && !isRightEdgeItem

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val scale = if (left1 || right1) {
                                0.5f
                            } else if (left2 || right2) {
                                0.8f
                            } else {
                                1f
                            }
                            scaleX = scale
                            scaleY = scale
                        }
                        .padding(properties.indicatorSpacing)
                        .clip(CircleShape)
                        .size(properties.indicatorSize)
                        .background(
                            if (actualPage == pagerState.currentPage) properties.activeColor else properties.inactiveColor,
                            CircleShape
                        )

                )
            }
        }
    }
}

@Preview
@Composable
private fun PreViewBeyondPagerIndicator() {
    val pagerState = rememberPagerState(pageCount = {
        40
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        //随机颜色
                        color = Color(
                            red = (0..255).random() / 255f,
                            green = (0..255).random() / 255f,
                            blue = (0..255).random() / 255f,
                            alpha = 1f
                        )
                    )
            ) {
                // Card content
                Text(text = "Page: $it")
            }
        }

        BeyondPagerIndicator.Content(
            pagerState = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 180.dp),
        )

    }

}