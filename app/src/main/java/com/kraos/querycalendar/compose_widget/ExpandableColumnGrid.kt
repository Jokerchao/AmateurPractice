package com.kraos.querycalendar.compose_widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author kraos
 * @date 2025/4/23
 */
object ExpandableColumnGrid {

    private const val COLUMNS = 3 // 列数


    @Composable
    fun ExpandableThreeColumnGrid() {
//        val gifts = listOf("樱恋玉泉", "梦幻岛屿")
        val gifts = listOf(
            "樱恋玉泉",
            "梦幻岛屿",
            "摩天轮",
            "月光守护",
            "魔法卡片",
            "星空之旅",
            "海洋之心",
            "梦幻花园",
            "时光之钥",
            "流星雨",
            "魔法森林",
            "梦幻城堡",
            "星际探险"
        )
        var expandedIndex by remember { mutableStateOf(-1) }
        val gridRowCount = (gifts.size + COLUMNS - 1) / COLUMNS


        LazyVerticalGrid(
            columns = GridCells.Fixed(COLUMNS),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (row in 0 until gridRowCount) {
                val startIndex = row * COLUMNS
                val endIndex = minOf(startIndex + COLUMNS, gifts.size)

                // 插入当前行的元素
                for (i in startIndex until endIndex) {
                    item {
                        GiftItem(
                            name = gifts[i],
                            isSelected = (expandedIndex == i),
                            onClick = {
                                expandedIndex = if (expandedIndex == i) -1 else i
                            }
                        )
                    }
                }

                // 如果当前行包含展开项，插入详情
                if (expandedIndex in startIndex until endIndex) {
                    item(span = { GridItemSpan(COLUMNS) }) {
                        GiftDetailCard(giftName = gifts[expandedIndex])
                    }
                }
            }
        }
    }

    @Composable
    fun GiftItem(name: String, isSelected: Boolean, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // 保证是正方形
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFE1F5FE) else Color.White)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(name, fontWeight = FontWeight.Bold)
            }
        }
    }

    @Composable
    fun GiftDetailCard(giftName: String) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4C3))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🎁 $giftName", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("这是 $giftName 的详细描述内容...", fontSize = 14.sp)
            }
        }
    }
}

@Composable
@Preview
fun PreviewExpandableThreeColumnGrid() {
    ExpandableColumnGrid.ExpandableThreeColumnGrid()
}