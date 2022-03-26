package dev.baseio.composeplayground.ui.animations

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.R
import dev.baseio.composeplayground.contributors.AnmolVerma
import dev.baseio.composeplayground.ui.theme.Typography
import kotlin.math.cos
import kotlin.math.sin

/**
 * https://github.com/amosgyamfi/swiftui-animation-library#yahoo-weather-sun--moon
 */
@Composable
fun YahooWeatherAndSun(modifier: Modifier) {

  val infiniteTransition = rememberInfiniteTransition()
  val angleRotation by infiniteTransition.animateFloat(
    initialValue = 20f,
    targetValue = 145f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 5000, delayMillis = 2000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )


  val scaleRect by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 0.9f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 5000, delayMillis = 2000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )

  val radian by infiniteTransition.animateFloat(
    initialValue = -3f,
    targetValue = -1f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 5000, delayMillis = 2000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )

  var iconX by remember {
    mutableStateOf(0f)
  }

  var iconY by remember {
    mutableStateOf(0f)
  }


  Box(
    modifier
      .background(Color(0xff112937))
  ) {

    Column(
      Modifier
        .fillMaxSize()
        .clickable {}
        .padding(4.dp),
      verticalArrangement = Arrangement.SpaceAround,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      Text(
        text = stringResource(id = R.string.sun_moon),
        style = Typography.h5.copy(color = Color.White),
      )
      Box {
        PathArcCanvas(Modifier.align(Alignment.Center), scaleRect, radian) { x, y ->
          iconX = x
          iconY = y
        }

        Icon(
          painter = painterResource(id = R.drawable.ic_sun),
          contentDescription = null,
          modifier = Modifier
            .offset { IntOffset(iconX.toInt(), iconY.toInt()) }
            .rotate(angleRotation),
          tint = Color(0xfff9d71c)
        )

        Box(
          Modifier
            .clip(CircleShape)
            .align(Alignment.Center)
            .size(320.dp)
        ) {
          ContainedCanvas(scaleRect)
        }
      }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp)
      ) {
        AnmolVerma(Modifier.align(Alignment.Center))
      }
    }
  }
}


@Composable
private fun ContainedCanvas(scaleRect: Float) {
  Box(
    modifier = Modifier
      .width(300.dp)
      .height(160.dp)
      .graphicsLayer(
        transformOrigin = TransformOrigin(0f, 0f),
        scaleX = scaleRect, scaleY = 1f, alpha = 0.1f
      )
      .background(Color(0xfff9d71c)),
  )
}

@Composable
private fun PathArcCanvas(
  modifier: Modifier,
  scaleRect: Float,
  radian: Float,
  updatedPosition: (Float, Float) -> Unit,
) {
  val circleRadius = LocalDensity.current.run { 8.dp.toPx() }
  val widthOfCircle = LocalDensity.current.run { 320.dp.toPx() }

  var centerPoint by remember {
    mutableStateOf(Offset.Zero)
  }

  var x by remember {
    mutableStateOf(0f)
  }

  var y by remember {
    mutableStateOf(0f)
  }


  LaunchedEffect(key1 = scaleRect, block = {
    val orbitRadius = widthOfCircle.div(2)
    x = (centerPoint.x + cos(radian) * orbitRadius.plus(circleRadius.times(2)))
    y = (centerPoint.y + sin(radian) * orbitRadius.plus(circleRadius.times(2)))
    updatedPosition(x, y)
  })

  Canvas(modifier = modifier.size(320.dp), onDraw = {
    centerPoint = this.center
    val firstCircle = centerPoint.copy(x = centerPoint.x.minus(widthOfCircle / 2))
    val secondCircle = centerPoint.copy(x = centerPoint.x.plus(widthOfCircle / 2))
    drawArc(
      color = Color(0xfff9d71c),
      style = Stroke(
        width = 1f,
        pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(27f, 27f))
      ), useCenter = false,
      startAngle = 180f,
      sweepAngle = 180f
    )


    drawLine(
      Color.White.copy(0.5f),
      start = firstCircle.copy(x = firstCircle.x.minus(80f)),
      secondCircle.copy(x = secondCircle.x.plus(80f)),
      4f
    )

    drawCircle(
      Color(0xfff9d71c),
      radius = circleRadius,
      center = firstCircle
    )

    drawCircle(
      Color(0xfff9d71c),
      radius = circleRadius,
      center = secondCircle
    )

    val paint1 = textpaint()
    val rect1 = Rect()
    paint1.getTextBounds(firstTime, 0, firstTime.length, rect1);

    drawContext.canvas.nativeCanvas.drawText(
      firstTime,
      firstCircle.x.minus(rect1.width() / 2),
      firstCircle.y.plus(rect1.height().times(4)),
      paint1
    )
    val paint2 = textpaint()
    val rect2 = Rect()
    paint2.getTextBounds(secondTime, 0, secondTime.length, rect2);

    drawContext.canvas.nativeCanvas.drawText(
      secondTime,
      secondCircle.x.minus(rect2.width() / 2),
      secondCircle.y.plus(rect2.height().times(4)),
      paint2
    )
  })
}

const val firstTime = "5:44 AM"
const val secondTime = "7:00 PM"
private fun textpaint(): Paint {
  return Paint().apply {
    this.color = android.graphics.Color.WHITE
    this.textSize = 32f
  }
}