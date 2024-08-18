@file:OptIn(ExperimentalFoundationApi::class)

package com.kraos.querycalendar.compose_widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
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
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue
import kotlin.math.min

/**
 * @author kraos
 * @date 2024/7/25
 * @desc 带缩放效果的左右滚动pager
 */
object ZoomPager {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        pagerState: PagerState,
        contentPadding: PaddingValues = PaddingValues(50.dp),
    ) {
        HorizontalPager(
            modifier = modifier,
            state = pagerState,
            contentPadding = contentPadding
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(
                        310f / 440f
                    )
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                        //根据偏移量缩放
                        scaleX = lerp(
                            start = 0.79f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                        scaleY = lerp(
                            start = 0.79f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                    }
            ) {
                // Card content
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
                    Text(text = "Page: $page")
                }
            }
        }
    }

}

@Preview
@Composable
private fun PreViewZoomPager() {
    val pagerState = rememberPagerState(pageCount = {
        40
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ZoomPager.Content(
            modifier = Modifier.fillMaxSize(),
            pagerState = pagerState
        )

        BeyondPagerIndicator.Content(
            pagerState = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 180.dp),
        )

    }

}
