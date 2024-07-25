package com.kraos.querycalendar.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kraos.querycalendar.compose_widget.BeyondPagerIndicator
import com.kraos.querycalendar.compose_widget.ZoomPager

@OptIn(ExperimentalFoundationApi::class)
class ZoomPagerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val pagerState = rememberPagerState(pageCount = {
                15
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
    }
}