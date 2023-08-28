package com.kraos.querycalendar.activity

import Orientation
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kraos.querycalendar.HorizontalAlignment
import com.kraos.querycalendar.HorizontalAnimationStyle
import com.kraos.querycalendar.R
import com.kraos.querycalendar.VerticalAlignment
import com.kraos.querycalendar.VerticalAnimationStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * @author kraos
 * @date 2023/7/10
 * @desc 仿探探卡片滑动交互
 */
class SwipeStackCardActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawables = listOf(
            R.drawable.ic_test_1,
            R.drawable.ic_test_2,
            R.drawable.ic_test_3,
            R.drawable.ic_test_4,
            R.drawable.ic_test_5,
        )
        setContent {
            /*Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                SwipeStackCard(
                    { index ->
                        Image(
                            painterResource(id = drawables[index]),
                            contentDescription = "Same Card Type with Different Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(355.dp, 611.dp)
                        )
                    },
                    cardCount = drawables.size,
                    paddingBetweenCards = 6.dp,
                )
            }*/
            StrangerExploreWidget()
        }
    }
}

@Composable
fun StrangerExploreWidget(
    onLoadMore: () -> Unit = {}
) {

    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalItemSpacing = 10.dp
    ) {
        itemsIndexed((0..20).toList()) { index, item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (index == 3) 120.dp else 100.dp)
                    .background(color = Color.Red)
                    .border(1.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text("$index")
            }
        }

    }
}

@Composable
@Preview
fun PreviewSwipeStackCard() {
    val drawables = listOf(
        R.drawable.ic_test_1,
        R.drawable.ic_test_2,
        R.drawable.ic_test_3,
        R.drawable.ic_test_4,
        R.drawable.ic_test_5,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        SwipeStackCard(
            { index ->
                Image(
                    painterResource(id = drawables[index]),
                    contentDescription = "Same Card Type with Different Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(355.dp, 611.dp)
                )
            }, cardCount = drawables.size
        )
    }
}

private const val rotationValue = 45f
private const val defaultAnimationDuration = 300
private val defaultPaddingBetweenItems = 40.dp
private val defaultSizeBetweenItems = 20.dp

@Composable
fun SwipeStackCard(
    cardContent: @Composable (Int) -> Unit,
    cardCount: Int,
    paddingBetweenCards: Dp = defaultPaddingBetweenItems,
    animationDuration: Int = defaultAnimationDuration,
    orientation: Orientation = Orientation.Vertical(),
    sizeBetweenCards: Dp = defaultSizeBetweenItems,
) {
    val coroutineScope = rememberCoroutineScope()

    var selectedIndex by remember { mutableStateOf(0) }

    val rotationValue = getRotation(orientation)

    Box(contentAlignment = Alignment.BottomCenter) {
        (0 until cardCount).forEachIndexed { index, _ ->
            ShowCard(coroutineScope,
                selectedIndex,
                index,
                cardCount,
                paddingBetweenCards,
                sizeBetweenCards,
                animationDuration,
                rotationValue,
                orientation,
                { cardContent(index) },
                { selectedIndex = it })
        }
    }
}


private fun getRotation(orientation: Orientation): Float {
    return when (orientation) {
        is Orientation.Vertical -> if (orientation.animationStyle == VerticalAnimationStyle.ToRight) rotationValue
        else -rotationValue

        else -> {
            throw IllegalStateException()
        }
    }
}

