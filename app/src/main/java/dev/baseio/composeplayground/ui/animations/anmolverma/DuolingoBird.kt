package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode.Reverse
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

val toeColor = Color(230, 149, 53)
val birdGreen = Color(120, 201, 60)
val birdLightGreen = Color(152, 216, 71)
val birdBeak = Color(246, 196, 52)
val birdBeakBelow = Color(230, 149, 53)

@Composable
fun DuolingoBird(modifier: Modifier = Modifier) {

    var scale by remember {
        mutableStateOf(1f)
    }

    val scalingAnim = remember {
        Animatable(scale)
    }

    val repeteableAnim = rememberInfiniteTransition()

    val rotateLeft by repeteableAnim.animateFloat(
        initialValue = 8f, targetValue = -4f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            repeatMode = Reverse
        )
    )

    val beakSpace by repeteableAnim.animateFloat(
        initialValue = 16f, targetValue = 0f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            repeatMode = Reverse
        )
    )

    val eyeBallsMoveLeft by repeteableAnim.animateFloat(
        initialValue = -16f, targetValue = 16f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = Reverse
        )
    )
    val eyeBallsMoveRight by repeteableAnim.animateFloat(
        initialValue = 16f, targetValue = -16f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = Reverse
        )
    )

    val toeRotateLeft by repeteableAnim.animateFloat(
        initialValue = 0f,
        targetValue = 20f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = Reverse
        )
    )
    val toeRotateRight by repeteableAnim.animateFloat(
        initialValue = 0f,
        targetValue = 30f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = Reverse
        )
    )
    val bodyRotateRight by repeteableAnim.animateFloat(
        initialValue = 0f,
        targetValue = 40f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = Reverse
        )
    )

    val bodyTranslationY by repeteableAnim.animateFloat(
        initialValue = 0f,
        targetValue = -40f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = Reverse
        )
    )

    LaunchedEffect(key1 = scale, block = {
        scalingAnim.animateTo(
            scale,
            spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow)
        )
    })

    Box(modifier = modifier
        .clickable {
            scale = if (scale == 1f) 56f else 1f
        }.graphicsLayer(translationY = scalingAnim.value)) {
        BirdToes(toeRotateLeft,toeRotateRight)
        Box(
            modifier = Modifier.graphicsLayer(
                rotationZ = bodyRotateRight,
                translationY = bodyTranslationY
            )
        ) {
            BirdBody(rotateLeft, 0f, eyeBallsMoveLeft, eyeBallsMoveRight, beakSpace)
        }
    }
}

@Composable
private fun BoxScope.BirdToes(toeRotateLeft: Float,toeRotateRight: Float) {
    BirdToes(Modifier.rotate(toeRotateRight).offset(x = 15.dp, y = (40).dp))
    BirdToes(
        Modifier
            .rotate(toeRotateLeft)
            .offset(x = (-30).dp, y = (40).dp)
    )
}

@Composable
private fun BoxScope.BirdBody(
    rotateLeft: Float,
    rotateRight: Float,
    eyeBallsMoveLeft: Float,
    eyeBallsMoveRight: Float,
    beakSpace: Float
) {
    BirdHands(rotateLeft, rotateRight)
    BirdMainShape(Modifier.offset(y = (-120).dp, x = (-80).dp))
    BirdFaceEyeBG(Modifier.offset(y = -120.dp, x = -80.dp))
    BirdEye(Modifier.offset(y = (-75).dp, x = -38.dp), eyeBallsMoveLeft)
    BirdEye(Modifier.offset(y = (-75).dp, x = 25.dp), eyeBallsMoveRight)
    BirdBeak(beakSpace)
    BirdCenterPatch()
}

@Composable
private fun BoxScope.BirdCenterPatch() {
    BirdCenterPatch(Modifier.offset(y = (-115).dp, x = (-70).dp))
    BirdCenterPatch(Modifier.offset(y = (-130).dp, x = (-50).dp))
    BirdCenterPatch(Modifier.offset(y = (-130).dp, x = (-90).dp))
}

@Composable
private fun BoxScope.BirdBeak(beakSpace: Float) {
    BirdBeakTop(Modifier.offset(y = -120.dp, x = -80.dp))
    BirdBreakBelow(
        Modifier
            .offset(y = -118.dp, x = -80.dp)
            .graphicsLayer(translationY = beakSpace)
    )
}

@Composable
private fun BoxScope.BirdHands(rotateLeft: Float, rotateRight: Float) {
    BirdHands(
        Modifier
            .offset(y = (-130).dp, x = (110).dp)
            .mirror()
            .graphicsLayer(rotationZ = rotateLeft),
    )
    BirdHands(
        Modifier
            .offset(y = (-130).dp, x = (-85).dp)
            .graphicsLayer(rotationZ = rotateRight)
    )
}

