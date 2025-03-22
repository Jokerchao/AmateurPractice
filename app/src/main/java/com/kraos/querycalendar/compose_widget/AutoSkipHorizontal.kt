package com.kraos.querycalendar.compose_widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author kraos
 * @date 2024/9/12
 * 自动缩略布局
 */

private enum class Slot {
    Content,
    Indicator
}

@Composable
fun AutoSkipHorizontal(
    modifier: Modifier = Modifier,
    skipIndicator: @Composable () -> Unit,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalSpacing: Dp = 0.dp,
    content: @Composable () -> Unit,
) {

    SubcomposeLayout(
        modifier = modifier
    ) { constraints ->
        val placeables =
            subcompose(Slot.Content, content).map { it.measure(constraints.copy(minWidth = 0)) }
        val indicatorPlaceable =
            subcompose(
                Slot.Indicator,
                skipIndicator
            ).map { it.measure(constraints.copy(minWidth = 0)) }.firstOrNull()
                ?: error("没有指示器")
        val horizontalSpacingPx = horizontalSpacing.roundToPx()
        var layoutWidth = 0

        var maxShowItem = 0
        placeables.forEachIndexed foreach@{ index, placeable ->
            if (placeable.width + layoutWidth + horizontalSpacingPx > constraints.maxWidth) {
                return@foreach
            }
            maxShowItem++
            if (index != 0) {
                layoutWidth += horizontalSpacingPx
            }
            layoutWidth += placeable.width
        }

        if (maxShowItem < placeables.size) {
            layoutWidth += (horizontalSpacingPx + indicatorPlaceable.width)
        }

        //高度取最高那个元素
        val layoutHeight =
            if (constraints.hasFixedHeight) constraints.maxHeight else placeables.maxByOrNull { it.height }?.height
                ?: 0

        layout(
            if (constraints.hasFixedWidth) constraints.maxWidth else layoutWidth,
            layoutHeight
        ) {
            var left = 0
            placeables.forEachIndexed { index, placeable ->
                if (index >= maxShowItem) {
                    return@forEachIndexed
                }
                if (index != 0) {
                    left += horizontalSpacingPx
                }
                placeable.placeRelative(
                    left,
                    verticalAlignment.align(
                        size = placeable.height,
                        space = layoutHeight
                    )
                )
                left += placeable.width
            }
            if (maxShowItem < placeables.size) {
                left += horizontalSpacingPx
                indicatorPlaceable.placeRelative(
                    left,
                    verticalAlignment.align(
                        size = indicatorPlaceable.height,
                        space = layoutHeight
                    )
                )
            }

        }
    }

}

@Composable
fun LazyAutoSkipHorizontal(
    modifier: Modifier = Modifier,
    skipIndicator: @Composable () -> Unit,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalSpacing: Dp = 0.dp,
    content: @Composable () -> Unit,
) {

    SubcomposeLayout(modifier = modifier) { constraints ->
        val placeables = mutableListOf<Placeable>()
        var layoutWidth = 0
        var maxShowItem = 0

        // 逐步测量子项，直到填满布局宽度
        val measures = subcompose(Slot.Content, content)
        measures.forEach { measure ->
            val placeable = measure.measure(constraints.copy(minWidth = 0))
            if (layoutWidth + placeable.width + horizontalSpacing.roundToPx() > constraints.maxWidth) {
                return@forEach
            }
            if (maxShowItem != 0) {
                layoutWidth += horizontalSpacing.roundToPx()
            }
            layoutWidth += placeable.width
            placeables.add(placeable)
            maxShowItem++
        }

        // 测量指示器
        val indicatorPlaceable = subcompose(Slot.Indicator, skipIndicator)
            .map { it.measure(constraints.copy(minWidth = 0)) }
            .firstOrNull() ?: error("没有指示器")

        if (maxShowItem < measures.size) {
            layoutWidth += horizontalSpacing.roundToPx() + indicatorPlaceable.width
        }

        val layoutHeight = if (constraints.hasFixedHeight) {
            constraints.maxHeight
        } else {
            placeables.maxOfOrNull { it.height } ?: 0
        }

        // 布局子项和指示器
        layout(
            width = if (constraints.hasFixedWidth) constraints.maxWidth else layoutWidth,
            height = layoutHeight
        ) {
            var left = 0

            placeables.forEachIndexed { index, placeable ->
                if (index != 0) left += horizontalSpacing.roundToPx()
                placeable.placeRelative(
                    x = left,
                    y = verticalAlignment.align(size = placeable.height, space = layoutHeight)
                )
                left += placeable.width
            }

            if (maxShowItem < measures.size) {
                left += horizontalSpacing.roundToPx()
                indicatorPlaceable.placeRelative(
                    x = left,
                    y = verticalAlignment.align(
                        size = indicatorPlaceable.height,
                        space = layoutHeight
                    )
                )
            }
        }
    }
}

/**
 * 只是为了证明Layout和SubcomposeLayout的区别,实际操作中不会这样写,SubcomposeLayout更适合这种情况
 */
@Composable
fun AutoSkipHorizontal1(
    modifier: Modifier = Modifier,
    skipIndicator: @Composable () -> Unit,
    content: @Composable () -> Unit
) {

    Layout(
        content = {
            content()
            skipIndicator()
        }) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        var totalWidth = 0
        val layoutHeight = placeables.minOf { it.height }
        var showMaxCount = 0

        placeables.forEach {
            if (totalWidth + it.width >= constraints.maxWidth) {
                if (placeables.last().width + totalWidth > constraints.maxWidth) {
                    showMaxCount--
                }
                return@forEach
            }
            totalWidth += it.width
            showMaxCount++
        }

        layout(constraints.maxWidth, layoutHeight) {
            var left = 0
            placeables.forEachIndexed { index, placeable ->
                if (index >= showMaxCount - 1) {
                    return@forEachIndexed
                }
                placeable.placeRelative(left, 0)
                left += placeable.width
            }
            if (showMaxCount < placeables.size - 1) {
                placeables.last().placeRelative(left, 0)
            }
        }
    }

}

@Composable
@Preview
private fun PreviewAutoSkipHorizontal() {

    AutoSkipHorizontal(
        modifier = Modifier.fillMaxWidth(),
        skipIndicator = {
            Box(
                Modifier
                    .size(20.dp, 60.dp)
                    .background(Color.Red)
            )
        },
        horizontalSpacing = 10.dp
    ) {
        (0..8).forEach {
            Box(
                Modifier
                    .size(60.dp, 20.dp)
                    .background(Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text("$it")
            }
        }
    }

}

@Composable
@Preview
private fun PreviewAutoSkipHorizontal1() {

    AutoSkipHorizontal1(
        modifier = Modifier.fillMaxWidth(),
        skipIndicator = {
            Box(
                Modifier
                    .size(20.dp, 60.dp)
                    .background(Color.Red)
            )
        },
    ) {
        (0..8).forEach {
            Box(
                Modifier
                    .size(60.dp, 20.dp)
                    .background(Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text("$it")
            }
        }
    }

}

@Composable
@Preview
private fun PreviewLazyAutoSkipHorizontal() {

    AutoSkipHorizontal(
        modifier = Modifier.fillMaxWidth(),
        skipIndicator = {
            Box(
                Modifier
                    .size(20.dp, 60.dp)
                    .background(Color.Red)
            )
        },
        horizontalSpacing = 10.dp
    ) {
        (0..3).forEach {
            Box(
                Modifier
                    .size(60.dp, 20.dp)
                    .background(Color.Green),
                contentAlignment = Alignment.Center
            ) {
                Text("$it")
            }
        }
    }

}