package com.kraos.querycalendar.activity

import Orientation.Horizontal
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.LifecycleRegistry
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.kraos.querycalendar.R
import com.kraos.querycalendar.view.TestLearn
import com.kraos.querycalendar.view.advancedShadow
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.roundToInt

class ComposeTestActivity : BaseActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LifecycleRegistry(this)
        setContent {
            val testList = listOf("主播榜", "作品榜", "榮譽榜", "其它榜單")
            HorizontalPager(count = 9) { page ->
                when (page) {
                    0 -> TestRank(testList)
                    1 -> TestLearn("明日方舟")
                    2 -> TestAnimation()
                    3 -> TestAnimatedVisibility()
                    4 -> TestCustomModifier()
                    5 -> TestComposeDialogParent()
                    6 -> TestParentDataModifier()
                    7 -> TestSnapshotFlow()
                    8 -> TestCustomComposeDraw()
                }
            }
        }
    }
}

@Composable
fun CustomLayout(modifier: Modifier = Modifier,content: @Composable () -> Unit) {
    Layout(content) { measurables, constraints ->
        var width = 0
        var height = 0
        val placeables = measurables.map {measurable ->
            measurable.measure(constraints).also { placeable ->
                width = max(width,placeable.width)
                height += placeable.height
            }
        }
        layout(width,height){
            var totalHeight = 0
            placeables.forEach {
                it.placeRelative(0,totalHeight)
                totalHeight += it.height
            }
        }
    }
}

@Composable
@Preview
fun TestCustomComposeDraw() {
    val image = ImageBitmap.imageResource(R.drawable.ic_test_1)
    val paint by remember { mutableStateOf(Paint()) }
    val camera by remember { mutableStateOf(android.graphics.Camera()) }
    val rotateAnim = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        rotateAnim.animateTo(360f, infiniteRepeatable(tween(2000)))
    }
    LookaheadScope() {

    }
    //Canvas=Spacer +  drawBehind
    Column {
        Text(text = "Kraos", modifier = Modifier.drawBehind {
            drawRect(Color.Red)
        })
//        Canvas(Modifier.size(100.dp).graphicsLayer {
//            //RenderNode (API 21)
//            rotationX = 45f
//        }) {
//            rotateRad(3f){
//                drawImage(image, dstSize = IntSize(size.width.toInt(), size.height.toInt()))
//            }
//        }
        Canvas(
            Modifier
                .size(100.dp)
        ) {
            drawIntoCanvas {
                it.translate(size.width / 2, size.height / 2f)
                it.rotate(-45f)
                camera.save()
                camera.rotateX(rotateAnim.value)
                camera.applyToCanvas(it.nativeCanvas)
                camera.restore()
                it.rotate(45f)
                it.translate(-size.width / 2, -size.height / 2f)
                it.drawImageRect(
                    image,
                    dstSize = IntSize(size.width.toInt(), size.height.toInt()),
                    paint = paint
                )
            }
        }
        CustomLayout {
            Text(text = "Kraos", color = Color.Red)
            Text(text = "Kraos", color = Color.Blue)
            Text(text = "Kraos", color = Color.Green)
        }

        val interactionSource = remember { MutableInteractionSource() }
        var textOffsetX by remember { mutableFloatStateOf(0f) }

        //自定义触摸
        Text(
            text = "触摸事件测试",
            modifier = Modifier.offset {
                IntOffset(textOffsetX.roundToInt(), 0)
            }.draggable(
                state = rememberDraggableState(onDelta = { delta ->
                    println("Kraos:delta:$delta")
                    textOffsetX += delta
                }),
                orientation = Orientation.Horizontal,
                interactionSource = interactionSource
            )
        )

        val isDragged = interactionSource.collectIsDraggedAsState()
        Text(text = "isDragged:${isDragged.value}")

    }

}


@Composable
@Preview
fun TestSnapshotFlow() {
    Column {
        var name by remember { mutableStateOf("Kraos") }
        //将任意多个state转换成一个flow
        val flow = snapshotFlow {
            name
        }
        Text(text = name)
        Button(onClick = { name += "1" }) {
            Text(text = "切换", fontSize = 20.sp)
        }
        LaunchedEffect(Unit) {
            flow.collect {
                println(it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestPopUp() {
    val offsetY = with(LocalDensity.current){
        -60.dp.roundToPx()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)

    ) {
        Popup(
            alignment = Alignment.TopCenter,
            offset = IntOffset(0, offsetY),
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Red)
                    .size(120.dp,60.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Popup message", fontSize = 15.sp)
            }
        }
    }
}

@Composable
@Preview
fun TestParentDataModifier() {
//    val state = produceState(initialValue = 1){
//        //这里的代码是协程的环境 在协程中更新该state的值
//        delay(1000)
//        value = 2
//    }
//    Text(text = state.value.toString())

    Row {
        SideEffect {
            println("Kraos:TestParentDataModifier")
        }

        DisposableEffect(Unit) {
            println("Kraos:进入界面啦")
            onDispose {
                //离开界面的通知回调
                println("Kraos:离开界面啦")
            }
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = Color.Red)
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = Color.Green)
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = Color.Blue)
        )
        //给组件设置一个独特的标签
        Modifier.layoutId("Kraos")

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun TestScaffold() {
    // 1. Create the TopAppBarScrollBehavior
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My App")
                },
                // 2. Provide scrollBehavior to TopAppBar
                scrollBehavior = scrollBehavior
            )
        },
        // 3. Connect the scrollBehavior.nestedScrollConnection to the Scaffold
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { _ ->
        /* Contents */
        Column {
            (0..100).forEach {
                Text(text = "Kraos")
            }
        }

    }

}