@Composable
fun BirdBeakTop(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xmul = 1f
            val xoff = 1f
            val ymul = 1f
            val yoff = 1f
            lineTo(255 * xmul + xoff, 249 * ymul + yoff);
            lineTo(222 * xmul + xoff, 241 * ymul + yoff);
            cubicTo(
                225 * xmul + xoff, 209 * ymul + yoff, 276 * xmul + xoff, 212 * ymul + yoff,
                256 * xmul + xoff, 212 * ymul + yoff
            )
            cubicTo(
                285 * xmul + xoff, 216 * ymul + yoff, 293 * xmul + xoff, 241 * ymul + yoff,
                289 * xmul + xoff, 242 * ymul + yoff
            )
            lineTo(255 * xmul + xoff, 249 * ymul + yoff);

            drawPath(this, birdBeak)

        }
    })
}

@Composable
fun BirdBreakBelow(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xmul = 1f
            val xoff = 1f
            val ymul = 1f
            val yoff = 1f
            lineTo(279 * xmul + xoff, 244 * ymul + yoff);
            lineTo(255 * xmul + xoff, 248 * ymul + yoff);
            lineTo(232 * xmul + xoff, 242 * ymul + yoff);
            cubicTo(
                233 * xmul + xoff, 244 * ymul + yoff, 226 * xmul + xoff, 265 * ymul + yoff,
                255 * xmul + xoff, 271 * ymul + yoff
            )
            cubicTo(
                276 * xmul + xoff, 273 * ymul + yoff, 280 * xmul + xoff, 246 * ymul + yoff,
                282 * xmul + xoff, 246 * ymul + yoff
            )

            drawPath(this, birdBeakBelow)

        }
    })
}

@Composable
fun BoxScope.BirdEye(
    modifier: Modifier = Modifier,
    eyeBallsMove: Float
) {

    Canvas(modifier = modifier
        .width(40.dp)
        .height(50.dp)
        .scale(0.8f), onDraw = {
        drawOval(Color.White)
    })

    Canvas(modifier = modifier
        .align(Alignment.Center)
        .width(40.dp)
        .height(50.dp)
        .scale(0.4f)
        .graphicsLayer(translationX = eyeBallsMove), onDraw = {
        drawOval(Color.Black)
    })
}

@Composable
fun BirdFaceEyeBG(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xmul = 1f
            val xoff = 1f
            val ymul = 1f
            val yoff = 1f
            moveTo(232 * xmul + xoff, 245 * ymul + yoff);
            cubicTo(
                175 * xmul + xoff, 328 * ymul + yoff, 109 * xmul + xoff, 257 * ymul + yoff,
                106 * xmul + xoff, 241 * ymul + yoff
            )
            cubicTo(
                102.3142292989963f * xmul + xoff,
                221.34255626131358f * ymul + yoff,
                85 * xmul + xoff,
                137 * ymul + yoff,
                129 * xmul + xoff,
                123 * ymul + yoff
            )
            cubicTo(
                133 * xmul + xoff, 143 * ymul + yoff, 121 * xmul + xoff, 90 * ymul + yoff,
                139 * xmul + xoff, 92 * ymul + yoff
            )
            cubicTo(
                119 * xmul + xoff, 76 * ymul + yoff, 158 * xmul + xoff, 101 * ymul + yoff,
                160 * xmul + xoff, 110 * ymul + yoff
            )
            cubicTo(
                158 * xmul + xoff,
                64 * ymul + yoff,
                179 * xmul + xoff,
                73 * ymul + yoff,
                169 * xmul + xoff,
                77 * ymul + yoff
            )
            cubicTo(
                225 * xmul + xoff, 115 * ymul + yoff, 212 * xmul + xoff, 143 * ymul + yoff,
                255 * xmul + xoff, 145 * ymul + yoff
            )
            cubicTo(
                317 * xmul + xoff, 129 * ymul + yoff, 311 * xmul + xoff, 95 * ymul + yoff,
                342 * xmul + xoff, 78 * ymul + yoff
            )
            cubicTo(
                350 * xmul + xoff, 66 * ymul + yoff, 356 * xmul + xoff, 113 * ymul + yoff,
                357 * xmul + xoff, 110 * ymul + yoff
            )
            cubicTo(
                369 * xmul + xoff,
                79 * ymul + yoff,
                395 * xmul + xoff,
                91 * ymul + yoff,
                375 * xmul + xoff,
                91 * ymul + yoff
            )
            cubicTo(
                385 * xmul + xoff, 84 * ymul + yoff, 389 * xmul + xoff, 130 * ymul + yoff,
                385 * xmul + xoff, 122 * ymul + yoff
            )
            cubicTo(
                394 * xmul + xoff, 119 * ymul + yoff, 421 * xmul + xoff, 158 * ymul + yoff,
                412 * xmul + xoff, 218 * ymul + yoff
            )
            cubicTo(
                399 * xmul + xoff, 285 * ymul + yoff, 358 * xmul + xoff, 281 * ymul + yoff,
                338 * xmul + xoff, 281 * ymul + yoff
            )
            cubicTo(
                318 * xmul + xoff, 281 * ymul + yoff, 288 * xmul + xoff, 269 * ymul + yoff,
                282 * xmul + xoff, 243 * ymul + yoff
            )
            drawPath(this, birdLightGreen)

        }
    })
}

