package dev.baseio.composeplayground.ui.animations.anmolverma.pulltomakesoup

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import dev.baseio.composeplayground.contributors.AnmolVerma
import dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh.RESISTANCE_SCROLL
import dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh.RandomCard
import dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh.green
import dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh.yellow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 * WIP for Pull to make Soup https://github.com/Yalantis/pull-to-make-soup
 */
@Composable
fun PullToMakeSoupYalantis() {
  val canAcceptTouch = remember {
    mutableStateOf(true)
  }

  val heightOfRefreshView = with(LocalDensity.current) {
    200.dp.toPx()
  }

  val circleAlpha = remember {
    Animatable(0f)
  }

  val coroutineScope = rememberCoroutineScope()
  val animateOffset = remember {
    Animatable(-heightOfRefreshView)
  }

  val widthScreen = with(LocalDensity.current) {
    LocalConfiguration.current.screenWidthDp.dp.toPx()
  }

  val heightScreen = with(LocalDensity.current) {
    LocalConfiguration.current.screenHeightDp.dp.toPx()
  }

  val panTopOffset = with(LocalDensity.current) { 60.dp.toPx() }
  val coverOffset = with(LocalDensity.current) { 90.dp.toPx() }
  val coverStartPointY = with(LocalDensity.current) { 10.dp.toPx() }
  val coverFinalPointY = with(LocalDensity.current) { 70.dp.toPx() }

  val carrotStartPointX = (widthScreen / 100) * 79
  val carrotFinalPointX = (widthScreen / 100) * 30.0f

  val carrotFinalPointY = with(LocalDensity.current) { 245.dp.toPx() }
  val carrotStartPointY = with(LocalDensity.current) { 161.dp.toPx() }

  val carrotOffset = with(LocalDensity.current) { 90.dp.toPx() }

  val potatoFinalPointX = (widthScreen / 100) * -25;
  val potatoStartPointX = (widthScreen / 100) * 14.5f;

  val potatoStartPointY = with(LocalDensity.current) { 150.dp.toPx() }
  val potatoFinalPointY = with(LocalDensity.current) { 237.dp.toPx() }
  val potatoOffset = with(LocalDensity.current) { 90.dp.toPx() }

  val rightPeaFinalPointX = (widthScreen / 100) * 30.5f;
  val rightPeaStartPointX = (widthScreen / 100) * 88;

  val rightPeaStartPointY = with(LocalDensity.current) { 150.dp.toPx() }
  val rightPeaFinalPointY = with(LocalDensity.current) { 242.dp.toPx() }
  val rightPeaOffset = with(LocalDensity.current) { 90.dp.toPx() }

  val leftPeaStartPointX = (widthScreen / 100) * 7.5f;
  val leftPeaFinalPointX = (widthScreen / 100) * -45;

  val leftPeaStartPointY = with(LocalDensity.current) { 150.dp.toPx() }
  val leftPeaFinalPointY = with(LocalDensity.current) { 242.dp.toPx() }
  val leftPeaOffset = with(LocalDensity.current) { 90.dp.toPx() }

  val flame1TopOffset = with(LocalDensity.current) { 161.dp.toPx() }
  val flame1LeftOffset = (widthScreen / 100) * 42;

  val flame2LeftOffset = (widthScreen / 100) * 45;

  val flame3TopOffset = with(LocalDensity.current) { 132.dp.toPx() }
  val flame3LeftOffset = (widthScreen / 100) * 48.5f;

  val flame4TopOffset = with(LocalDensity.current) { 134.dp.toPx() }
  val flame4LeftOffset = (widthScreen / 100) * 51.5f;

  val flame5TopOffset = with(LocalDensity.current) { 134.dp.toPx() }
  val flame5LeftOffset = (widthScreen / 100) * 54f;

  val bubble1TopOffset = with(LocalDensity.current) { 144.dp.toPx() }
  val bubble1LeftOffset = (widthScreen / 100) * 40;

  val bubble2TopOffset = with(LocalDensity.current) { 144.dp.toPx() }
  val bubble2LeftOffset = (widthScreen / 100) * 42;

  val bubble3TopOffset = with(LocalDensity.current) { 144.dp.toPx() }
  val bubble3LeftOffset = (widthScreen / 100) * 44;

  val bubble4TopOffset = with(LocalDensity.current) { 144.dp.toPx() }
  val bubble4LeftOffset = (widthScreen / 100) * 46;

  val bubble5TopOffset = with(LocalDensity.current) { 144.dp.toPx() }
  val bubble5LeftOffset = (widthScreen / 100) * 48;

  val bubble6TopOffset = with(LocalDensity.current) { 144.dp.toPx() }
  val bubble6LeftOffset = (widthScreen / 100) * 50;

  val circlePivotX = with(LocalDensity.current) { 100.dp.toPx() }
  val circlePivotY = with(LocalDensity.current) { 40.dp.toPx() }

  val bubbleScaleOffset = with(LocalDensity.current) { 100.dp.toPx() }

  val bubble1PivotX = bubble1LeftOffset - with(LocalDensity.current) { 140.dp.toPx() }
  val bubble2PivotX = bubble2LeftOffset - with(LocalDensity.current) { 140.dp.toPx() }
  val bubble3PivotX = bubble3LeftOffset - with(LocalDensity.current) { 140.dp.toPx() }
  val bubble4PivotX = bubble4LeftOffset - with(LocalDensity.current) { 140.dp.toPx() }
  val bubble5PivotX = bubble5LeftOffset - with(LocalDensity.current) { 140.dp.toPx() }
  val bubble6PivotX = bubble6LeftOffset - with(LocalDensity.current) { 140.dp.toPx() }



  Column {
    TopAppBar(content = {

    })
    Box(
      Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {

      KitchenComposable(
        circleModifier = Modifier
          .alpha(circleAlpha.value)
          .offset { IntOffset(circlePivotX.toInt(), circlePivotY.toInt()) },
        panModifier = Modifier.align(Alignment.TopCenter)
      )

      FoodOptionsList(
        animateOffset,
        heightOfRefreshView,
        coroutineScope,
        canAcceptTouch.value,
        { refreshViewCurrentHeight ->
          coroutineScope.launch {
            // circle alpha,scale,offset
           /* val dragPercent = Math.min(0.85f, Math.abs(mPercent))
            val offsetX: Float = widthScreen / 2 - mCircle.getWidth() / 2
            val offsetY: Float = -(heightScreen / 100)
            matrix.postScale(dragPercent, dragPercent, mCirclePivotX, mCirclePivotY)
            matrix.postTranslate(offsetX, offsetY)
            circleAlpha.animateTo(dragPercent / 2 * 500)*/
          }
        },
        {
          canAcceptTouch.value = false

          delay(4000) // fake delay TODO  animate cooking here.....

          canAcceptTouch.value = true
          animateOffset.animateTo(-heightOfRefreshView, animationSpec = tween(250))

        })

    }
  }
}

@Composable
fun FoodOptionsList(
  animateOffset: Animatable<Float, AnimationVector1D>,
  heightOfRefreshView: Float,
  coroutineScope: CoroutineScope,
  canAcceptTouch: Boolean,
  onUpdate: (Float) -> Unit,
  loadData: suspend () -> Unit,
) {
  Column(Modifier
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

    // This is a fake view! LazyColumn doesn't support overscroll ??
    KitchenComposable(0f, Modifier.alpha(0f))

    RandomCard(yellow)

    RandomCard(green)

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(140.dp)
        .background(MaterialTheme.colors.background)
    ) {
      AnmolVerma(Modifier.align(Alignment.Center))
    }

  }
}

@Composable
fun KitchenComposable(circleModifier: Modifier, panModifier: Modifier) {
  Box(
    Modifier
      .fillMaxWidth()
  ) {

    CircleComposable(circleModifier
    )

    PanComposable(panModifier)
  }
}

@Composable
fun PanComposable(modifier: Modifier) {
  Image(
    painter = painterResource(id = R.drawable.pan),
    contentDescription = null,
    modifier
  )
}

@Composable
fun CircleComposable(modifier: Modifier) {
  Image(
    painter = painterResource(id = R.drawable.circle),
    contentDescription = null,
    modifier
  )
}


