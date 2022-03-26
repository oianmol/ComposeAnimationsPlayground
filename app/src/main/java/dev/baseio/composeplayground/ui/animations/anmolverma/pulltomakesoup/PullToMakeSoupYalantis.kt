package dev.baseio.composeplayground.ui.animations.anmolverma.pulltomakesoup

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh.*
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


  Column {
    TopAppBar(content = {

    })
    Box(
      Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {

      KitchenComposable(circleAlpha.value, Modifier.align(Alignment.TopCenter))

      FoodOptionsList(
        animateOffset,
        heightOfRefreshView,
        coroutineScope,
        canAcceptTouch.value,
        { refreshViewCurrentHeight ->
          coroutineScope.launch {
            val newAlpha = abs(refreshViewCurrentHeight / heightOfRefreshView)
            circleAlpha.animateTo(1 - newAlpha)
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
fun KitchenComposable(circleAlpha: Float, modifier: Modifier) {
  Box(
    modifier
      .fillMaxWidth()
  ) {

    CircleComposable(
      Modifier
        .align(Alignment.Center)
        .alpha(circleAlpha)
    )

    UtensilComposable(
      Modifier
        .align(Alignment.Center)
    )
  }
}

@Composable
fun UtensilComposable(modifier: Modifier) {
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
