/*
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
            painter = painterResource(id = R.drawable.applewall),
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
        userScrollEnabled = canAcceptTouchScroll
    ) {
        itemsIndexed(icons) { index, item ->
            DockIcon(item, Modifier.pointerInput(Unit) {
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
            .align(Alignment.BottomCenter)
            .background(
                Color.White.copy(alpha = 0.4f),
                shape = RoundedCornerShape(30)
            )
            .height(48.dp)
            .fillMaxWidth()
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
        painter = painterResource(id = icon.drawable),
        contentDescription = null,
        modifier = modifier
            .size(48.dp)
            .zIndex(zIndex)
            .scale(scale)
            .offset(y = bottomOffset)
    )
}

data class MyDockIcon(var drawable: Int) {
    var scaledValue: Float = 2f
    var zIndex = 1f
    var shouldAnimate: Boolean = false
    val changeObserver = Channel<Unit>()

    fun onAnimateRequested() {
        changeObserver.trySend(Unit)
    }
}

val icons = listOf(
    MyDockIcon(R.drawable.keychain),
    MyDockIcon(R.drawable.news),
    MyDockIcon(R.drawable.safari),
    MyDockIcon(R.drawable.timemachine),
    MyDockIcon(R.drawable.systempreferences),
    MyDockIcon(R.drawable.colorsync),
    MyDockIcon(R.drawable.migration),
    MyDockIcon(R.drawable.quicktime),
    MyDockIcon(R.drawable.contacts),
    MyDockIcon(R.drawable.dictionary),
    MyDockIcon(R.drawable.music),
    MyDockIcon(R.drawable.stocks),
    MyDockIcon(R.drawable.grapher),
    MyDockIcon(R.drawable.messages),
    MyDockIcon(R.drawable.siri),
    MyDockIcon(R.drawable.findmy),
    MyDockIcon(R.drawable.imagecapture),
    MyDockIcon(R.drawable.calendar),
    MyDockIcon(R.drawable.calculator),
    MyDockIcon(R.drawable.launchpad),
    MyDockIcon(R.drawable.screenshot),
    MyDockIcon(R.drawable.maps),
    MyDockIcon(R.drawable.diskutility),
    MyDockIcon(R.drawable.feedbackassistant),
    MyDockIcon(R.drawable.mail),
    MyDockIcon(R.drawable.facetime),
    MyDockIcon(R.drawable.textedit),
    MyDockIcon(R.drawable.scripteditor),
    MyDockIcon(R.drawable.systeminfo),
    MyDockIcon(R.drawable.tv),
    MyDockIcon(R.drawable.stickies),
    MyDockIcon(R.drawable.reminders),
    MyDockIcon(R.drawable.midi),
    MyDockIcon(R.drawable.automator),
    MyDockIcon(R.drawable.home),
    MyDockIcon(R.drawable.appstore),
    MyDockIcon(R.drawable.voicememos),
    MyDockIcon(R.drawable.bluetoothfile),
    MyDockIcon(R.drawable.activitymonitor),
    MyDockIcon(R.drawable.colormeter),
    MyDockIcon(R.drawable.voiceover),
    MyDockIcon(R.drawable.airportutility),
    MyDockIcon(R.drawable.bootcamp),
    MyDockIcon(R.drawable.notes),
    MyDockIcon(R.drawable.missioncontrol),
    MyDockIcon(R.drawable.terminal),
    MyDockIcon(R.drawable.console),
    MyDockIcon(R.drawable.books),
    MyDockIcon(R.drawable.fontbook),
    MyDockIcon(R.drawable.chess),
    MyDockIcon(R.drawable.podcasts),
    MyDockIcon(R.drawable.preview),
    MyDockIcon(R.drawable.photos),
    MyDockIcon(R.drawable.photobooth),
)


@Preview
@Composable
fun PreviewMacOsxDock() {
    MaterialTheme {
        MacOsxDock()
    }
}*/
