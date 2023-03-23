package com.kraos.querycalendar.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kraos.querycalendar.R
import com.kraos.querycalendar.view.advancedShadow

class ComposeTestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val testList = listOf("主播榜", "作品榜", "榮譽榜", "其它榜單")
            TestRank(testList)
        }
    }
}

@Composable
private fun Test(name: String = "Hello World!") {
    Column(Modifier.background(Color.Gray)) {
//        val textWord = remember { mutableStateOf("Hello World!") }
        val textWord = remember(name) {
            if (name.length > 5) {
                "long test"
            } else {
                "short test"
            }
        }
        Text(textWord)
        Image(painter = painterResource(id = R.drawable.test), contentDescription = "image")
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
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
        Text(text = it, color = Color(0xff050505))
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


