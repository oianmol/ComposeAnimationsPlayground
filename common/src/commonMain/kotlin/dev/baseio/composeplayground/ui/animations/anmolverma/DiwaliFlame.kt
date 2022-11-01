package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

// Inspired by https://codepen.io/oianmol/pen/oNyvZPx
@Composable
fun DiwaliFlame() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .layoutId("root")
    ) {
        Container(Modifier.align(Alignment.Center).offset(x = 30.dp)) {
            flames.forEach { flameInfo ->
                if (flameInfo.isCircle) {
                    Circle(
                        Modifier
                            .offset(x = flameInfo.left, y = flameInfo.bottom ?: 0.dp), flameInfo
                    )
                } else {
                    Flame(
                        Modifier
                            .offset(x = flameInfo.left, y = flameInfo.bottom ?: 0.dp), flameInfo
                    )
                }
            }
        }
    }
}

@Composable
fun BoxScope.Circle(modifier: Modifier, flameInfo: FlameInfo) {
    Box(
        modifier = modifier
            .size(flameInfo.size)
            .graphicsLayer {
                rotationZ = -45f
                scaleX = 1.5f
                scaleY = 1.5f
                transformOrigin = TransformOrigin(0.5f, 1f)
                renderEffect = BlurEffect(flameInfo.blur, flameInfo.blur, TileMode.Decal)
            }

    ) {
        Box(
            modifier = Modifier
                .size(flameInfo.size)
                .background(
                    flameInfo.bg, RoundedCornerShape(
                        bottomEndPercent = 50,
                        bottomStartPercent = 50,
                        topStartPercent = 50
                    )
                )
        )
    }

}

@Composable
fun BoxScope.Flame(modifier: Modifier, flameInfo: FlameInfo) {
    Box(
        modifier
            .graphicsLayer {
                rotationZ = -45f
                scaleX = 1.5f
                scaleY = 1.5f
                transformOrigin = TransformOrigin(0.5f, 1f)
                renderEffect = BlurEffect(flameInfo.blur, flameInfo.blur, TileMode.Decal)
            }
    ) {
        Box(
            modifier = Modifier
                .size(flameInfo.size)
                .background(
                    flameInfo.bg, RoundedCornerShape(
                        bottomEndPercent = 50,
                        bottomStartPercent = 50,
                        topStartPercent = 50
                    )
                )
        )
    }
}

@Composable
private fun Container(modifier: Modifier, child: @Composable BoxScope.() -> Unit) {
    val infinite = rememberInfiniteTransition()
    val flickerRotate by infinite.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = InfiniteRepeatableSpec(animation = keyframes {
            durationMillis = 300
            delayMillis = 100
            -1f at (durationMillis * 0f).roundToInt()
            1f at (durationMillis * 0.2f).roundToInt()
            -1f at (durationMillis * 0.4f).roundToInt()
            1f at (durationMillis * 0.6f).roundToInt()
            -2f at (durationMillis * 0.8f).roundToInt()
            1f at (durationMillis * 1f).roundToInt()
        }, repeatMode = RepeatMode.Reverse)
    )
    val flickerScale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1f,
        animationSpec = InfiniteRepeatableSpec(animation = keyframes {
            durationMillis = 300
            delayMillis = 100
            1f at (durationMillis * 0f).roundToInt()
            1f at (durationMillis * 0.2f).roundToInt()
            1f at (durationMillis * 0.4f).roundToInt()
            1.04f at (durationMillis * 0.6f).roundToInt()
            0.92f at (durationMillis * 0.8f).roundToInt()
            1f at (durationMillis * 1f).roundToInt()
        }, repeatMode = RepeatMode.Reverse)
    )
    Box(
        modifier
            .layoutId("container")
            .size(60.dp)
            .graphicsLayer(
                rotationZ = flickerRotate,
                scaleX = flickerScale,
                scaleY = flickerScale,
                transformOrigin = TransformOrigin(0.5f, 1f)
            )
    ) {
        child()
    }
}

data class FlameInfo(
    val left: Dp,
    val bottom: Dp? = null,
    val size: Dp,
    val bg: Color,
    val blur: Float,
    val spread: Float,
    val isCircle: Boolean = false
)

val flames = mutableListOf<FlameInfo>().apply {
    add(
        FlameInfo(
            left = 5.dp,
            size = 50.dp,
            bg = Color(0xffFF4500),
            blur = 5f,
            spread = 4f,
        )
    )//red orange
    add(
        FlameInfo(
            left = 5.dp,
            size = 40.dp,
            bg = Color(0xffFFA500),
            blur = 9f,
            spread = 4f,
            bottom = 12.dp
        )
    )//orange
    add(
        FlameInfo(
            left = 5.dp,
            size = 30.dp,
            bg = Color(0xffFFD700),
            blur = 9f,
            spread = 4f,
            bottom = 24.dp
        )
    )//gold
    add(
        FlameInfo(
            left = 5.dp,
            size = 30.dp,
            bg = Color(0xffFFFFFF),
            blur = 9f,
            spread = 4f,
            bottom = 24.dp,
        )
    ) //white
    add(
        FlameInfo(
            left = 5.dp,
            bottom = (55).dp,
            size = 10.dp,
            bg = Color(0xff6A5ACD),
            blur = 15f,
            spread = 10f, isCircle = true
        )
    ) //blue
    add(
        FlameInfo(
            left = 5.dp,
            bottom = (70).dp,
            size = 40.dp,
            bg = Color(0xff000000),
            blur = 15f,
            spread = 10f, isCircle = true
        )
    ) //blue
}


@Composable
fun PreviewDiwaliFrame() {
    DiwaliFlame()
}

