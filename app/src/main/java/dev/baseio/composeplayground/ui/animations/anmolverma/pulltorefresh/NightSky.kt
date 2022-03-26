package dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh

import android.graphics.PointF
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh.particlesystem.StarParticleSystem
import kotlinx.coroutines.launch

@Composable
fun Sky(content: @Composable (BoxScope) -> Unit) {
  val height = with(LocalDensity.current) {
    200.dp.toPx()
  }
  Box(
    Modifier
      .fillMaxWidth()
      .height(200.dp)
  ) {
    Box {
      if (isSystemInDarkTheme()) {
        NightSky(height)
      } else {
        DaySky()
      }
      content(this)
    }

  }
}

@Composable
fun DaySky() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xff1fb4ff))
  ) {
    Canvas(
      modifier = Modifier.fillMaxSize()
    ) {

    }
  }
}

@Composable
fun NightSky(height: Float, particleCount: Int = 1000) {
  val width = with(LocalDensity.current) {
    LocalConfiguration.current.screenWidthDp.dp.toPx()
  }

  val nightParticles by remember {
    mutableStateOf(StarParticleSystem(width.times(2), height.times(2), particleCount))
  }

  val infiniteTransition = rememberInfiniteTransition()
  val angle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(80000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    )
  )
  val coroutineScope = rememberCoroutineScope()

  Box(
    modifier = Modifier
      .scale(2f)
      .rotate(angle)
      .fillMaxSize()
      .background(Color.Black)
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      coroutineScope.launch{
        nightParticles.update()
      }
      nightParticles.particles.forEach {
        drawCircle(Color.White, it.scale, it.pos.toOffset(), it.alpha)
      }
    }
  }
}

private fun PointF.toOffset(): Offset {
  return Offset(this.x, this.y)
}
