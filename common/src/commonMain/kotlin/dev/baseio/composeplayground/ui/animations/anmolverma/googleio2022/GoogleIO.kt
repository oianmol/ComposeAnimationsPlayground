package dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import dev.baseio.composeplayground.ui.animations.anmolverma.drawText
import kotlinx.coroutines.launch

val ioBGColor = Color(254, 254, 254)
val colorBlue = Color(82, 146, 247)
val colorYellow = Color(245, 194, 66)
val colorGreen = Color(91, 176, 103)
val colorOrange = Color(236, 119, 83)
val black = Color(58, 63, 64)

@Composable
fun GoogleIO() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ioBGColor)
    ) {


        IO22()

    }
}

@Composable
fun BoxScope.IO22() {
    var start by remember {
        mutableStateOf(false)
    }
    val scaleFirstFrame by animateFloatAsState(
        targetValue = if (start) 3f else 0f,
        tween(1000, delayMillis = 0)
    )

    val googleIoScale by animateFloatAsState(
        targetValue = if (start) 2f else 0f,
        tween(1000, delayMillis = 3500)
    )


    val iHeight = remember {
        Animatable(0f)
    }

    val offsetCircle = remember {
        Animatable(180f)
    }

    val circleEndScale = remember {
        Animatable(0f)
    }


    val scaleEarth = remember {
        Animatable(0f)
    }

    val earthX = remember {
        Animatable(0f)
    }

    val translationXI by animateDpAsState(
        targetValue = if (start) (-280).dp else 0.dp,
        tween(2000, delayMillis = 0)
    )

    val translationXSlash by animateDpAsState(
        targetValue = if (start) (-160).dp else 0.dp,
        tween(2000, delayMillis = 0)
    )

    val transTwoFirst by animateDpAsState(
        targetValue = if (start) (120).dp else 0.dp,
        tween(2000, delayMillis = 0)
    )

    val transTwoSecond by animateDpAsState(
        targetValue = if (start) (260).dp else 0.dp,
        tween(2000, delayMillis = 0)
    )


    val showGoogleIO = remember {
        Animatable(0f)
    }




    LaunchedEffect(key1 = Unit, block = {
        start = true
        launch {
            scaleEarth.animateTo(3.5f, tween(1000, delayMillis = 150))
            scaleEarth.animateTo(5.0f, tween(1000, delayMillis = 500))
            launch {
                earthX.animateTo(300f, tween(1000, delayMillis = 500))
            }
            launch {
                offsetCircle.animateTo(310f, tween(2000, delayMillis = 500))
            }
            launch {
                circleEndScale.animateTo(2f, tween(700, delayMillis = 500))
            }
            launch {
                scaleEarth.animateTo(0f, tween(1000, delayMillis = 500))
            }
            launch {
                showGoogleIO.animateTo(1f, tween(1000, delayMillis = 800))
            }
            launch {
                iHeight.animateTo(4f, tween(2000, delayMillis = 500))
            }
        }

    })

    ColoredCircle(colorYellow, 1000)
    ColoredCircle(colorOrange, 1500)

    // I
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .offset(translationXI)
            .height(72.dp)
            .width(32.dp)
            .scale(scaleFirstFrame)
            .background(colorBlue, shape = RectangleShape)
            .border(1.dp, color = black, shape = RectangleShape)
    )

    // /

    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .offset(translationXSlash)
            .height(82.dp)
            .rotate(12f)
            .width(8.dp)
            .scale(scaleFirstFrame)
            .background(colorYellow, shape = RectangleShape)
            .border(1.dp, color = black, shape = RectangleShape)
    )

    // 22

    val size = LocalDensity.current.run { 80.sp.toPx() }

    Two(transTwoFirst, scaleFirstFrame, size)
    Two(transTwoSecond, scaleFirstFrame, size)

    ColoredCircle(color = ioBGColor, delay = 2000)

    HashGoogle(googleIoScale, size.times(0.9f), showGoogleIO.value)

    IOGoogle(googleIoScale, iHeight.value, offsetCircle.value.dp, circleEndScale.value)

    // O
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .offset(x = earthX.value.dp)
            .size(64.dp)
            .scale(scaleEarth.value)
            .zIndex(6f)
            .background(color = colorGreen, shape = CircleShape)
            .border(1.dp, color = black, shape = CircleShape)
    )

}

@Composable
fun BoxScope.IOGoogle(
    googleIoScale: Float,
    iHeight: Float,
    offsetCircle: Dp,
    circleEndScale: Float
) {
    // I
    Box(
        modifier = Modifier
            .graphicsLayer(scaleY = iHeight)
            .align(Alignment.Center)
            .offset(200.dp)
            .width(36.dp)
            .height(36.dp)
            .background(Color(82, 146, 247), shape = RectangleShape)
    )

    // 0
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .offset(offsetCircle)
            .height(72.dp)
            .width(72.dp)
            .scale(circleEndScale)
            .background(Color(82, 146, 247), shape = CircleShape)
    )
}

@Composable
fun BoxScope.HashGoogle(scale: Float, size: Float, showGoogleIO: Float) {
    Canvas(
        modifier = Modifier.Companion
            .align(Alignment.CenterStart)
            .alpha(showGoogleIO)
            .scale(scale),
        onDraw = {
            drawIntoCanvas {
                it.drawText("#Google", 0f, size.div(4), Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    color = Color.Black.toArgb()
                }, size, PaintMode.STROKE, PaintStrokeJoin.ROUND)
            }
        }
    )
}

@Composable
private fun BoxScope.Two(
    transTwoSecond: Dp,
    scale: Float,
    size: Float
) {
    Canvas(
        modifier = Modifier.Companion
            .align(Alignment.Center)
            .offset(transTwoSecond, y = 10.dp)
            .scale(scale),
        onDraw = {
            drawIntoCanvas {
                it.drawText("2", 0f, size.div(4), Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    color = Color.Black.toArgb()
                    strokeWidth = 6f
                    strokeMiter = 10f
                }, size,style = PaintMode.STROKE,strokeJoin = PaintStrokeJoin.ROUND)

                it.drawText("2", 0f, size.div(4), Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    color = Color(0xffec7753).toArgb()
                }, size, PaintMode.FILL, null)
            }
        }
    )
}

@Composable
fun BoxScope.ColoredCircle(color: Color, delay: Int) {
    var start by remember {
        mutableStateOf(false)
    }
    val scaleCircle by animateFloatAsState(
        targetValue = if (start) 25f else 0f,
        tween(delay.div(2), delayMillis = delay)
    )
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .size(48.dp)
            .scale(scaleCircle)
            .background(color = color, shape = CircleShape)
            .border(1.dp, color = black, shape = CircleShape)
    )

    LaunchedEffect(key1 = Unit, block = {
        start = true
    })

}