@Composable
private fun ShowCard(
    coroutineScope: CoroutineScope,
    selectedIndex: Int,
    index: Int,
    cardCount: Int,
    paddingBetweenCards: Dp,
    sizeBetweenCards: Dp,
    animationDuration: Int,
    rotationValue: Float,
    orientation: Orientation,
    composable: @Composable (Int) -> Unit,
    newIndexBlock: (Int) -> Unit
) {
    var itemPxSize = 0

    val selectedIndexState by rememberUpdatedState(newValue = selectedIndex)

    val sizeWidth = when {
        selectedIndexState == index -> 355.dp
        selectedIndexState < index -> 355.dp - ((index - selectedIndexState) * sizeBetweenCards.value).dp
        selectedIndexState > index -> 355.dp - ((cardCount - selectedIndexState + index) * sizeBetweenCards.value).dp
        else -> throw IllegalStateException()
    }

    val sizeHeight = sizeWidth.value * 1.72f

    val padding = when {
        selectedIndexState == index -> 0.dp
        selectedIndexState < index -> ((index - selectedIndexState) * paddingBetweenCards.value + 611 - sizeHeight).dp
        selectedIndexState > index -> ((cardCount - selectedIndexState + index) * paddingBetweenCards.value + 611 - sizeHeight).dp
        else -> throw IllegalStateException()
    }


    val paddingAnimation by animateDpAsState(
        padding, tween(animationDuration, easing = FastOutSlowInEasing), label = "paddingAnimation"
    )

    val sizeAnimation by animateDpAsState(
        sizeWidth, tween(animationDuration, easing = FastOutSlowInEasing), label = "sizeAnimation"
    )

    val offsetAnimation = remember { Animatable(0f) }
    val rotateAnimation = remember { Animatable(0f) }

    var dragOffsetX by remember { mutableStateOf(0f) }
    var dragOffsetY by remember { mutableStateOf(0f) }

    val offsetValues = when (orientation) {
        is Orientation.Vertical -> {
            IntOffset(
                if (orientation.animationStyle == VerticalAnimationStyle.ToRight) offsetAnimation.value.toInt()
                else -offsetAnimation.value.toInt(), 0
            )
        }

        else -> {
            throw IllegalStateException()
        }
    }

    val paddingModifier = when {
        orientation.alignment == VerticalAlignment.TopToBottom -> PaddingValues(
            top = paddingAnimation
        )

        orientation.alignment == VerticalAlignment.BottomToTop -> PaddingValues(
            bottom = paddingAnimation
        )

        else -> PaddingValues(end = paddingAnimation)
    }


    val modifier = Modifier
        .padding(paddingModifier)
        .zIndex(-padding.value)
        .offset {
            if (selectedIndexState == index) {
                IntOffset(dragOffsetX.roundToInt(), dragOffsetY.roundToInt())
            } else {
                offsetValues
            }
        }
        .rotate(rotateAnimation.value)
        .onSizeChanged {
            itemPxSize = if (itemPxSize > it.width) itemPxSize
            else it.width

        }
        .pointerInput(Unit) {
            detectDragGestures(onDragCancel = {
                dragOffsetX = 0f
                dragOffsetY = 0f
            }, onDragEnd = {
                if (selectedIndexState == index) {
                    coroutineScope.launch {
                        Log.d(
                            "ShowCard",
                            "dragOffsetX->$dragOffsetX,selectedIndexState$selectedIndexState,index$index"
                        )
                        if (dragOffsetX > 50) {
                            animateOnClick(itemPxSize,
//                                    runAnimations,
                                animationDuration,
                                rotationValue,
                                index,
                                cardCount,
                                offsetAnimation,
                                rotateAnimation,
                                newIndexBlock,
                                animateCallback = {
                                    dragOffsetX = 0f
                                    dragOffsetY = 0f
                                })
                        } else {
                            dragOffsetX = 0f
                            dragOffsetY = 0f
                        }
                    }
                }
            }, onDrag = { change, dragAmount ->
                if (selectedIndexState == index) {
                    change.consume()
                    dragOffsetX += dragAmount.x
                    dragOffsetY += dragAmount.y
                    coroutineScope.launch {
                        rotateAnimation.animateTo(dragOffsetX / 50, TweenSpec(10))
                    }
                }
            })
        }
        .size(sizeAnimation, sizeHeight.dp)
    Box(
        modifier = Modifier
            .then(modifier)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(shape = RoundedCornerShape(20.dp), color = Color.White),
    ) {
        composable.invoke(index)
    }
}

private suspend fun animateOnClick(
    pxValue: Int,
    animationDuration: Int,
    rotationValue: Float,
    index: Int,
    cardCount: Int,
    offsetAnimation: Animatable<Float, AnimationVector1D>,
    rotateAnimation: Animatable<Float, AnimationVector1D>,
    newIndexBlock: (Int) -> Unit,
    animateCallback: () -> Unit = {}
) {
    coroutineScope {
        Log.d(
            "ShowCard", "animateOnClick"
        )

        val spec: TweenSpec<Float> = tween(animationDuration)

        offsetAnimation.animateTo(pxValue.toFloat(), TweenSpec(10, easing = LinearEasing))

        val newIndex = if (cardCount > index + 1) {
            index + 1
        } else {
            0
        }

        Log.d("ShowCard", "animateOnClick: $newIndex $index")

        newIndexBlock.invoke(newIndex)

        rotateAnimation.animateTo(rotationValue, spec)
        joinAll(launch { rotateAnimation.animateTo(0f, spec) },
            launch { offsetAnimation.animateTo(0f, spec) })
        animateCallback.invoke()
    }

}
