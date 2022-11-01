package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.baseio.common.PainterRes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun MacOsxDock() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = PainterRes.applewall(),
            contentDescription = null,
            Modifier.fillMaxSize(), contentScale = ContentScale.Crop
        )
        DockBox()

    }

}

@Composable
private fun BoxScope.DockBox() {
    BGBlur()
    IconsRow()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BoxScope.IconsRow() {
    var canAcceptTouchScroll by remember {
        mutableStateOf(true)
    }

    val listState = rememberLazyListState()
    var swipeOffset by remember {
        mutableStateOf(0f)
    }
    var indexSelected by remember {
        mutableStateOf(0)
    }
    val hapticFeedback = LocalHapticFeedback.current

    var gestureConsumed by remember {
        mutableStateOf(false)
    }
    LazyRow(
        state = listState,
        modifier =
        Modifier.Companion
            .align(Alignment.BottomCenter)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        userScrollEnabled = canAcceptTouchScroll
    ) {
        itemsIndexed(icons) { index, item ->
            DockIcon(item, Modifier
                .onPointerEvent(PointerEventType.Enter) {
                    indexSelected = index
                    item.shouldAnimate = true
                    item.onAnimateRequested()
                }
                .onPointerEvent(PointerEventType.Exit) {
                    indexSelected = 0
                    item.shouldAnimate = false
                    item.onAnimateRequested()
                }
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = {
                            indexSelected = index
                            canAcceptTouchScroll = false
                            item.shouldAnimate = true
                            item.onAnimateRequested()
                            swipeOffset = it.x
                        },
                        onDragEnd = {
                            canAcceptTouchScroll = true

                            disableAnimationForIndex(hapticFeedback)

                            swipeOffset = 0f

                            gestureConsumed = false
                            indexSelected = 0
                        },
                        onDrag = { change, dragAmount ->
                            var isMovingLeft = false
                            var isMovingRight = false

                            val sensitivity = 80
                            swipeOffset += dragAmount.x
                            when {
                                swipeOffset > sensitivity -> {
                                    if (!gestureConsumed) {
                                        isMovingRight = true
                                        gestureConsumed = true
                                    }
                                }

                                swipeOffset < -sensitivity -> {
                                    if (!gestureConsumed) {
                                        isMovingLeft = true
                                        gestureConsumed = true
                                    }
                                }
                            }


                            when {
                                isMovingLeft -> {
                                    if (indexSelected == 0) {
                                        return@detectDragGesturesAfterLongPress
                                    }
                                    enableAnimationIndex(hapticFeedback, indexSelected)

                                    gestureConsumed = false
                                    indexSelected -= 1
                                    swipeOffset = 0f
                                }

                                isMovingRight -> {
                                    if (indexSelected == icons.size.minus(1)) {
                                        return@detectDragGesturesAfterLongPress
                                    }
                                    enableAnimationIndex(hapticFeedback, indexSelected)

                                    gestureConsumed = false
                                    indexSelected += 1
                                    swipeOffset = 0f
                                }
                            }


                        })
                })
        }
    }
}

private fun enableAnimationIndex(
    hapticFeedback: HapticFeedback,
    indexSelected: Int
) {
    disableAnimationForIndex(hapticFeedback)
    icons[indexSelected].shouldAnimate = true
    icons[indexSelected].zIndex = 1000f
    icons[indexSelected].onAnimateRequested()
    hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
}

private fun disableAnimationForIndex(hapticFeedback: HapticFeedback) {
    icons.forEach {
        it.shouldAnimate = false
        it.onAnimateRequested()
    }
    hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
}

@Composable
private fun BoxScope.BGBlur() {
    Box(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(
                Color.White.copy(alpha = 0.4f),
                shape = RoundedCornerShape(30)
            )
            .height(48.dp)
            .padding(horizontal = 12.dp)
            .blur(12.dp)
    )
}

@Composable
fun DockIcon(icon: MyDockIcon, modifier: Modifier) {
    var shouldAnimate by remember {
        mutableStateOf(icon.shouldAnimate)
    }

    val zIndex by animateFloatAsState(targetValue = if (shouldAnimate) icon.zIndex else 1f)
    val bottomOffset by animateDpAsState(targetValue = if (shouldAnimate) (-8).dp else 0.dp)
    val scale by animateFloatAsState(
        targetValue = if (shouldAnimate) icon.scaledValue else 1f,
        tween(350)
    )

    LaunchedEffect(key1 = Unit, block = {
        icon.changeObserver.receiveAsFlow().collect {
            shouldAnimate = icon.shouldAnimate
        }
    })

    Image(
        painter = icon.drawable.invoke(),
        contentDescription = null,
        modifier = modifier
            .size(48.dp)
            .zIndex(zIndex)
            .scale(scale)
            .offset(y = bottomOffset)
    )
}

data class MyDockIcon(var drawable: @Composable () -> Painter) {
    var scaledValue: Float = 2f
    var zIndex = 100f
    var shouldAnimate: Boolean = false
    val changeObserver = Channel<Unit>()

    fun onAnimateRequested() {
        changeObserver.trySend(Unit)
    }
}

val icons = listOf(
    MyDockIcon { PainterRes.keychain() },
    MyDockIcon { PainterRes.news() },
    MyDockIcon { PainterRes.safari() },
    MyDockIcon { PainterRes.timemachine() },
    MyDockIcon { PainterRes.systempreferences() },
    MyDockIcon { PainterRes.colorsync() },
    MyDockIcon { PainterRes.migration() },
    MyDockIcon { PainterRes.quicktime() },
    MyDockIcon { PainterRes.contacts() },
    MyDockIcon { PainterRes.dictionary() },
    MyDockIcon { PainterRes.music() },
    MyDockIcon { PainterRes.stocks() },
    MyDockIcon { PainterRes.keychain() },
    MyDockIcon { PainterRes.news() },
    MyDockIcon { PainterRes.safari() },
    MyDockIcon { PainterRes.timemachine() },
    MyDockIcon { PainterRes.systempreferences() },
    MyDockIcon { PainterRes.colorsync() },
    MyDockIcon { PainterRes.migration() },
    MyDockIcon { PainterRes.quicktime() },
    MyDockIcon { PainterRes.contacts() },
    MyDockIcon { PainterRes.dictionary() },
    MyDockIcon { PainterRes.music() },
    MyDockIcon { PainterRes.stocks() },
)


@Composable
fun PreviewMacOsxDock() {
    MaterialTheme {
        MacOsxDock()
    }
}
