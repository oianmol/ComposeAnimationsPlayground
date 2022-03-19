package dev.baseio.composeplayground.ui.animations

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.R
import dev.baseio.composeplayground.contributors.AnmolVerma
import dev.baseio.composeplayground.ui.theme.Typography

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


  val infiniteTransition2 = rememberInfiniteTransition()
  val scaleRect by infiniteTransition2.animateFloat(
    initialValue = 0f,
    targetValue = 0.9f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 5000, delayMillis = 2000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )


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
      Box() {
        Text(
          text = stringResource(id = R.string.sun_moon),
          style = Typography.h5.copy(color = Color.White),
        )
        Text(
          text = "5:44 AM",
          style = Typography.caption.copy(color = Color.White),
          modifier = Modifier.offset(y = 280.dp, x = (-120).dp)
        )
        Text(
          text = "20:01 PM",
          style = Typography.caption.copy(color = Color.White),
          modifier = Modifier.offset(y = 280.dp, x = (190).dp)
        )

        Box(
          Modifier
            .offset(x = (-100).dp, y = 262.dp)
            .clip(CircleShape)
            .size(10.dp)
            .background(Color(0xfff9d71c))
        )

        Box(
          Modifier
            .offset(x = (220).dp, y = 262.dp)
            .clip(CircleShape)
            .size(10.dp)
            .background(Color(0xfff9d71c))
        )
      }

      Box {
        PathArcCanvas(Modifier.align(Alignment.Center))

        Icon(
          painter = painterResource(id = R.drawable.ic_sun),
          contentDescription = null,
          modifier = Modifier
            .offset(x = 30.dp, y = 106.dp)
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

        Box(
          Modifier
            .fillMaxWidth(fraction = 0.98f)
            .align(Alignment.Center)
            .height(1.dp)
            .background(Color.White.copy(0.5f))
            .offset(y = 160.dp)
        )


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
private fun PathArcCanvas(modifier: Modifier) {
  Canvas(modifier = modifier.size(320.dp), onDraw = {
    drawArc(
      color = Color(0xfff9d71c),
      style = Stroke(
        width = 1f,
        pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(27f, 27f))
      ), useCenter = false,
      startAngle = 180f,
      sweepAngle = 180f
    )
  })
}