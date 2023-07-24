package com.kraos.querycalendar.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kraos.querycalendar.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * @author kraos
 * @date 2023/7/10
 * @desc 仿探探卡片滑动交互
 */
class SwipeStrangerCardActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawables = mutableStateListOf(
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
                StrangerCardStackList(drawables.take(3), onRemove = {
                    drawables.remove(it)
                }, onFetchData = {
                    drawables.add(R.drawable.ic_launcher_background)
                })
            }
        }
    }
}


private val defaultPaddingBetweenItems = 6.dp
private val defaultSizeBetweenItems = 20.0.dp


/**
 * 单个陌生人卡片的动画管理
 */
@Stable
class AnimatedSwipeStrangerCard(
    val bean: Int,
    //卡片的宽度
    val initWidth: Dp = 0.dp,
    // 偏移最大值
    private val maxOffset: Dp = 100.dp,
    private val duration: Int = 300
) {
    private val offsetX = Animatable(0.dp, Dp.VectorConverter)
    val offsetXDp: Dp get() = offsetX.value
    private val offsetY = Animatable(0.dp, Dp.VectorConverter)
    val offsetYDp: Dp get() = offsetY.value
    private val rotation = Animatable(0f)
    val rotationValue: Float get() = rotation.value

    var isRemove = false

    /**
     * 拖动时候的动画 旋转和偏移
     */
    suspend fun startDragAnim(dragOffsetX: Dp, dragOffsetY: Dp) {
        coroutineScope {
            joinAll(
                launch { offsetX.snapTo(offsetXDp + dragOffsetX) },
                launch { offsetY.snapTo(offsetYDp + dragOffsetY) },
                launch { rotation.snapTo((dragOffsetX.value / 50) + rotationValue) }
            )
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
            joinAll(
                launch { rotation.animateTo(0f, spec) },
                launch { offsetX.animateTo(0.dp) },
                launch { offsetY.animateTo(0.dp) })
        }
    }

    /**
     * 移除的动画
     */
    private suspend fun startRemoveAnim() {
        coroutineScope {
            val spec: TweenSpec<Float> = tween(300)
            val newRotationValue = if (rotationValue > 0) {
                rotationValue + 20
            } else {
                rotationValue - 20
            }
            val newOffsetX = if (offsetXDp > 0.dp) {
                offsetXDp + (initWidth.times(1.5f))
            } else {
                offsetXDp - (initWidth.times(1.5f))
            }
            joinAll(
                launch { rotation.animateTo(newRotationValue, spec) },
                launch { offsetX.animateTo(newOffsetX, tween(300)) }
            )
            isRemove = true
        }
    }

}

/**
 * 单个卡片
 */
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
    onRemove: (AnimatedSwipeStrangerCard) -> Unit = {},
    paddingBetweenCards: Dp = defaultPaddingBetweenItems,
    sizeBetweenCards: Dp = defaultSizeBetweenItems,
    initWidth: Dp,
    initHeight: Dp
) {

    val coroutineScope = rememberCoroutineScope()

    var firstCardOffsetX by remember {
        mutableStateOf(0.dp)
    }

    var firstCardOffsetY by remember {
        mutableStateOf(0.dp)
    }


    Box(contentAlignment = Alignment.BottomCenter) {

        list.forEachIndexed { index, anim ->
            key(anim.bean) {
                val rememberIndex by rememberUpdatedState(newValue = index)

                //根据手势和卡片的index计算出偏移量
                val paddingBottom by remember {
                    derivedStateOf {
                        if (rememberIndex == 0) {
                            0.dp
                        } else {
                            val abs = if (abs((firstCardOffsetX) / 100.dp) >= 1.0f) {
                                1.0f
                            } else {
                                abs((firstCardOffsetX) / 100.dp)
                            }
                            paddingBetweenCards * rememberIndex - paddingBetweenCards * abs
                        }
                    }
                }

                val zIndex by remember {
                    derivedStateOf {
                        if (rememberIndex == 0) {
                            1f
                        } else {
                            -paddingBottom.value
                        }
                    }
                }

                val sizeWith by remember {
                    derivedStateOf {
                        initWidth - paddingBottom * (sizeBetweenCards / paddingBetweenCards)
                    }
                }


                if (index == 0) {
                    SwipeStrangerCard(modifier = Modifier
                        .zIndex(zIndex)
                        .rotate(anim.rotationValue)
                        .offset(x = anim.offsetXDp, y = anim.offsetYDp)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragEnd = {
                                    coroutineScope.launch {
                                        anim.startDragEndAnim()
                                        if (anim.isRemove) {
                                            onRemove.invoke(anim)
                                        }
                                        firstCardOffsetX = 0.dp
                                        firstCardOffsetY = 0.dp
                                    }

                                },
                                onDragCancel = {
                                    firstCardOffsetX = 0.dp
                                    firstCardOffsetY = 0.dp
                                    coroutineScope.launch {
                                        anim.startDragAnim(0.dp, 0.dp)
                                    }
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    firstCardOffsetX += dragAmount.x.toDp()
                                    firstCardOffsetY += dragAmount.y.toDp()

                                    coroutineScope.launch {
                                        Log.d("StrangerCardStack", "onDrag: " + anim.bean)
                                        anim.startDragAnim(
                                            dragAmount.x.toDp(),
                                            dragAmount.y.toDp()
                                        )
                                    }
                                }
                            )
                        }
                        .size(sizeWith, initHeight)
                    ) {
                        Image(
                            painterResource(id = anim.bean),
                            contentDescription = "Same Card Type with Different Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    SwipeStrangerCard(
                        modifier = Modifier
                            .offset(y = -paddingBottom)
                            .zIndex(zIndex)
                            .size(sizeWith, initHeight)
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
    }


}

@Composable
fun StrangerCardStackList(
    beans: List<Int>,
    onRemove: (Int) -> Unit = {},
    onFetchData: () -> Unit = {}
) {


    //通过数据列表派生实际ui列表
    val stateList by remember(beans) {
        derivedStateOf {
            beans.map {
                AnimatedSwipeStrangerCard(
                    it, initWidth = 355.dp,
                )
            }
        }
    }

    //监听列表数量 低于4个时触发获取数据
    LaunchedEffect(Unit) {
        snapshotFlow { stateList.size }.collect {
            onFetchData.invoke()
        }
    }

    StrangerCardStack(
        list = stateList,
        onRemove = {
            onRemove.invoke(it.bean)
        },
        initWidth = 355.dp,
        initHeight = 611.dp
    )

}

@Composable
@Preview(showBackground = true)
private fun PreStrangerCardStackList() {
    val drawables = remember {
        mutableStateListOf(
            R.drawable.ic_test_1,
            R.drawable.ic_test_2,
            R.drawable.ic_test_3,
            R.drawable.ic_test_4,
            R.drawable.ic_test_5,
        )
    }


    StrangerCardStackList(drawables)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        StrangerCardStackList(drawables.take(3), onRemove = {
            drawables.remove(it)
        }, onFetchData = {
            drawables.add(R.drawable.ic_launcher_background)
        })
    }

}
