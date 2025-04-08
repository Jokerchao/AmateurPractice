package com.kraos.querycalendar.compose_widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

/**
 * @author kraos
 * @date 2025/4/8
 */


object AnimatedQueue {

    data class UserEnterInfo(
        val userName: String,
        val avatarUrl: String,
        val vipUrl: String,
        val wealthUrl: String,
        val charmUrl: String
    )

    const val TIME_MILLIS = 1000L // 前置任务超时时间

    /**
     * 通用动画队列组件，负责按顺序显示内容
     *
     * @param queue 待显示的内容队列
     * @param modifier 修饰符
     * @param enter 进入动画
     * @param exit 退出动画
     * @param preTaskTimeoutMillis 前置任务超时时间
     * @param onPreTask 前置任务（返回 true 表示继续显示，false 表示跳过）
     * @param onEnterAnimationComplete 进入动画完成回调
     * @param onExitAnimationComplete 退出动画完成回调
     * @param content 内容展示的 Composable，接收结束回调
     */
    @Composable
    fun <T> Content(
        queue: SnapshotStateList<T>,
        modifier: Modifier = Modifier,
        enter: EnterTransition = fadeIn() + expandIn(),
        exit: ExitTransition = fadeOut() + shrinkOut(),
        preTaskTimeoutMillis: Long = TIME_MILLIS,
        onPreTask: suspend (item: T) -> Boolean = { true },
        onEnterAnimationComplete: () -> Unit = {},
        onExitAnimationComplete: () -> Unit = {},
        content: @Composable (item: T, onDisplayEnd: () -> Unit) -> Unit,
    ) {
        var currentItem by remember { mutableStateOf<T?>(null) } // 当前显示的内容
        val visibleState = remember { MutableTransitionState(false) } // 动画开关
        var isAnimating by remember { mutableStateOf(false) } // 动画进行中标记
        val scope = rememberCoroutineScope() // 协程作用域


        // 监听队列和动画状态
        LaunchedEffect(queue.size, isAnimating) {
            if (queue.isNotEmpty() && !isAnimating) {
                try {
                    val item = queue.removeAt(0)
                    val shouldProceed = withContext(scope.coroutineContext) {
                        withTimeout(preTaskTimeoutMillis) { onPreTask(item) }
                    }
                    if (shouldProceed) {
                        currentItem = item
                        visibleState.targetState = true// 触发进入动画
                        isAnimating = true
                    } else {
                        isAnimating = false
                    }
                } catch (e: Exception) {
                    isAnimating = false
//                    handleException(e, currentItem)
                }
            }
        }

        // 监听动画完成
        LaunchedEffect(visibleState.currentState, visibleState.isIdle) {
            if (visibleState.isIdle) {
                if (visibleState.currentState) {
                    onEnterAnimationComplete() // 进入动画结束
                } else {
                    currentItem = null
                    isAnimating = false// 退出动画结束，重置
                }
            }
        }

        currentItem?.let { item ->
            AnimatedVisibility(
                visibleState = visibleState, modifier = modifier, enter = enter, exit = exit
            ) {
                content(item) {
                    visibleState.targetState = false// 触发退出动画
                    onExitAnimationComplete()
                }
            }
        }
    }

    @Composable
    fun UserEnterRoomWidget(
        userName: String = "User Name",
        avatarUrl: String = "https://example.com/avatar.png",
        vipUrl: String = "https://example.com/vip.png",
        wealthUrl: String = "https://example.com/wealth.png",
        charmUrl: String = "https://example.com/charm.png"
    ) {
        // 用户进入房间的 UI 组件
        Column(
            modifier = Modifier
                .background(Color.Blue)
                .padding(16.dp)
        ) {
            // 显示用户信息
            Text(text = userName)
            // 显示其他信息（头像、VIP、财富、魅力等）
        }
    }
}

@Preview(locale = "en")
@Composable
fun UserEnterRoomWidgetPreview() {
    // 用户队列
    val userQueue = remember { mutableStateListOf<AnimatedQueue.UserEnterInfo>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(Modifier.height(40.dp))
        Button(onClick = {
            // 模拟用户进入，添加到队列
            userQueue.add(AnimatedQueue.UserEnterInfo("User ${(0..100).random()}", "", "", "", ""))
        }) {
            Text("模拟用户进入")
        }
        val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
        AnimatedQueue.Content(
            queue = userQueue,
            onPreTask = {
                true
            },
            onEnterAnimationComplete = {
            },
            onExitAnimationComplete = {
            },
            // 尝试自己更改动画效果！
            enter = slideInHorizontally(
                animationSpec = tween(500),
                initialOffsetX = { if (isRtl) -it else it }),
            exit = slideOutHorizontally(
                animationSpec = tween(500),
                targetOffsetX = { if (isRtl) it else -it })
        ) { user, onDisplayEnd ->
            // 自定义显示逻辑，可以：
            // 1. 根据用户属性设置不同显示时长
            LaunchedEffect(user) {
                delay(1000L)
                onDisplayEnd()
            }

            // 2. 或者通过按钮点击结束
            AnimatedQueue.UserEnterRoomWidget(
                userName = user.userName,
                avatarUrl = user.avatarUrl,
                vipUrl = user.vipUrl,
                wealthUrl = user.wealthUrl,
                charmUrl = user.charmUrl,
//                    onCloseClick = onDisplayEnd,
                // 其他参数...
            )
        }

//        SimpleQueue(
//            queue = userQueue,
//            onPreTask = {
//                // 前置任务，返回 false 表示跳过
//
//
////                delay(1000)
//                true
//            }
//        ) { user, onDisplayEnd ->
//            // 自动关闭示例
//            LaunchedEffect(user) {
//                delay(1000L)
//                onDisplayEnd()
//            }
//
//            // 或者手动关闭示例
//            UserEnterRoomWidget(
//                userName = user.userName,
//                avatarUrl = user.avatarUrl,
//                vipUrl = user.vipUrl,
//                wealthUrl = user.wealthUrl,
//                charmUrl = user.charmUrl,
////                onCloseClick = onDisplayEnd,
//                // 其他参数...
//            )
//        }
    }
}