@Composable
@Preview
private fun TestComposeDialogParent(
    modifier: Modifier = Modifier
) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            )
        ) {
            Box {
                TestComposeDialog(
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .aspectRatio(335f / 445f)
                        .background(Color.White)
                ) {
                    showDialog = false
                }
            }
        }
    }


    Box(modifier = modifier.fillMaxSize()) {
        Button(onClick = { showDialog = true }) {
            Text(text = "Show Dialog")
        }
    }

}


@Composable
@Preview
private fun PreTestComposeDialog(
) {
    Box {
        TestComposeDialog(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .aspectRatio(335f / 445f)
                .background(Color.White)
        ) {

        }
    }
}


@Composable
private fun TestComposeDialog(
    modifier: Modifier = Modifier,
    onClosed: () -> Unit = {}
) {
    ConstraintLayout(modifier = modifier) {
        val (bg, box, button) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(box) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(Color.White)
                .padding(20.dp)
        ) {
            Text(text = "Compose Dialog")
        }

        Box(
            modifier = Modifier
                .constrainAs(bg) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    linkTo(start = parent.start, end = parent.end)
                    width = Dimension.percent(0.8f)
                    height = Dimension.ratio("16:9")
                }
                .background(Color.Gray.copy(alpha = 0.5f))
        )

        Button(
            onClick = onClosed,
            modifier = Modifier
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(20.dp)
        ) {
            Text(text = "close")
        }
    }
}

/**
 * 自定义Modifier的Layout练习
 */
@Composable
@Preview
fun TestCustomModifier() {
    Column(Modifier.background(Color.Blue)) {
        //有点类似于自定义Layout的方式,只是改吧Layout布局的方式,不影响内部元素
        Text(text = "Kraos", Modifier.layout { measurable, constraints ->
            val paddingX = 10.dp.roundToPx()
            val paddingY = 5.dp.roundToPx()
            //测量内部元素尺寸
            val placeable = measurable.measure(
                constraints.copy(
                    maxWidth = constraints.maxWidth - paddingX * 2,
                    maxHeight = constraints.maxHeight - paddingY * 2
                )
            )

            //给定自身尺寸
            layout(placeable.width + paddingX * 2, placeable.height + paddingY * 2) {
                placeable.placeRelative(paddingX, paddingY)
            }
        })
        Text(
            text = "Kraos",
            Modifier
                .padding(10.dp)
                .padding(10.dp)
                .background(Color.Red)
        )
    }
}

/**
 * AnimatedVisibility练习
 */
@Composable
@Preview
fun TestAnimatedVisibility() {
    Column {
        var shown by remember { mutableStateOf(true) }
        AnimatedVisibility(visible = shown, enter = scaleIn()) {
            TestTransition()
        }
        Button(onClick = { shown = !shown }) {
            Text(text = "切换", fontSize = 20.sp)
        }
    }
}

@Composable
@Preview
fun TestTransition() {
    var big by remember { mutableStateOf(false) }
    val bigTransition = updateTransition(targetState = big, label = "bigTransition")
    val size by bigTransition.animateDp(label = "size") {
        if (it) 96.dp else 48.dp
    }
    val corner by bigTransition.animateDp(label = "corner") {
        if (it) 0.dp else 18.dp
    }

    var shown by remember { mutableStateOf(true) }

    Column {
        AnimatedVisibility(visible = shown) {
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(RoundedCornerShape(corner))
                    .background(Color.Blue)
                    .clickable {
                        big = !big
                    }
            )
        }

        Button(onClick = { shown = !shown }) {
            Text(text = "切换", fontSize = 20.sp)
        }

    }


}

@Composable
@Preview
fun TestAnimation() {
    var big by remember { mutableStateOf(false) }
    val size by animateDpAsState(targetValue = if (big) 96.dp else 48.dp, label = "size")
    val anim = remember { Animatable(size, Dp.VectorConverter) }
    val decayAnim = remember { Animatable(0.dp, Dp.VectorConverter) }
    val decay = rememberSplineBasedDecay<Dp>()

    LaunchedEffect(big) {
        anim.animateTo(
            size,
            keyframes {
                144.dp at 400
                durationMillis = 1000
            }
        )
    }

    LaunchedEffect(Unit) {
        delay(1000)
        decayAnim.animateDecay(1000.dp, decay)
    }


    Column() {
        Box(Modifier.size(200.dp), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(anim.value)
                    .background(Color.Green)
                    .clickable {
                        big = !big
                    }
            )
        }
        Box(
            modifier = Modifier
                .padding(0.dp, decayAnim.value, 0.dp, 0.dp)
                .size(100.dp)
                .background(Color.Red)
        )
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


