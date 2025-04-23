package com.kraos.querycalendar.compose_widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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

    @Stable
    class ExpandableColumnGridState {
        //选中的item
        var expandedIndex by mutableStateOf(-1)

        val contentList = mutableStateListOf<String>()
    }

    private const val COLUMNS = 3 // 列数


    @Composable
    fun ExpandableColumnGrid(
        modifier: Modifier = Modifier,
        state: ExpandableColumnGridState = remember { ExpandableColumnGridState() },
        contentPadding: PaddingValues = PaddingValues(0.dp),
        verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
        horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
        columnCount: Int = COLUMNS,
        itemContent: @Composable (Int) -> Unit = {},
        itemExpandContent: @Composable (Int) -> Unit = {}
    ) {
        val gridRowCount = remember {
            (state.contentList.size + columnCount - 1) / columnCount
        }
        if (state.contentList.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columnCount),
                modifier = modifier,
                contentPadding = contentPadding,
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement
            ) {
                for (row in 0 until gridRowCount) {
                    val startIndex = row * columnCount
                    val endIndex = minOf(startIndex + columnCount, state.contentList.size)

                    val isInCurrentRow by derivedStateOf {
                        state.expandedIndex in startIndex until endIndex
                    }
                    val isIndexValid by derivedStateOf {
                        state.expandedIndex >= 0 && state.expandedIndex < state.contentList.size
                    }

                    // 插入当前行的元素
                    for (i in startIndex until endIndex) {
                        item {
                            itemContent.invoke(i)
                        }
                    }

                    item(span = { GridItemSpan(columnCount) }) {
                        AnimatedVisibility(visible = isInCurrentRow && isIndexValid) {
                            itemExpandContent(state.expandedIndex)
                        }
                    }

                }
            }
        }


    }

    @Composable
    fun TestGiftItem(name: String, isSelected: Boolean, onClick: () -> Unit) {
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
    fun TestGiftDetailCard(giftName: String) {
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
    val state = remember {
        ExpandableColumnGrid.ExpandableColumnGridState().apply {
            contentList.addAll(
                listOf(
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
                    "星际探险",
                    "梦幻乐园"
                )
            )
        }
    }
    ExpandableColumnGrid.ExpandableColumnGrid(
        state = state,
        itemContent = { index ->
            ExpandableColumnGrid.TestGiftItem(
                name = "${index + 1} ${state.contentList[index]}",
                isSelected = (state.expandedIndex == index),
                onClick = {
                    state.expandedIndex =
                        if (state.expandedIndex == index) -1 else index
                }
            )
        },
        itemExpandContent = { index ->
            if (index >= 0 && index < state.contentList.size) {
                ExpandableColumnGrid.TestGiftDetailCard(
                    giftName = state.contentList[index]
                )
            }
        }
    )
}