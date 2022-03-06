package dev.baseio.composeplayground.ui.animations

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 * https://dribbble.com/shots/1623131-Pull-to-Refresh
 */
@Composable
fun PullToRefreshOne() {

  val coroutineScope = rememberCoroutineScope()
  val animateOffset = remember {
    Animatable(0f)
  }

  val cloudsZoom = remember {
    Animatable(1f)
  }

  val heightOfRefreshView = with(LocalDensity.current) {
    200.dp.toPx()
  }

  val airplaneXPixels = with(LocalDensity.current) {
    (LocalConfiguration.current.screenWidthDp.dp.toPx() * 0.3f)
  }

  val airplaneYPixels = (heightOfRefreshView * 1.2f)

  val airplaneOffsetX = remember {
    Animatable(airplaneXPixels)
  }
  val airplaneOffsetY = remember {
    Animatable(airplaneYPixels)
  }

  LaunchedEffect(true) {
    animateOffset.animateTo(-heightOfRefreshView, animationSpec = tween(500))
  }

  Box(
    Modifier
      .fillMaxSize()
  ) {
    CloudList(
      animateOffset,
      airplaneOffsetX,
      airplaneOffsetY,
      cloudsZoom,
      heightOfRefreshView,
      coroutineScope
    ) { newY ->
      coroutineScope.launch {
        val newScale = abs(heightOfRefreshView / newY)
        Log.e("new scale",newScale.toString())
        cloudsZoom.animateTo(max(1f, min(2f, newScale)))
      }
      coroutineScope.launch {
        val newAirplaneX = airplaneXPixels.times(abs(heightOfRefreshView / newY))
        Log.e(this.javaClass.simpleName, newAirplaneX.toString())
        airplaneOffsetX.animateTo(newAirplaneX)
      }
      coroutineScope.launch {
        val newAirplaneY = airplaneYPixels.times(abs(newY / heightOfRefreshView))
        Log.e(this.javaClass.simpleName, newAirplaneY.toString())
        airplaneOffsetY.animateTo(min(newAirplaneY, airplaneYPixels))
      }
    }
  }
}

@Composable
private fun CloudList(
  animateOffset: Animatable<Float, AnimationVector1D>,
  airplaneOffsetX: Animatable<Float, AnimationVector1D>,
  airplaneOffsetY: Animatable<Float, AnimationVector1D>,
  cloudsZoom: Animatable<Float, AnimationVector1D>,
  heightOfRefreshView: Float,
  coroutineScope: CoroutineScope,
  onUpdate: (Float) -> Unit
) {
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

          },
          onVerticalDrag = { change, dragAmount ->
            val summedMain = Offset(x = 0f, y = animateOffset.targetValue + dragAmount)
            val newDragValueMain =
              Offset(x = 0f, y = min(0f, max(-heightOfRefreshView, summedMain.y)))
            change.consumePositionChange()
            onUpdate(newDragValueMain.y)
            coroutineScope.launch {
              animateOffset.animateTo(newDragValueMain.y, animationSpec = tween(50))
            }
          })
      }) {

    CloudPlaneComposable(airplaneOffsetX.value, airplaneOffsetY.value, cloudsZoom.value)

    RandomCard(yellow)

    RandomCard(green)

    RandomCard(red)

    RandomCard(yellow)

    RandomCard(green)

    RandomCard(red)
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
        .size(200.dp)
        .align(Alignment.Center), tint = Color.White
    )
  }
}


val yellow = Color(0xffefa92f)
val green = Color(0xff079c6d)
val red = Color(0xffe35050)

@Composable
private fun CloudPlaneComposable(
  airplaneOffsetX: Float,
  airplaneOffsetY: Float,
  cloudsZoom: Float
) {
  Box(
    Modifier
      .fillMaxWidth()
      .height(200.dp)
      .background(if (isSystemInDarkTheme()) Color.Black else Color(0xff1fb4ff))
  ) {
    Image(
      painter = painterResource(id = R.drawable.airplane),
      contentDescription = null,
      Modifier
        .offset { IntOffset(airplaneOffsetX.toInt(), airplaneOffsetY.toInt()) }
    )

    CloudsBottom(cloudsZoom)

    ArrowsExpanding()
  }
}

@Composable
private fun BoxScope.ArrowsExpanding() {
  Box(
    Modifier.Companion
      .align(Alignment.BottomCenter)
      .fillMaxWidth()
  ) {


  }
}

@Composable
private fun BoxScope.CloudsBottom(cloudsZoom: Float) {
  Box(
    Modifier.Companion
      .align(Alignment.BottomCenter)
      .fillMaxWidth()
  ) {

    Image(
      painter = painterResource(id = R.drawable.clouds_left),
      contentDescription = null,
      Modifier
        .scale(cloudsZoom)
        .align(Alignment.BottomStart)
    )

    Image(
      painter = painterResource(id = R.drawable.clouds_left),
      contentDescription = null,
      Modifier
        .scale(cloudsZoom)
        .align(Alignment.BottomEnd)
    )


    Image(
      painter = painterResource(id = R.drawable.clouds_center),
      contentDescription = null,
      Modifier
        .scale(cloudsZoom)
        .align(Alignment.BottomCenter)
    )


  }
}