@Composable
fun BirdCenterPatch(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xmul = 1f
            val xoff = 1f
            val ymul = 1f
            val yoff = 1f
            lineTo(192 * xmul + xoff, 340 * ymul + yoff);
            lineTo(245 * xmul + xoff, 340 * ymul + yoff);
            cubicTo(
                250 * xmul + xoff, 340 * ymul + yoff, 238 * xmul + xoff, 362 * ymul + yoff,
                218 * xmul + xoff, 362 * ymul + yoff
            )
            cubicTo(
                198 * xmul + xoff, 362 * ymul + yoff, 195 * xmul + xoff, 340 * ymul + yoff,
                192 * xmul + xoff, 340 * ymul + yoff
            )
            drawPath(this, birdLightGreen)
        }
    })
}

@Composable
fun BirdMainShape(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xmul = 1f
            val xoff = 1f
            val ymul = 1f
            val yoff = 1f
            lineTo(83 * xmul + xoff, 132 * ymul + yoff);
            cubicTo(
                84 * xmul + xoff,
                121 * ymul + yoff,
                59 * xmul + xoff,
                383 * ymul + yoff,
                141 * xmul + xoff,
                411 * ymul + yoff
            )
            cubicTo(
                191 * xmul + xoff, 480 * ymul + yoff, 342 * xmul + xoff, 476 * ymul + yoff,
                395 * xmul + xoff, 391 * ymul + yoff
            )
            cubicTo(
                405.5820258211916f * xmul + xoff,
                374.0288265131832f * ymul + yoff,
                439 * xmul + xoff,
                282 * ymul + yoff,
                429 * xmul + xoff,
                133 * ymul + yoff
            )
            cubicTo(
                437 * xmul + xoff,
                29 * ymul + yoff,
                353 * xmul + xoff,
                44 * ymul + yoff,
                335 * xmul + xoff,
                57 * ymul + yoff
            )
            cubicTo(
                318.7864154320024f * xmul + xoff,
                68.70981107688718f * ymul + yoff,
                278 * xmul + xoff,
                82 * ymul + yoff,
                258 * xmul + xoff,
                82 * ymul + yoff
            )
            cubicTo(
                234 * xmul + xoff,
                93 * ymul + yoff,
                163 * xmul + xoff,
                33 * ymul + yoff,
                124 * xmul + xoff,
                48 * ymul + yoff
            )
            cubicTo(
                49 * xmul + xoff,
                88 * ymul + yoff,
                102 * xmul + xoff,
                145 * ymul + yoff,
                83 * xmul + xoff,
                132 * ymul + yoff
            )
            drawPath(this, birdGreen)
        }
    })
}

@Composable
fun BirdHands(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xmul = 1f
            val xoff = 1f
            val ymul = 1f
            val yoff = 1f

            moveTo(426 * xmul + xoff, 247 * ymul + yoff);
            cubicTo(
                418 * xmul + xoff, 251 * ymul + yoff, 510 * xmul + xoff, 378 * ymul + yoff,
                500 * xmul + xoff, 390 * ymul + yoff
            )
            cubicTo(
                457 * xmul + xoff, 447 * ymul + yoff, 302 * xmul + xoff, 360 * ymul + yoff,
                298 * xmul + xoff, 360 * ymul + yoff
            )

            drawPath(this, color = birdGreen)
        }
    })
}

@Composable
fun BoxScope.BirdToes(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier
        .width(40.dp)
        .height(20.dp), onDraw = {
        drawRoundRect(color = toeColor, cornerRadius = CornerRadius(24f, 24f))
    })
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun PreviewDuolingoBird() {
    MaterialTheme {
        Surface(Modifier.background(Color.Black).fillMaxSize()) {
            Box(modifier = Modifier.background(Color.Black)) {
                DuolingoBird(Modifier.align(Alignment.Center))
            }
        }
    }
}

@Stable
fun Modifier.mirror(): Modifier {
    return this.scale(scaleX = -1f, scaleY = 1f)
}