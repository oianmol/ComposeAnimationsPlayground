package dev.baseio.composeplayground.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.R
import dev.baseio.composeplayground.contributors.AnmolVerma


/**
 * https://twitter.com/ritikaagrawal08/status/1500391227946336260?s=21
 */
@Composable
fun BellAnimation(modifier: Modifier) {
  Surface(modifier.background(MaterialTheme.colors.background)) {

    var needsAnimate by remember {
      mutableStateOf(NotificationBellDefinition.NotificationBellState.BellIdle)
    }

    val swayAnim by animateFloatAsState(
      targetValue = if (isBellMoveMode(needsAnimate)) 1f else 0f,
      animationSpec = NotificationBellDefinition.swayAnimation
    )

    val swayAnimReverse by animateFloatAsState(
      targetValue = if (isBellMoveMode(needsAnimate)) 1f else 0f,
      animationSpec = NotificationBellDefinition.swayReverse
    )

    LaunchedEffect(true) {
      needsAnimate =
        if (isBellMoveMode(needsAnimate)) NotificationBellDefinition.NotificationBellState.BellIdle else NotificationBellDefinition.NotificationBellState.BellMove
    }

    val containerHeight = 120.dp
    val height = 100.dp

    val clapperHeight = containerHeight.times(0.9f)
    val bellHeight = containerHeight.times(0.1f)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Button(onClick = {
        needsAnimate =
          if (isBellMoveMode(needsAnimate)) NotificationBellDefinition.NotificationBellState.BellIdle else NotificationBellDefinition.NotificationBellState.BellMove
      }) {
        Text(text = "Repeat Animation!")
      }

      Box(
        Modifier
          .size(containerHeight)
          .align(Alignment.CenterHorizontally)
          .clickable {
            needsAnimate =
              if (isBellMoveMode(needsAnimate)) NotificationBellDefinition.NotificationBellState.BellIdle else NotificationBellDefinition.NotificationBellState.BellMove

          }
      ) {

        Svg(swayAnim, height,Modifier.align(Alignment.Center))

        Clapper(swayAnimReverse, clapperHeight, bellHeight, swayAnim,Modifier.align(Alignment.Center))

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

@Composable
private fun Svg(swayAnim: Float, height: Dp, modifier: Modifier) {
  Icon(
    painter = painterResource(R.drawable.ic_notification_bell),
    contentDescription = null,
    tint = if (isSystemInDarkTheme()) Color.White else Color(0xff0b0b31),
    modifier = modifier
      .size(height)
      .graphicsLayer(
        transformOrigin = TransformOrigin(0.5f, 0f),
        rotationZ = swayAnim
      )
  )
}

@Composable
private fun Clapper(
  swayAnimReverse: Float,
  clapperHeight: Dp,
  bellHeight: Dp,
  swayAnim: Float,
  modifier: Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.graphicsLayer(
      rotationZ = swayAnimReverse
    )
  ) {
    Box(
      Modifier
        .width(16.dp)
        .height(clapperHeight)
        .background(Color.Transparent)
    )
    Box(
      Modifier
        .width(24.dp)
        .height(bellHeight)
        .graphicsLayer(rotationZ = swayAnim)
        .background(
          Color.Red, RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 15.dp,
            bottomEnd = 15.dp
          )
        )
    )
  }
}

fun isBellMoveMode(needsAnimate: NotificationBellDefinition.NotificationBellState) =
  needsAnimate == NotificationBellDefinition.NotificationBellState.BellMove

object NotificationBellDefinition {

  enum class NotificationBellState {
    BellMove, BellIdle
  }

  val swayAnimation = keyframes<Float> {
    durationMillis = 1000
    -10f at 0 with LinearEasing
    10f at 250 with LinearEasing
    -10f at 500 with LinearEasing
    5f at 750 with LinearEasing
    0f at 1000 with LinearEasing
  }

  val swayReverse = keyframes<Float> {
    durationMillis = 1000
    5f at 0 with LinearEasing
    -5f at 250 with LinearEasing
    5f at 500 with LinearEasing
    -5f at 750 with LinearEasing
    0f at 1000 with LinearEasing
  }
}