package com.kraos.querycalendar.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.kraos.querycalendar.R
import com.kraos.querycalendar.view.TestLearn
import com.kraos.querycalendar.view.advancedShadow

class ComposeTestActivity : BaseActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val testList = listOf("主播榜", "作品榜", "榮譽榜", "其它榜單")
            HorizontalPager(count = 2) { page ->
                when (page) {
                    0 -> TestRank(testList)
                    1 -> TestLearn("明日方舟")
                }
            }
        }
    }
}


@Composable
@Preview
private fun PreTestRank() {
    val stringList = listOf("主播榜", "作品榜", "榮譽榜", "其它榜單")
    TestRank(stringList)
}

@Composable
private fun TestRank(rankList: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                HeadContent()
            }
            items(rankList) {
                Box {
                    RankItem(it)
                }
            }
        }
    }
}

@Composable
private fun RankItem(it: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .advancedShadow(
                color = remember { Color(0xfa000000) },
                alpha = 0.04f,
                cornersRadius = 15.dp,
                shadowBlurRadius = 10.dp,
                offsetY = 1.5.dp
            )
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(it)
        FoldList()
    }
}

@Composable
private fun HeadContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .advancedShadow(
                color = remember { Color(0xfa000000) },
//                color = Color.Black,
                alpha = 0.04f,
                cornersRadius = 15.dp,
                shadowBlurRadius = 10.dp,
                offsetY = 1.5.dp
            )
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "image",
            modifier = Modifier.size(30.dp)
        )
        Text("用户名用户名", fontSize = 5.sp)
        Text(
            "共上榜210次",
            fontSize = 8.sp,
            modifier = Modifier
                .padding(top = 5.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            TopSingleRank()
            Spacer(
                modifier = Modifier
                    .size(1.dp, 30.dp)
                    .background(Color.Gray)
            )
            TopSingleRank()
            Spacer(
                modifier = Modifier
                    .size(1.dp, 30.dp)
                    .background(Color.Gray)
            )
            TopSingleRank()
        }
    }

}

@Composable
private fun TopSingleRank() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box() {
            Text(text = "70次", fontSize = 15.sp)
            Text(
                text = "+3", fontSize = 10.sp, modifier = Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .padding(start = 35.dp)
                    .background(Color.Red)
            )
        }
        Text(text = "作品榜", fontSize = 10.sp)
    }
}

@Composable
@Preview(showBackground = true)
fun FoldList() {
    var open by remember { mutableStateOf(false) }
    val degree by animateFloatAsState(targetValue = if (open) 180f else 0f)
    Column(
        Modifier.fillMaxSize(),
    ) {
        Row(modifier = Modifier
            .padding(top = 10.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(0.5.dp, Color(0x1AFF485C), RoundedCornerShape(12.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        remember { Color(0xFFFF485C).copy(alpha = 0.1f) },
                        remember { Color(0xFFFF485C).copy(alpha = 0.03f) }
                    ),
                    start = Offset(0f, 36.dp.value),
                    end = Offset(335.dp.value, 40.dp.value)
                )
            )
            .padding(10.dp)
            .clickable { open = !open }
        ) {
            Column(
                modifier = Modifier
//                    .weight(1f)
                    .width(300.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("《桥边姑娘》")
                    Text("NO.1", color = Color(0xFFFF485C))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("2023-03-0")
                    Text("歡歌live榜", color = Color(0xFFFF485C))
                }
            }

            Box {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .rotate(degree),
                    contentScale = ContentScale.Fit
                )
            }
        }

        AnimatedVisibility(visible = open) {
            Column {
                (0..10).forEach { _ ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("2023-02-09")
                            Text("片段榜")
                        }
                        Text("NO.3", color = Color(0xFFFF485C))
                    }
                }
            }
        }

    }

}


