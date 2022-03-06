package dev.baseio.composeplayground.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
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
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 * https://dribbble.com/shots/1623131-Pull-to-Refresh
 */
@Composable
fun PullToRefreshOne() {

  val canAcceptTouch = remember {
    mutableStateOf(true)
  }

  val heightOfRefreshView = with(LocalDensity.current) {
    200.dp.toPx()
  }

  val coroutineScope = rememberCoroutineScope()
  val animateOffset = remember {
    Animatable(-heightOfRefreshView)
  }

  val sideCloudScale = remember {
    Animatable(1.05f)
  }
  val centerCloudScale = remember {
    Animatable(0.8f)
  }

  val widthScreen = with(LocalDensity.current) {
    LocalConfiguration.current.screenWidthDp.dp.toPx()
  }

  val airplaneXPixels = (widthScreen * 0.2f)
  val airplaneYPixels = (heightOfRefreshView * 1.2f)

  val airplaneOffsetX = remember {
    Animatable(airplaneXPixels)
  }
  val airplaneOffsetY = remember {
    Animatable(airplaneYPixels)
  }

  val dividerHeight = remember {
    Animatable(heightOfRefreshView * 0.8f)
  }
  val airplaneVerticalOffset = with(LocalDensity.current) {
    4.dp.toPx()
  }

  Box(
    Modifier
      .fillMaxSize()
  ) {
    CloudList(
      animateOffset,
      airplaneOffsetX,
      airplaneOffsetY,
      sideCloudScale,
      centerCloudScale,
      dividerHeight,
      heightOfRefreshView,
      coroutineScope,
      { refreshViewCurrentHeight ->
        onRefreshViewTranslated(
          coroutineScope,
          heightOfRefreshView,
          refreshViewCurrentHeight,
          sideCloudScale,
          centerCloudScale,
          airplaneXPixels,
          airplaneOffsetX,
          airplaneYPixels,
          airplaneOffsetY, widthScreen, dividerHeight
        )
      }, {
        canAcceptTouch.value = false

        upDownAirplaneMove(
          airplaneOffsetY,
          airplaneVerticalOffset,
          animateOffset,
          heightOfRefreshView,
          airplaneOffsetX,
          widthScreen,
          coroutineScope,
        )

        canAcceptTouch.value = true

      }, canAcceptTouch.value
    )
  }
}

private fun onRefreshViewTranslated(
  coroutineScope: CoroutineScope,
  heightOfRefreshView: Float,
  refreshViewCurrentHeight: Float,
  sideCloudScale: Animatable<Float, AnimationVector1D>,
  centerCloudScale: Animatable<Float, AnimationVector1D>,
  airplaneXPixels: Float,
  airplaneOffsetX: Animatable<Float, AnimationVector1D>,
  airplaneYPixels: Float,
  airplaneOffsetY: Animatable<Float, AnimationVector1D>,
  widthScreen: Float,
  dividerHeight: Animatable<Float, AnimationVector1D>
) {
  coroutineScope.launch {
    val newScale = abs(heightOfRefreshView / refreshViewCurrentHeight)
    sideCloudScale.animateTo(max(1f, min(1.55f, newScale)))
  }
  coroutineScope.launch {
    val newScale = abs(heightOfRefreshView / refreshViewCurrentHeight)
    centerCloudScale.animateTo(max(1f, min(1.30f, newScale)))
  }
  coroutineScope.launch {
    val newAirplaneX = airplaneXPixels.times(abs(heightOfRefreshView / refreshViewCurrentHeight))
    airplaneOffsetX.animateTo(min(newAirplaneX, widthScreen / 2.5f))
  }
  coroutineScope.launch {
    val newAirplaneY = airplaneYPixels.times(abs(refreshViewCurrentHeight / heightOfRefreshView))
    airplaneOffsetY.animateTo(max(newAirplaneY, heightOfRefreshView / 2))
  }
  coroutineScope.launch {
    dividerHeight.animateTo(refreshViewCurrentHeight * 0.8f)
  }
}

