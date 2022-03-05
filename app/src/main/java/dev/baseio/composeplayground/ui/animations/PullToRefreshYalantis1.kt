package dev.baseio.composeplayground.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


/**
 * https://dribbble.com/shots/1623131-Pull-to-Refresh
 */
@Composable
fun PullToRefreshOne() {

  val coroutineScope = rememberCoroutineScope()
  val animateOffset = remember {
    Animatable(0f)
  }
  val height = with(LocalDensity.current) {
    200.dp.toPx()
  }

  LaunchedEffect(true) {
    animateOffset.animateTo(height, animationSpec = tween(500))
  }

  Box(
    Modifier
      .fillMaxSize()
  ) {
    CloudPlaneComposable()
    Column(
      Modifier
        .offset {
          IntOffset(
            0,
            animateOffset.value.toInt()
          )
        }
        .pointerInput(Unit) {
          detectVerticalDragGestures(
            onDragStart = {},
            onDragCancel = {},
            onDragEnd = {
              if (animateOffset.targetValue > height / 2) {
                coroutineScope.launch {
                  animateOffset.animateTo(0f, animationSpec = tween(500))
                }
              } else {
                coroutineScope.launch {
                  animateOffset.animateTo(height, animationSpec = tween(500))
                }
              }
            },
            onVerticalDrag = { change, dragAmount ->
              val summedMain = Offset(x = 0f, y = animateOffset.targetValue + dragAmount)
              val newDragValueMain = Offset(x = 0f, y = summedMain.y.coerceIn(0f, height))
              change.consumePositionChange()
              coroutineScope.launch {
                animateOffset.animateTo(newDragValueMain.y, animationSpec = tween(50))
              }
            })
        }) {

      RandomCard(yellow)

      RandomCard(green)

      RandomCard(red)

      RandomCard(yellow)

      RandomCard(green)

      RandomCard(red)
    }
  }
}

@Composable
fun RandomCard(color: Color) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(200.dp)
      .background(color)
  ) {
    Icon(
      imageVector = Icons.Filled.ShoppingCart,
      contentDescription = null,
      modifier = Modifier
        .size(120.dp)
        .align(Alignment.Center), tint = Color.White
    )
  }
}


val yellow = Color(0xffefa92f)
val green = Color(0xff079c6d)
val red = Color(0xffe35050)

@Composable
private fun BoxScope.CloudPlaneComposable() {
  Box(
    Modifier
      .fillMaxWidth()
      .align(Alignment.TopCenter)
      .height(240.dp)
      .background(if (isSystemInDarkTheme()) Color.Black else Color(0xff1fb4ff))
  ) {

  }
}