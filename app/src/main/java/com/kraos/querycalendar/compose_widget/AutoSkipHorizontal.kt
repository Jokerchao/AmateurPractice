package com.kraos.querycalendar.compose_widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
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
    content: List<@Composable () -> Unit>,
) {

    SubcomposeLayout(modifier = modifier) { constraints ->
        val placeables = mutableListOf<Placeable>()
        var layoutWidth = 0
        var maxShowItem = 0

        // 逐步测量子项，直到填满布局宽度
        for (item in content) {
            val placeable = subcompose(Slot.Content) { item() }
                .map { it.measure(constraints.copy(minWidth = 0)) }
                .first()

            if (layoutWidth + placeable.width + horizontalSpacing.roundToPx() > constraints.maxWidth) {
                break
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

        if (maxShowItem < content.size) {
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

            if (maxShowItem < content.size) {
                left += horizontalSpacing.roundToPx()
                indicatorPlaceable.placeRelative(
                    x = left,
                    y = verticalAlignment.align(size = indicatorPlaceable.height, space = layoutHeight)
                )
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
        (0..5).forEach {
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