package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.ui.animations.anmolverma.netflixanim.random

const val shootingTime = 3000

@Composable
fun ShootingStarsAnimation() {

  val widthPixels = LocalDensity.current.run {
    LocalConfiguration.current.screenWidthDp.dp.toPx()
  }
  val heightPixels = LocalDensity.current.run {
    LocalConfiguration.current.screenHeightDp.dp.toPx()
  }


  Box(
    modifier = Modifier
      .fillMaxSize()
  ) {
    Night(widthPixels.div(1.5f), heightPixels)
    ShootingStarsBox(widthPixels, heightPixels)
  }
}

@Composable
fun ShootingStarsBox(widthPixels: Float, heightPixels: Float) {
  val stars by remember {
    mutableStateOf(
      generateStars(
        count = 5,
        width = widthPixels / 2,
        height = heightPixels / 2
      )
    )
  }

  repeat(stars.size) {
    StarTailComposable(stars[it])
  }
}

@Composable
fun StarTailComposable(star: Star) {


  val infinite = rememberInfiniteTransition()
  val shooting = infinite.animateFloat(
    initialValue = 0f,
    targetValue = 300f,
    animationSpec = InfiniteRepeatableSpec(animation = keyframes {
      durationMillis = shootingTime
      delayMillis = star.animDelay
      0f at 0 with FastOutLinearInEasing
      300f at shootingTime with FastOutLinearInEasing
    }, repeatMode = RepeatMode.Restart)
  )

  val tailWidth = infinite.animateFloat(
    initialValue = 0f,
    targetValue = 0f,
    animationSpec = InfiniteRepeatableSpec(animation = keyframes {
      durationMillis = shootingTime
      delayMillis = star.animDelay
      0f at 0 with FastOutLinearInEasing
      100f at 900 with FastOutLinearInEasing
      0f at shootingTime with FastOutLinearInEasing
    }, repeatMode = RepeatMode.Restart)
  )
  val shining = infinite.animateFloat(
    initialValue = 0f,
    targetValue = 0f,
    animationSpec = InfiniteRepeatableSpec(animation = keyframes {
      durationMillis = shootingTime
      delayMillis = star.animDelay
      0f at 0 with FastOutLinearInEasing
      30f at shootingTime.div(2) with FastOutLinearInEasing
      0f at shootingTime with FastOutLinearInEasing
    }, repeatMode = RepeatMode.Restart)
  )


  StarTrail(tailWidth.value, shining.value, shooting.value, star)
}

@Composable
private fun StarTrail(
  tailWidth: Float,
  shining: Float,
  shooting: Float,
  star: Star,
) {
  // the trail
  Row(
    Modifier
      .offset { IntOffset(star.left.toInt(), star.top.toInt()) }
      .rotate(45f).graphicsLayer(translationX = shooting),
  ) {

    Box(
      modifier = Modifier
        .height(2.dp)
        .width(tailWidth.dp)

        .background(
          Brush.linearGradient(
            colors = listOf(
              Color(0xff0000FF),
              Color(0xff5F91FF),
            )
          )
        )
    )


    Box(Modifier) {
      Box(
        Modifier
          .height(2.dp)
          .width(shining.dp)
          .graphicsLayer(
            rotationZ = 45f
          )
          .background(
            Brush.linearGradient(
              colors = listOf(
                Color(0xff0000FF),
                Color(0xff5F91FF),
                Color(0xff0000FF),
              )
            )
          )
      )

      Box(
        Modifier
          .height(2.dp)
          .width(shining.dp)
          .graphicsLayer(
            rotationZ = -45f
          )
          .background(
            Brush.linearGradient(
              colors = listOf(
                Color(0xff0000FF),
                Color(0xff5F91FF),
                Color(0xff0000FF),
              )
            )
          )
      )
    }

  }


}

fun generateStars(width: Float, height: Float, count: Int): List<Star> {
  val stars = mutableListOf<Star>().apply {
    repeat(count) {
      val animDelay = random.nextInt(0, 9999)
      val top = height - (random.nextDouble(0.0, 400.0).toFloat() - 200f)
      val left = width - (random.nextDouble(0.0, 400.0).toFloat())
      add(Star(top = top, left = left, animDelay = animDelay))
    }
  }
  return stars
}

data class Star(var top: Float, var left: Float, var animDelay: Int)

@Composable
fun Night(xPixels: Float, yPixels: Float) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.radialGradient(
          colorStops = arrayOf(
            Pair(0f, Color(0xff1b2735)),
            Pair(1f, Color(0xff090a0f))
          ), center = Offset(xPixels, yPixels), radius = xPixels.times(0.8f)
        )
      )
      .rotate(45f)
  )

}
