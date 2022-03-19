package dev.baseio.composeplayground.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.R
import dev.baseio.composeplayground.contributors.AnmolVerma
import dev.baseio.composeplayground.ui.theme.Typography

@Composable
fun GlowingRingLoader(modifier: Modifier) {
  Surface(
    modifier
      .background(MaterialTheme.colors.background)
  ) {

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
      initialValue = 0f,
      targetValue = 360f,
      animationSpec = infiniteRepeatable(
        animation = tween(2000, easing = LinearEasing),
        repeatMode = RepeatMode.Restart
      )
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

      Spacer(modifier = Modifier.height(28.dp))

      Box(
        Modifier
          .size(400.dp)
          .padding(24.dp)
      ) {
        Text(
          text = stringResource(id = R.string.loading),
          style = Typography.h6.copy(
            Color(0xfff9d71c), shadow = Shadow(
              color = Color(0xfff9d71c),
              offset = Offset(1f, 1f),
              blurRadius = 5f
            )
          ),
          modifier = Modifier
            .align(Alignment.Center)
        )

        Canvas(modifier = Modifier
          .align(Alignment.Center)
          .size(150.dp), onDraw = {
          drawCircle(color = Color.DarkGray, style = Stroke(width = 5f))
        })

        Canvas(modifier = Modifier
          .align(Alignment.Center)
          .size(150.dp), onDraw = {
          drawArc(
            color =
            Color(0xfff9d71c),
            style = Stroke(
              width = 5f,
              cap = StrokeCap.Round,
              join =
              StrokeJoin.Round,
            ),
            startAngle = angle,
            sweepAngle = 360 / 4f,
            useCenter = false
          )
        })
      }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp)
          .background(MaterialTheme.colors.background)
      ) {
        AnmolVerma(Modifier.align(Alignment.Center))
      }
    }
  }
}