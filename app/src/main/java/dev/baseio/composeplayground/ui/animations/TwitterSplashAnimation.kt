package dev.baseio.composeplayground.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.R
import dev.baseio.composeplayground.contributors.AnmolVerma
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TwitterSplashAnimation(modifier: Modifier = Modifier) {
  val width = with(LocalDensity.current) {
    LocalConfiguration.current.screenWidthDp.dp.toPx()
  }
  val height = with(LocalDensity.current) {
    LocalConfiguration.current.screenHeightDp.dp.toPx()
  }

  val scaleFactor = remember {
    Animatable(1.5f)
  }

  val coroutineScope = rememberCoroutineScope()

  LaunchedEffect(key1 = true, block = {
    runAnimation(coroutineScope, scaleFactor)
  })

  Box(
    modifier
      .fillMaxSize()
      .background(Color(0xff03A9F4))
  ) {

    Image(
      painter =
      androidx.compose.ui.res.painterResource(id = R.drawable.twitter_icon),
      contentDescription = null,
      modifier = Modifier
        .size(48.dp)
        .offset {
          IntOffset(
            width
              .div(2)
              .toInt()
              .minus(100),
            height
              .div(2)
              .toInt()
          )
        }
        .scale(scaleFactor.value)
    )

    Box(
      modifier = Modifier.fillMaxWidth()
        .align(Alignment.BottomEnd)
    ) {
      AnmolVerma(Modifier.align(Alignment.Center))
    }

  }


}

private fun runAnimation(
  coroutineScope: CoroutineScope,
  scaleFactor: Animatable<Float, AnimationVector1D>
) {
  coroutineScope.launch {
    repeat(2) {
      scaleFactor.animateTo(
        1f,
        tween(easing = FastOutSlowInEasing, durationMillis = 1000.div(it.plus(1)))
      )
      scaleFactor.animateTo(
        1.5f,
        tween(easing = FastOutSlowInEasing, durationMillis = 1500.div(it.plus(1)))
      )
    }
    scaleFactor.animateTo(160f, tween(easing = FastOutSlowInEasing))
    delay(1000)
    scaleFactor.animateTo(1f)
    runAnimation(coroutineScope, scaleFactor)
  }
}