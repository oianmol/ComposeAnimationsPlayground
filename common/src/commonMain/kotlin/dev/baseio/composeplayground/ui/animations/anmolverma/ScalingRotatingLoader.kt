package dev.baseio.composeplayground.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import dev.baseio.common.LocalWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.contributors.AnmolVerma
import kotlinx.coroutines.delay

/**
 * scalling_rotating_loader
 */
@Composable
fun ScalingRotatingLoader() {
  var rotateOuter by remember {
    mutableStateOf(false)
  }


  val angle by animateFloatAsState(
    targetValue = if (rotateOuter) 360 * 3f else 0f,
    animationSpec = spring(
      visibilityThreshold = 0.3f,
      dampingRatio = 0.1f,
      stiffness = 0.87f
    )
  )
  val infiniteTransition = rememberInfiniteTransition()
  val scaleBox by infiniteTransition.animateFloat(
    initialValue = 0.3f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(2000),
      repeatMode = RepeatMode.Reverse
    )
  )

  val centerOffset = with(LocalDensity.current) {
    Offset(
      LocalWindow.current.screenWidthDp.div(2).toPx(),
      LocalWindow.current.screenHeightDp.div(2).toPx()
    )
  }

  LaunchedEffect(key1 = true, block = {
    rotateOuter = !rotateOuter
    while (true){
      // infiniteRepeatable does not support spring yet  Compose documentation has a
      // TODO: Consider supporting repeating spring specs
      delay(2000)
      rotateOuter = !rotateOuter
    }
  })


  Box {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .scale(1.2f)
        .background(
          brush = Brush.radialGradient(
            listOf(Color.Black, Color.Blue),
            center = centerOffset, radius = 500f, tileMode = TileMode.Clamp
          )
        )
    ) {
      Box(
        Modifier
          .scale(scaleBox)
          .align(Alignment.Center)
      ) {
        // center circle
        Box(
          Modifier
            .align(Alignment.Center)
            .size(50.dp)
            .background(Color.White, shape = CircleShape)
        )
        // two arc's
        Box(Modifier.rotate(angle)) {
          Canvas(modifier = Modifier
            .align(Alignment.Center)
            .size(100.dp), onDraw = {
            drawArc(
              color =
              Color.White,
              style = Stroke(
                width = 3f,
                cap = StrokeCap.Round,
                join =
                StrokeJoin.Round,
              ),
              startAngle = 180f,
              sweepAngle = 288f,
              useCenter = false
            )

          })
        }

        Box(Modifier.rotate(angle)) {
          Canvas(modifier = Modifier
            .rotate(180f)
            .align(Alignment.Center)
            .size(100.dp), onDraw = {
            drawArc(
              color =
              Color.Blue,
              style = Stroke(
                width = 3f,
                cap = StrokeCap.Round,
                join =
                StrokeJoin.Round,
              ),
              startAngle = 180f,
              sweepAngle = 288f,
              useCenter = false
            )
          }
          )
        }

      }

    }
    Box(modifier = Modifier.align(Alignment.BottomCenter)) {
      AnmolVerma()
    }
  }
}