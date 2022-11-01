package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.colorBlue
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.colorGreen
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.colorOrange
import dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh.yellow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * https://twitter.com/jmtrivedi/status/1517561485622321152?s=20&t=cp99MJHWwc4bosgrDCqbWA
 */
@Composable
fun CoolToolbarControl() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 40.dp)
    ) {
        CoolToolBar()
    }

}

@Composable
fun BoxScope.CoolToolBar() {
    var canAcceptTouchScroll by remember {
        mutableStateOf(true)
    }

    val listState = rememberLazyListState()

    Box(
        Modifier
            .align(Alignment.TopStart)
            .width(CoolToolbarControlSpec.toolbarWidth.dp)
            .height(CoolToolbarControlSpec.toolbarHeight.dp)
            .shadow(12.dp)
            .background(Color.White, shape = RoundedCornerShape(15))
    )

    var swipeOffset by remember {
        mutableStateOf(0f)
    }
    var indexSelected by remember {
        mutableStateOf(0)
    }

    var gestureConsumed by remember {
        mutableStateOf(false)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .height(CoolToolbarControlSpec.toolbarHeight.dp)
            .padding(start = 8.dp), userScrollEnabled = canAcceptTouchScroll
    ) {


        itemsIndexed(toolbarItems) { index, item ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = {
                                indexSelected = index
                                canAcceptTouchScroll = false
                                item.shouldAnimate = true
                                item.onAnimateRequested()
                                swipeOffset = it.y
                            },
                            onDragEnd = {
                                canAcceptTouchScroll = true

                                toolbarItems[indexSelected].shouldAnimate = false
                                toolbarItems[indexSelected].onAnimateRequested()

                                swipeOffset = 0f

                                gestureConsumed = false
                                indexSelected = 0
                            },
                            onDrag = { change, dragAmount ->
                                var isMovingUp = false
                                var isMovingDown = false

                                val sensitivity = 150
                                //dragAmount: positive when scrolling down; negative when scrolling up
                                swipeOffset += dragAmount.y
                                when {
                                    swipeOffset > sensitivity -> {
                                        //offset > 0 when swipe down
                                        if (!gestureConsumed) {
                                            isMovingDown = true
                                            gestureConsumed = true
                                        }
                                    }

                                    swipeOffset < -sensitivity -> {
                                        //offset < 0 when swipe up
                                        if (!gestureConsumed) {
                                            isMovingUp = true
                                            gestureConsumed = true
                                        }
                                    }
                                }


                                when {
                                    isMovingUp -> {
                                        toolbarItems[indexSelected - 1].shouldAnimate = true
                                        toolbarItems[indexSelected - 1].onAnimateRequested()

                                        toolbarItems[indexSelected].shouldAnimate = false
                                        toolbarItems[indexSelected].onAnimateRequested()
                                        gestureConsumed = false
                                        indexSelected -= 1
                                        swipeOffset = 0f
                                    }
                                    isMovingDown -> {
                                        toolbarItems[indexSelected + 1].shouldAnimate = true
                                        toolbarItems[indexSelected + 1].onAnimateRequested()

                                        toolbarItems[indexSelected].shouldAnimate = false
                                        toolbarItems[indexSelected].onAnimateRequested()

                                        gestureConsumed = false
                                        indexSelected += 1
                                        swipeOffset = 0f
                                    }
                                }

                            })
                    }) {
                Spacer(modifier = Modifier.height(4.dp))
                ToolBarItemView(toolbarItemData = item)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ToolBarItemView(toolbarItemData: ToolbarItemData) {
    var shouldAnimate by remember {
        mutableStateOf(toolbarItemData.shouldAnimate)
    }
    val scale = remember {
        androidx.compose.animation.core.Animatable(0.6f)
    }
    val offset by animateFloatAsState(
        targetValue = if (shouldAnimate) CoolToolbarControlSpec.toolbarWidth.toFloat()
            .times(1.1f) else 0f,
        tween(700)
    )

    LaunchedEffect(key1 = Unit, block = {
        scale.animateTo(1f, tween(400))
    })

    LaunchedEffect(key1 = Unit, block = {
        toolbarItemData.changeObserver.receiveAsFlow().collect {
            shouldAnimate = toolbarItemData.shouldAnimate
        }
    })

    Row(
        Modifier
            .scale(scale.value)
            .offset(x = offset.dp)
            .background(toolbarItemData.color, shape = RoundedCornerShape(15))
            .height(CoolToolbarControlSpec.toolbarWidth.times(0.8).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = toolbarItemData.icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(
                start = 16.dp,
                top = 12.dp,
                bottom = 12.dp,
                end = if (shouldAnimate) 8.dp else 16.dp
            )
        )
        AnimatedVisibility(
            visible = shouldAnimate,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut(),
        ) {
            Text(
                text = toolbarItemData.title, style = TextStyle(color = Color.White, fontSize = 16.sp),
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

data class ToolbarItemData(
    val title: String,
    val color: Color,
    val icon: ImageVector
) {
    var shouldAnimate: Boolean = false
    val changeObserver = Channel<Unit>()

    fun onAnimateRequested() {
        changeObserver.trySend(Unit)
    }

}

val toolbarItems = listOf(
    ToolbarItemData(
        title = "Edit",
        color = Color.Green,
        icon = Icons.Default.Edit,
    ),
    ToolbarItemData(
        title = "Delete",
        color = yellow,
        icon = Icons.Default.Delete,
    ),
    ToolbarItemData(
        title = "Comment",
        color = colorOrange,
        icon = Icons.Default.Menu,
    ),
    ToolbarItemData(
        title = "Post",
        color = colorBlue,
        icon = Icons.Default.AddCircle,
    ),
    ToolbarItemData(
        title = "Favorite",
        color = colorGreen,
        icon = Icons.Default.Favorite,
    ),
    ToolbarItemData(
        title = "Shopping",
        color = Color.Magenta,
        icon = Icons.Default.ShoppingCart,
    ),
    ToolbarItemData(
        title = "Edit",
        color = Color.Green,
        icon = Icons.Default.Edit,
    ),
    ToolbarItemData(
        title = "Delete",
        color = yellow,
        icon = Icons.Default.Delete,
    ),
    ToolbarItemData(
        title = "Comment",
        color = colorOrange,
        icon = Icons.Default.Menu,
    ),
    ToolbarItemData(
        title = "Post",
        color = colorBlue,
        icon = Icons.Default.AddCircle,
    ),
    ToolbarItemData(
        title = "Favorite",
        color = colorGreen,
        icon = Icons.Default.Favorite,
    ),
    ToolbarItemData(
        title = "Shopping",
        color = Color.Magenta,
        icon = Icons.Default.ShoppingCart,
    ),

    )

object CoolToolbarControlSpec {
    const val toolbarHeight = 420
    const val toolbarWidth = 70
}

@Composable
fun PrevCoolControl() {
    MaterialTheme {
        CoolToolbarControl()
    }
}