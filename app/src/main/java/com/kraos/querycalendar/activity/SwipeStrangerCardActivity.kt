package com.kraos.querycalendar.activity

import Orientation
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kraos.querycalendar.R
import com.kraos.querycalendar.VerticalAlignment
import com.kraos.querycalendar.VerticalAnimationStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * @author kraos
 * @date 2023/7/10
 * @desc 仿探探卡片滑动交互
 */
class SwipeStrangerCardActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawables = mutableListOf(
            R.drawable.ic_test_1,
            R.drawable.ic_test_2,
            R.drawable.ic_test_3,
            R.drawable.ic_test_4,
            R.drawable.ic_test_5,
        )
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                StrangerCardStackList(drawables)
            }
        }
    }
}

@Composable
@Preview
fun PreviewSwipeStranger() {
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
        SwipeStranger(
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
private val defaultPaddingBetweenItems = 6.dp
private val defaultSizeBetweenItems = 20.dp

@Composable
fun SwipeStranger(
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

/**
 * 单个陌生人卡片的动画管理
 */
@Stable
class AnimatedSwipeStrangerCard(
    val bean: Int,
    //每张卡片的顶部偏移
    private val initPadding: Dp = 0.dp,
    //卡片的宽度
    private val initWidth: Dp = 0.dp,
    //卡片的高度
    private val initHeight: Dp = 0.dp,
    // 偏移最大值
    private val maxOffset: Dp = 100.dp,
    private val duration: Int = 300
) {
    private val offsetX = Animatable(0.dp, Dp.VectorConverter)
    val offsetXDp: Dp get() = offsetX.value
    private val offsetY = Animatable(0.dp, Dp.VectorConverter)
    val offsetYPx: Dp get() = offsetY.value
    private val rotation = Animatable(0f)
    val rotationValue: Float get() = rotation.value

    private val paddingTop = Animatable(initPadding, Dp.VectorConverter)
    val paddingTopDp: Dp get() = paddingTop.value

    private val width = Animatable(initWidth, Dp.VectorConverter)
    val widthDp: Dp get() = width.value

    private val height = Animatable(initHeight, Dp.VectorConverter)
    val heightDp: Dp get() = height.value

    var isRemove = false

    /**
     * 拖动时候的动画 旋转和偏移
     */
    suspend fun startDragAnim(dragOffsetX: Dp, dragOffsetY: Dp) {
        coroutineScope {
            launch { offsetX.snapTo(offsetXDp + dragOffsetX) }
            launch { offsetY.snapTo(offsetYPx + dragOffsetY) }
            launch { rotation.snapTo((dragOffsetX.value / 50) + rotationValue) }
        }
    }


    suspend fun startDragEndAnim() {
        coroutineScope {
            if (abs(offsetXDp.value) > maxOffset.value) {
                startRemoveAnim()
            } else {
                resetOffsetAnim()
            }
        }
    }

    /**
     * 复位的动画
     */
    private suspend fun resetOffsetAnim() {
        coroutineScope {
            val spec: TweenSpec<Float> = tween(duration)
            launch { rotation.animateTo(0f, spec) }
            launch { offsetX.animateTo(0.dp) }
            launch { offsetY.animateTo(0.dp) }
        }
    }

    /**
     * 移除的动画
     */
    private suspend fun startRemoveAnim() {
        coroutineScope {
            val spec: TweenSpec<Float> = tween(600)
            val newRotationValue = if (rotationValue > 0) {
                rotationValue + 20
            } else {
                rotationValue - 20
            }
            val newOffsetX = if (offsetXDp > 0.dp) {
                offsetXDp + (widthDp.times(1.5f))
            } else {
                offsetXDp - (widthDp.times(1.5f))
            }
            launch { rotation.animateTo(newRotationValue, spec) }
            launch { offsetX.animateTo(newOffsetX, tween(1000)) }
        }
    }

}

@Composable
fun SwipeStrangerCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(shape = RoundedCornerShape(20.dp), color = Color.White),
    ) {
        content.invoke()
    }
}

@Composable
fun StrangerCardStack(
    list: List<AnimatedSwipeStrangerCard>,
    cardCount: Int = 5,
    onRemove: (Int) -> Unit = {},
    paddingBetweenCards: Dp = defaultPaddingBetweenItems,
    sizeBetweenCards: Dp = defaultSizeBetweenItems,
    coroutineScope: CoroutineScope
) {

//    val coroutineScope = rememberCoroutineScope()

    val selectedIndexState by remember {
        mutableStateOf(0)
    }




    Box(contentAlignment = Alignment.BottomCenter) {
        list.forEachIndexed { index, anim ->
            SwipeStrangerCard(modifier = Modifier
                .padding(top = anim.paddingTopDp)
                .zIndex(-anim.paddingTopDp.value)
                .offset(x = anim.offsetXDp, y = anim.offsetYPx)
                .rotate(anim.rotationValue)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            coroutineScope.launch {
                                anim.startDragEndAnim()
                                onRemove.invoke(index)
                            }
                        },
                        onDragCancel = {
                            coroutineScope.launch {
                                anim.startDragAnim(0.dp, 0.dp)
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                anim.startDragAnim(dragAmount.x.toDp(), dragAmount.y.toDp())
                            }
                        }
                    )
                }
//                .clickable {
//                    onRemove.invoke(index)
//                }
                .size(anim.widthDp, anim.heightDp)
            ) {
                Image(
                    painterResource(id = anim.bean),
                    contentDescription = "Same Card Type with Different Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }


}

@Composable
fun StrangerCardStackList(beans: MutableList<Int>) {

    val animatedSwipeStrangerCardList = remember { mutableStateListOf<AnimatedSwipeStrangerCard>() }


    val stateResultList by remember {
        derivedStateOf {
            beans.forEach {
                animatedSwipeStrangerCardList.add(
                    AnimatedSwipeStrangerCard(
                        bean = it,
                        initWidth = 355.dp,
                        initHeight = 611.dp
                    )
                )
            }
            animatedSwipeStrangerCardList
        }
    }

    val coroutineScope = rememberCoroutineScope()

    StrangerCardStack(
        list = stateResultList,
        cardCount = stateResultList.size,
        onRemove = {
            beans.removeAt(it)
        },
        coroutineScope = coroutineScope
    )

}

@Composable
@Preview(showBackground = true)
private fun PreStrangerCardStackList() {
    val drawables = remember{
        mutableListOf(
            R.drawable.ic_test_1,
            R.drawable.ic_test_2,
            R.drawable.ic_test_3,
            R.drawable.ic_test_4,
            R.drawable.ic_test_5,
        )
    }

    StrangerCardStackList(drawables)
}