private suspend fun upDownAirplaneMove(
  airplaneOffsetY: Animatable<Float, AnimationVector1D>,
  airplaneVerticalOffset: Float,
  animateOffset: Animatable<Float, AnimationVector1D>,
  heightOfRefreshView: Float,
  airplaneOffsetX: Animatable<Float, AnimationVector1D>,
  widthScreen: Float,
  coroutineScope: CoroutineScope
) {
  val currentPlaneOffsetY = airplaneOffsetY.value

  repeat(10) {
    airplaneOffsetY.animateTo(
      if (it % 2 != 0) (currentPlaneOffsetY.minus(airplaneVerticalOffset)) else (currentPlaneOffsetY.plus(
        airplaneVerticalOffset
      )),
      tween(390, easing = FastOutLinearInEasing)
    )
  }

  val airplaneXJob = coroutineScope.launch {
    airplaneOffsetX.animateTo(widthScreen, animationSpec = tween(500))
  }
  val airplaneYJob = coroutineScope.launch {
    airplaneOffsetY.animateTo(0f, animationSpec = tween(500))
  }

  joinAll(airplaneXJob, airplaneYJob)
  animateOffset.animateTo(-heightOfRefreshView, animationSpec = tween(250))
}

@Composable
private fun CloudList(
  animateOffset: Animatable<Float, AnimationVector1D>,
  airplaneOffsetX: Animatable<Float, AnimationVector1D>,
  airplaneOffsetY: Animatable<Float, AnimationVector1D>,
  sideCloudScale: Animatable<Float, AnimationVector1D>,
  centerCloudScale: Animatable<Float, AnimationVector1D>,
  dividerHeight: Animatable<Float, AnimationVector1D>,
  heightOfRefreshView: Float,
  coroutineScope: CoroutineScope,
  onUpdate: (Float) -> Unit,
  loadData: suspend () -> Unit,
  canAcceptTouch: Boolean,
) {
  Column(
    Modifier
      .offset {
        IntOffset(
          0,
          animateOffset.value.toInt()
        )
      }
      .pointerInput(canAcceptTouch) {
        if (!canAcceptTouch) return@pointerInput

        detectVerticalDragGestures(
          onDragStart = {},
          onDragCancel = {},
          onDragEnd = {
            if (animateOffset.value >= (-heightOfRefreshView / 1.6)) {
              coroutineScope.launch {
                loadData()
              }
            } else {
              coroutineScope.launch {
                animateOffset.animateTo(-heightOfRefreshView, animationSpec = tween(50))
              }
            }
          },
          onVerticalDrag = { change, dragAmount ->
            val summedMain =
              Offset(x = 0f, y = animateOffset.targetValue + dragAmount.times(RESISTANCE_SCROLL))
            val newDragValueMain =
              Offset(x = 0f, y = min(0f, max(-heightOfRefreshView, summedMain.y)))
            change.consumePositionChange()
            onUpdate(newDragValueMain.y)

            coroutineScope.launch {
              animateOffset.animateTo(newDragValueMain.y, animationSpec = tween(50))
            }

          })
      }) {

    CloudPlaneComposable(
      airplaneOffsetX.value,
      airplaneOffsetY.value,
      sideCloudScale.value,
      centerCloudScale.value,
      dividerHeight = dividerHeight.value
    )

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
        .size(100.dp)
        .align(Alignment.Center), tint = Color.White
    )
  }
}


const val RESISTANCE_SCROLL = 0.3f
val yellow = Color(0xffefa92f)
val green = Color(0xff079c6d)
val red = Color(0xffe35050)

@Composable
private fun CloudPlaneComposable(
  airplaneOffsetX: Float,
  airplaneOffsetY: Float,
  cloudsZoom: Float,
  centerCloud: Float,
  dividerHeight: Float
) {
  Sky {
    Image(
      painter = painterResource(id = R.drawable.airplane),
      contentDescription = null,
      Modifier
        .offset {
          IntOffset(
            airplaneOffsetX.toInt(),
            airplaneOffsetY.toInt()
          )
        }
    )

    it.CloudsBottom(cloudsZoom, centerCloud)

    it.ArrowsExpanding(dividerHeight)
  }

}

@Composable
private fun BoxScope.ArrowsExpanding(dividerHeight: Float) {
  val dividerHeightDp = with(LocalDensity.current) {
    dividerHeight.toDp()
  }
  Box(
    Modifier.Companion
      .align(Alignment.Center)
      .fillMaxWidth()
  ) {
    Row(
      Modifier
        .height(IntrinsicSize.Max)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      Spacer(Modifier.padding())
      Divider(
        color = yellow,
        modifier = Modifier
          .height(dividerHeightDp)
          .width(4.dp)
      )
      Spacer(Modifier.padding())
      Divider(
        color = yellow,
        modifier = Modifier
          .height(dividerHeightDp)
          .width(4.dp)
      )
      Spacer(Modifier.padding())
    }


  }
}

@Composable
private fun BoxScope.CloudsBottom(
  cloudsZoom: Float,
  centerCloud: Float,
) {
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
        .scale(centerCloud)
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